package presenters;

import models.DeleteModel;
import models.ExtractModel;
import models.SaveModel;
import utils.UIStrings;
import views.StoredInfoView;

import static utils.ParserHTML.textToHtml;

//TODO tiene muchas responsabilidades?
public class StoredInfoPresenter {
    private StoredInfoView storedInfoView;
    private final SaveModel saveModel;
    private final DeleteModel deleteModel;
    private final ExtractModel extractModel;

    public StoredInfoPresenter(SaveModel saveModel, DeleteModel deleteModel, ExtractModel extractModel) {
        this.saveModel = saveModel;
        this.deleteModel = deleteModel;
        this.extractModel = extractModel;
        initListeners();
    }

    private void initListeners() {
        saveModel.addEventListener(() -> {
            storedInfoView.updateComboBox();
            if (storedInfoView.getComponent().isVisible()) {
                storedInfoView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
            }
        });

        deleteModel.addEventLister(() -> {
            storedInfoView.showMessageDialog(UIStrings.DELETE_DIALOG_SUCCESS);
        });

        extractModel.addEventListener(() -> {
            String extract = extractModel.getLastExtract();

            if (!extract.isEmpty()) {
                storedInfoView.setResultTextPane(textToHtml(extract));
            } else {
                storedInfoView.showMessageDialog(UIStrings.ERROR_EXTRACT_EMPTY);
            }
        });
    }

    public void setStoredInfoView(StoredInfoView storedInfoView) {
        this.storedInfoView = storedInfoView;
        storedInfoView.setStoredInfoPresenter(this);
    }

    public void onUpdate() {
        Object selectedItem = storedInfoView.getSelectedItem();
        if (selectedItem != null) {
            saveModel.savePage(selectedItem.toString().replace("'", "`"), storedInfoView.getText());
            storedInfoView.updateComboBox();
        } else {
            storedInfoView.showMessageDialog(UIStrings.UPDATE_DIALOG_NOSELECTEDITEM);
        }
    }

    public void onSelectedItem() {
        String title = storedInfoView.getSelectedItem().toString();
        if (!title.isEmpty()) {
            extractModel.extract(title);
        } else {
            storedInfoView.showMessageDialog(UIStrings.EXTRACT_DIALOG_NOSELECTEDITEM);
        }
    }

    public void onDelete() {
        Object selectedItem = storedInfoView.getSelectedItem();
        if (selectedItem != null) {
            deleteModel.deletePage(selectedItem.toString());
            storedInfoView.updateComboBox();
            storedInfoView.setResultTextPane("");
        } else {
            storedInfoView.showMessageDialog(UIStrings.DELETE_DIALOG_NOSELECTEDITEM);
        }
    }
}