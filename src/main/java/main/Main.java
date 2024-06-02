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
        DataBase.executeUpdate("drop table searches");
        DataBase.loadDatabase();

        DataBase.saveSearchResult(new SearchResult("The X-Files", 1, "The X-Files is an American science fiction drama television series created by Chris Carter. The original television series aired from September 1993 to May 2002 on Fox. During its original run, the program spanned nine seasons, with 202 episodes. A short tenth season consisting of six episodes ran from January to February 2016. Following the ratings success of this revival, The X-Files returned for an eleventh season of ten episodes, which ran from January to March 2018. In addition to the television series, two feature films have been released: The 1998 film The X-Files and the stand-alone film The X-Files: I Want to Believe, released in 2008, six years after the original television run had ended.", 1, null));
        DataBase.saveSearchResult(new SearchResult("Twin Peaks", 2, "Twin Peaks tv show description..", 2, null));
        DataBase.saveSearchResult(new SearchResult("Wayward Pines", 3, "Wayward Pines tv show description..", 3, null));
        DataBase.saveSearchResult(new SearchResult("True Detective", 4, "True Detective tv show description..", 4, null));

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
