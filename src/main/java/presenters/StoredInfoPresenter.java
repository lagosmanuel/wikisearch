package presenters;

import models.PageResult;
import models.pages.DeletePageModel;
import models.pages.LoadPageModel;
import models.pages.SavePageModel;
import models.pages.SavedTitlesModel;
import utils.ImagesCache;
import utils.UIStrings;
import views.StoredInfoView;

public class StoredInfoPresenter {
    private StoredInfoView storedInfoView;
    private final SavePageModel savePageModel;
    private final DeletePageModel deletePageModel;
    private final LoadPageModel loadPageModel;
    private final SavedTitlesModel savedTitlesModel;
    private PageResult lastPageResult;

    public StoredInfoPresenter(SavePageModel savePageModel, DeletePageModel deletePageModel, LoadPageModel loadPageModel, SavedTitlesModel savedTitlesModel) {
        this.savePageModel = savePageModel;
        this.deletePageModel = deletePageModel;
        this.loadPageModel = loadPageModel;
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

        loadPageModel.addEventListener(() -> {
            lastPageResult = loadPageModel.getLastResult();
            ImagesCache.saveImageToCache(lastPageResult.getThumbnail(), String.valueOf(lastPageResult.getPageID()));
            if (lastPageResult != null) storedInfoView.setResultTextPane(lastPageResult.getExtract());
            else storedInfoView.showMessageDialog(UIStrings.ERROR_DIALOG_EXTRACTEMPTY);
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
        new Thread(() -> savePageModel.savePage(lastPageResult.setExtract(storedInfoView.getText()))).start();
    }

    public void onSelectedItem() {
        new Thread(() -> loadPageModel.getPageExtract(storedInfoView.getSelectedItem().toString())).start();
    }

    public void onDeletePage() {
        new Thread(() -> deletePageModel.deletePage(storedInfoView.getSelectedItem().toString())).start();
    }
}