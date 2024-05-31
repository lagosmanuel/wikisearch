package presenters;

import models.DeleteModel;
import models.ExtractModel;
import models.SaveModel;
import models.TitlesModel;
import utils.UIStrings;
import views.StoredInfoView;

import static utils.ParserHTML.textToHtml;

//TODO tiene muchas responsabilidades?
public class StoredInfoPresenter {
    private StoredInfoView storedInfoView;
    private final SaveModel saveModel;
    private final DeleteModel deleteModel;
    private final ExtractModel extractModel;
    private final TitlesModel titlesModel;

    public StoredInfoPresenter(SaveModel saveModel, DeleteModel deleteModel, ExtractModel extractModel, TitlesModel titlesModel) {
        this.saveModel = saveModel;
        this.deleteModel = deleteModel;
        this.extractModel = extractModel;
        this.titlesModel = titlesModel;
        initListeners();
    }

    public void setStoredInfoView(StoredInfoView view) {
        storedInfoView = view;
        storedInfoView.setStoredInfoPresenter(this);
        titlesModel.getTitles();
    }

    private void initListeners() {
        saveModel.addEventListener(() -> {
            if (storedInfoView.getComponent().isVisible()) storedInfoView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
            else titlesModel.getTitles();
        });

        deleteModel.addEventListener(() -> {
            storedInfoView.showMessageDialog(UIStrings.DELETE_DIALOG_SUCCESS);
            titlesModel.getTitles();
        });

        extractModel.addEventListener(() -> {
            String extract = extractModel.getLastExtract();
            if (extract != null && !extract.isEmpty()) storedInfoView.setResultTextPane(textToHtml(extract));
            else storedInfoView.showMessageDialog(UIStrings.ERROR_EXTRACT_EMPTY);
        });

        titlesModel.addEventListener(() -> {
            storedInfoView.updateComboBox(titlesModel.getLastResult());
            if (storedInfoView.comboBoxHasItems()) {
                storedInfoView.setEnable(true);
                onSelectedItem();
            }
            else {
                storedInfoView.setEnable(false);
                storedInfoView.setResultTextPane("");
            }
        });
    }

    // #TODO: está mal asumir que nunca hay selecteditem null? (la vista me lo manda)

    public void onUpdate() {
        saveModel.savePage(storedInfoView.getSelectedItem().toString().replace("'", "`"),
                           storedInfoView.getText());
    }

    public void onSelectedItem() {
        extractModel.extract(storedInfoView.getSelectedItem().toString());
    }

    public void onDelete() {
        deleteModel.deletePage(storedInfoView.getSelectedItem().toString());
    }
}