package presenters;

import models.PageResult;
import models.pages.DeletePageModel;
import models.pages.LoadPageModel;
import models.pages.SavePageModel;
import models.pages.SavedPageTitlesModel;
import utils.ImagesCache;
import utils.UIStrings;
import views.StoredInfoView;

public class StoredInfoPresenter {
    private StoredInfoView storedInfoView;
    private final SavePageModel savePageModel;
    private final DeletePageModel deletePageModel;
    private final LoadPageModel loadPageModel;
    private final SavedPageTitlesModel savedPageTitlesModel;
    private PageResult lastPageResult;

    public StoredInfoPresenter(SavePageModel savePageModel, DeletePageModel deletePageModel, LoadPageModel loadPageModel, SavedPageTitlesModel savedPageTitlesModel) {
        this.savePageModel = savePageModel;
        this.deletePageModel = deletePageModel;
        this.loadPageModel = loadPageModel;
        this.savedPageTitlesModel = savedPageTitlesModel;
        initListeners();
    }

    public void setStoredInfoView(StoredInfoView view) {
        storedInfoView = view;
        storedInfoView.setStoredInfoPresenter(this);
        savedPageTitlesModel.getSavedPageTitles();
    }

    private void initListeners() {
        savePageModel.addEventListener(() -> {
            if (storedInfoView.getComponent().isVisible()) storedInfoView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
            else savedPageTitlesModel.getSavedPageTitles();
        });

        deletePageModel.addEventListener(() -> {
            storedInfoView.showMessageDialog(UIStrings.DELETE_DIALOG_SUCCESS);
            savedPageTitlesModel.getSavedPageTitles();
        });

        loadPageModel.addEventListener(() -> {
            lastPageResult = loadPageModel.getLastResult();
            ImagesCache.getInstance().saveImageToCache(lastPageResult.getThumbnail(), String.valueOf(lastPageResult.getPageID()));
            if (lastPageResult != null) storedInfoView.setPageTextPane(lastPageResult.getExtract());
            else storedInfoView.showMessageDialog(UIStrings.ERROR_DIALOG_EXTRACTEMPTY);
        });

        savedPageTitlesModel.addEventListener(() -> {
            storedInfoView.updateComboBox(savedPageTitlesModel.getLastResults().toArray());
            if (storedInfoView.comboBoxHasItems()) {
                storedInfoView.setEditable(true);
                onSelectedItem();
            }
            else {
                storedInfoView.setEditable(false);
                storedInfoView.setPageTextPane("");
            }
        });
    }

    public void onUpdatePage() {
        new Thread(() -> savePageModel.savePage(lastPageResult.setExtract(storedInfoView.getText()))).start();
    }

    public void onSelectedItem() {
        new Thread(() -> loadPageModel.loadPage(storedInfoView.getSelectedItem().toString())).start();
    }

    public void onDeletePage() {
        new Thread(() -> deletePageModel.deletePageByTitle(storedInfoView.getSelectedItem().toString())).start();
    }
}