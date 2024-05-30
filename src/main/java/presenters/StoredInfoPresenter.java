package presenters;

import models.DeleteModel;
import models.SaveModel;
import utils.UIStrings;
import views.StoredInfoView;

//TODO tiene muchas responsabilidades?
public class StoredInfoPresenter {
    protected StoredInfoView storedInfoView;
    protected SaveModel saveModel;
    protected DeleteModel deleteModel;

    public StoredInfoPresenter(SaveModel saveModel, DeleteModel deleteModel) {
        this.saveModel = saveModel;
        this.deleteModel = deleteModel;
        initListeners();
    }

    protected void initListeners() {
        saveModel.addEventListener(() -> {
            storedInfoView.updateComboBox();
            if (storedInfoView.getComponent().isVisible()) {
                storedInfoView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
            }
        });

        deleteModel.addEventLister(() -> {
            storedInfoView.showMessageDialog(UIStrings.DELETE_DIALOG_SUCCESS);
        });
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

    public void setStoredInfoView(StoredInfoView storedInfoView) {
        this.storedInfoView = storedInfoView;
        storedInfoView.setStoredInfoPresenter(this);
    }
}