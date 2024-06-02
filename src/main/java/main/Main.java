package main;

import models.SearchResult;
import models.entries.UpdateSearchResultsModel;
import models.pages.DeletePageModel;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.entries.SearchTermModel;
import models.pages.LoadPageModel;
import models.entries.GetSearchResultsModel;
import models.pages.SavedTitlesModel;
import models.repos.DataBase;
import presenters.RankingPresenter;
import presenters.SearchPresenter;
import presenters.StoredInfoPresenter;
import views.MainView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBase.loadDatabase();

        SearchTermModel searchTermModel = new SearchTermModel();
        RetrievePageModel retrievePageModel = new RetrievePageModel();
        SavePageModel savePageModel = new SavePageModel();
        DeletePageModel deletePageModel = new DeletePageModel();
        LoadPageModel loadPageModel = new LoadPageModel();
        SavedTitlesModel savedTitlesModel = new SavedTitlesModel();
        GetSearchResultsModel getSearchResultsModel = new GetSearchResultsModel();
        UpdateSearchResultsModel updateSearchResultsModel = new UpdateSearchResultsModel();

        MainView mainView = new MainView();

        SearchPresenter searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel);
        StoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(savePageModel, deletePageModel, loadPageModel, savedTitlesModel);
        RankingPresenter rankingPresenter = new RankingPresenter(getSearchResultsModel, updateSearchResultsModel);

        searchPresenter.setSearchView(mainView.getSearchView());
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
        rankingPresenter.setStoredInfoView(mainView.getRankingView());
    }
}
