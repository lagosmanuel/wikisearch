package presenters;

import models.*;
import models.repos.DataBase;
import views.MainView;

public class MainPresenter {
    protected MainView mainView;
    protected SearchPresenter searchPresenter;

    protected StoredInfoPresenter storedInfoPresenter;

    public MainPresenter() {
        SearchModel searchModel = new SearchModel(); // TODO: el director debe conocer a los modelos?
        RetrieveModel retrieveModel = new RetrieveModel();
        SaveModel saveModel = new SaveModel();
        DeleteModel deleteModel = new DeleteModel();

        DataBase.loadDatabase(); //TODO: hay que cargar la base de datos? cuando? donde? una sola vez?

        mainView = new MainView();
        mainView.init();

        searchPresenter = new SearchPresenter(searchModel, retrieveModel, saveModel);
        searchPresenter.setSearchView(mainView.getSearchView());

        storedInfoPresenter = new StoredInfoPresenter(saveModel, deleteModel);
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
    }
}
