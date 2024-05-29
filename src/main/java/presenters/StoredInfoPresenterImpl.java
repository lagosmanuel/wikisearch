package presenters;

import views.StoredInfoView;

public class StoredInfoPresenterImpl implements StoredInfoPresenter {
    protected StoredInfoView storedInfoView;

    @Override
    public void setStoredInfoView(StoredInfoView storedInfoView) {
        this.storedInfoView = storedInfoView;
        storedInfoView.setStoredInfoPresenter(this);
    }
}
