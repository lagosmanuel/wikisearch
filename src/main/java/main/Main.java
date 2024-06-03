package main;

import models.search.UpdateSearchResultsModel;
import models.pages.DeletePageModel;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.search.SearchTermModel;
import models.pages.LoadPageModel;
import models.search.GetSearchResultsModel;
import models.pages.SavedTitlesModel;
import models.repos.databases.CatalogDataBase;
import models.repos.databases.SearchResultDataBase;
import presenters.RankingPresenter;
import presenters.SearchPresenter;
import presenters.StoredInfoPresenter;
import views.MainView;

public class Main {
    public static void main(String[] args) {
        CatalogDataBase.load();
        SearchResultDataBase.load();

        SearchTermModel searchTermModel = new SearchTermModel();
        RetrievePageModel retrievePageModel = new RetrievePageModel();
        SavePageModel savePageModel = new SavePageModel();
        DeletePageModel deletePageModel = new DeletePageModel();
        LoadPageModel loadPageModel = new LoadPageModel();
        SavedTitlesModel savedTitlesModel = new SavedTitlesModel();
        GetSearchResultsModel getSearchResultsModel = new GetSearchResultsModel();
        UpdateSearchResultsModel updateSearchResultsModel = new UpdateSearchResultsModel();

        MainView mainView = new MainView();

        SearchPresenter searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel, updateSearchResultsModel);
        StoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(savePageModel, deletePageModel, loadPageModel, savedTitlesModel);
        RankingPresenter rankingPresenter = new RankingPresenter(getSearchResultsModel, updateSearchResultsModel);

        searchPresenter.setSearchView(mainView.getSearchView());
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
        rankingPresenter.setStoredInfoView(mainView.getRankingView());
    }
}
