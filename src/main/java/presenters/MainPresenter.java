package presenters;

import models.*;
import models.repos.DataBase;
import views.MainView;

public class MainPresenter {

    public MainPresenter() {
        DataBase.loadDatabase();

        SearchModel searchModel = new SearchModel();
        RetrieveModel retrieveModel = new RetrieveModel();
        SaveModel saveModel = new SaveModel();
        DeleteModel deleteModel = new DeleteModel();
        ExtractModel extractModel = new ExtractModel();
        TitlesModel titlesModel = new TitlesModel();
        ItemsModel itemsModel = new ItemsModel();

        MainView mainView = new MainView();

        SearchPresenter searchPresenter = new SearchPresenter(searchModel, retrieveModel, saveModel);
        StoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(saveModel, deleteModel, extractModel, titlesModel);
        RankingPresenter rankingPresenter = new RankingPresenter(itemsModel);

        searchPresenter.setSearchView(mainView.getSearchView());
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
        rankingPresenter.setStoredInfoView(mainView.getRankingView());
    }
}
