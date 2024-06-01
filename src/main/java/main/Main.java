package main;

import models.pages.DeletePageModel;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.entries.SearchTermModel;
import models.pages.PageExtractModel;
import models.entries.SavedEntriesModel;
import models.entries.SavedTitlesModel;
import models.repos.DataBase;
import presenters.RankingPresenter;
import presenters.SearchPresenter;
import presenters.StoredInfoPresenter;
import views.MainView;

public class Main {
    public static void main(String[] args) {
        DataBase.loadDatabase();

        SearchTermModel searchTermModel = new SearchTermModel();
        RetrievePageModel retrievePageModel = new RetrievePageModel();
        SavePageModel savePageModel = new SavePageModel();
        DeletePageModel deletePageModel = new DeletePageModel();
        PageExtractModel pageExtractModel = new PageExtractModel();
        SavedTitlesModel savedTitlesModel = new SavedTitlesModel();
        SavedEntriesModel savedEntriesModel = new SavedEntriesModel();

        MainView mainView = new MainView();

        SearchPresenter searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel);
        StoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(savePageModel, deletePageModel, pageExtractModel, savedTitlesModel);
        RankingPresenter rankingPresenter = new RankingPresenter(savedEntriesModel);

        searchPresenter.setSearchView(mainView.getSearchView());
        storedInfoPresenter.setStoredInfoView(mainView.getStoredInfoView());
        rankingPresenter.setStoredInfoView(mainView.getRankingView());
    }
}
