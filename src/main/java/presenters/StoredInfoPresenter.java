package presenters;

import models.pages.DeletePageModel;
import models.pages.PageExtractModel;
import models.pages.SavePageModel;
import models.pages.SavedTitlesModel;
import utils.UIStrings;
import views.StoredInfoView;

import static utils.ParserHTML.textToHtml;

public class StoredInfoPresenter {
    private StoredInfoView storedInfoView;
    private final SavePageModel savePageModel;
    private final DeletePageModel deletePageModel;
    private final PageExtractModel pageExtractModel;
    private final SavedTitlesModel savedTitlesModel;

    public StoredInfoPresenter(SavePageModel savePageModel, DeletePageModel deletePageModel, PageExtractModel pageExtractModel, SavedTitlesModel savedTitlesModel) {
        this.savePageModel = savePageModel;
        this.deletePageModel = deletePageModel;
        this.pageExtractModel = pageExtractModel;
        this.savedTitlesModel = savedTitlesModel;
        initListeners();
    }

    public void setStoredInfoView(StoredInfoView view) {
        storedInfoView = view;
        storedInfoView.setStoredInfoPresenter(this);
        savedTitlesModel.getSavedTitles();
    }

    private void initListeners() {
        savePageModel.addEventListener(() -> {
            if (storedInfoView.getComponent().isVisible()) storedInfoView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
            else savedTitlesModel.getSavedTitles();
        });

        deletePageModel.addEventListener(() -> {
            storedInfoView.showMessageDialog(UIStrings.DELETE_DIALOG_SUCCESS);
            savedTitlesModel.getSavedTitles();
        });

        pageExtractModel.addEventListener(() -> {
            String extract = pageExtractModel.getLastResult();
            if (extract != null && !extract.isEmpty()) storedInfoView.setResultTextPane(textToHtml(extract));
            else storedInfoView.showMessageDialog(UIStrings.ERROR_EXTRACT_EMPTY);
        });

        savedTitlesModel.addEventListener(() -> {
            storedInfoView.updateComboBox(savedTitlesModel.getLastResults());
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

    public void onUpdatePage() {
        new Thread(() -> {
            savePageModel.savePage(storedInfoView.getSelectedItem().toString().replace("'", "`"),
                    storedInfoView.getText());
        }).start();
    }

    public void onSelectedItem() {
        new Thread(() -> {
            pageExtractModel.getPageExtract(storedInfoView.getSelectedItem().toString());
        }).start();
    }

    public void onDeletePage() {
        new Thread(() -> {
            deletePageModel.deletePage(storedInfoView.getSelectedItem().toString());
        }).start();
    }
}