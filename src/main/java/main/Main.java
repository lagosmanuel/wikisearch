package main;

import models.repos.apis.APIHelperImpl;
import models.repos.apis.APIHelper;
import models.repos.databases.CatalogDataBase;
import models.repos.databases.SearchResultDataBase;
import models.search.UpdateSearchResultsModel;
import models.pages.DeletePageModel;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.search.SearchTermModel;
import models.pages.LoadPageModel;
import models.search.GetSearchResultsModel;
import models.pages.SavedTitlesModel;
import models.repos.databases.CatalogDataBaseImpl;
import models.repos.databases.SearchResultDataBaseImpl;
import presenters.RankingPresenter;
import presenters.SearchPresenter;
import presenters.StoredInfoPresenter;
import views.MainView;

public class Main {
    public static void main(String[] args) {
        CatalogDataBase catalogDataBase = new CatalogDataBaseImpl();
        SearchResultDataBase searchResultDataBase = new SearchResultDataBaseImpl();
        APIHelper apiHelper = new APIHelperImpl();

        SearchTermModel searchTermModel = new SearchTermModel(searchResultDataBase, apiHelper);
        RetrievePageModel retrievePageModel = new RetrievePageModel(apiHelper);
        SavePageModel savePageModel = new SavePageModel(catalogDataBase);
        DeletePageModel deletePageModel = new DeletePageModel(catalogDataBase);
        LoadPageModel loadPageModel = new LoadPageModel(catalogDataBase);
        SavedTitlesModel savedTitlesModel = new SavedTitlesModel(catalogDataBase);
        GetSearchResultsModel getSearchResultsModel = new GetSearchResultsModel(searchResultDataBase);
        UpdateSearchResultsModel updateSearchResultsModel = new UpdateSearchResultsModel(searchResultDataBase);

        MainView mainView = new MainView();

        SearchPresenter searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel, updateSearchResultsModel);
        StoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(savePageModel, deletePageModel, loadPageModel, savedTitlesModel);
        RankingPresenter rankingPresenter = new RankingPresenter(getSearchResultsModel, updateSearchResultsModel, searchPresenter);

        searchPresenter.setSearchView(mainView.getSearchView());
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
        rankingPresenter.setRankingView(mainView.getRankingView(), mainView);
    }
}
