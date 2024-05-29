package presenters;

import views.MainViewImpl;
import views.MainView;

public class MainPresenterImpl implements MainPresenter {
    protected MainView mainView;

    protected SearchPresenter searchPresenter;
    protected StoredInfoPresenter storedInfoPresenter;

    public MainPresenterImpl() {
        mainView = new MainViewImpl();
        mainView.init();

        searchPresenter = new SearchPresenterImpl();
        searchPresenter.setSearchView(mainView.getSearchView());

        storedInfoPresenter = new StoredInfoPresenterImpl();
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
    }
}
