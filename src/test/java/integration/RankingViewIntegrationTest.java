package integration;

import models.PageResult;
import models.SearchResult;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.repos.apis.APIHelper;
import models.repos.databases.CatalogDataBase;
import models.repos.databases.SearchResultDataBase;
import models.search.GetSearchResultsModel;
import models.search.SearchTermModel;
import models.search.UpdateSearchResultsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import presenters.RankingPresenter;
import presenters.SearchPresenter;
import views.MainView;
import views.RankingView;
import views.SearchView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RankingViewIntegrationTest {
    private MainView mainView;
    private RankingView rankingView;
    private SearchView searchView;
    private RankingPresenter rankingPresenter;
    private SearchPresenter searchPresenter;
    private SearchTermModel searchTermModel;
    private RetrievePageModel retrievePageModel;
    private GetSearchResultsModel getSearchResultsModel;
    private SavePageModel savePageModel;
    private UpdateSearchResultsModel updateSearchResultsModel;
    private APIHelper apiHelper;
    private CatalogDataBase catalogDataBase;
    private SearchResultDataBase searchResultDataBase;

    @Before
    public void setup() {
        mainView = mock(MainView.class);
        searchView = new SearchView();
        apiHelper = mock(APIHelper.class);
        catalogDataBase = mock(CatalogDataBase.class);
        searchResultDataBase = mock(SearchResultDataBase.class);
        rankingView = new RankingView();
        searchTermModel = new SearchTermModel(searchResultDataBase, apiHelper);
        retrievePageModel = new RetrievePageModel(apiHelper);
        savePageModel = new SavePageModel(catalogDataBase);
        getSearchResultsModel = new GetSearchResultsModel(searchResultDataBase);
        updateSearchResultsModel = new UpdateSearchResultsModel(searchResultDataBase);
        searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel, updateSearchResultsModel);
        searchPresenter.setSearchView(searchView);
        rankingPresenter = new RankingPresenter(getSearchResultsModel, updateSearchResultsModel, searchPresenter);
        rankingPresenter.setRankingView(rankingView, mainView);
    }

    @Test
    public void getSortedSearchResults() throws InterruptedException {
        List<SearchResult> searchResultList = new ArrayList<>();
        SearchResult firstSearchResult = new SearchResult("foo", 1, "", 1, "");
        SearchResult lastSearchResult = new SearchResult("bar", 2, "", 10, "");
        searchResultList.add(firstSearchResult);
        searchResultList.add(lastSearchResult);
        when(searchResultDataBase.getSearchResults()).thenReturn(new ArrayList<>(searchResultList));

        getSearchResultsModel.getSavedSearchResults();
        Thread.sleep(500);

        searchResultList.sort((key1, key2) -> Integer.compare(key2.getScore(), key1.getScore()));
        Assert.assertEquals(searchResultList, JListToList(rankingView.getRankingList()));
    }

    private List<SearchResult> JListToList(JList<SearchResult> searchResultsJList) {
        List<SearchResult> searchResultList = new ArrayList<>();
        for (int i = 0; i < searchResultsJList.getModel().getSize(); ++i)
            searchResultList.add(searchResultsJList.getModel().getElementAt(i));
        return searchResultList;
    }

    @Test
    public void searchSearchResult() throws InterruptedException {
        String title = "foo";
        int id = 1;
        int score = 10;

        List<SearchResult> searchResultList = new ArrayList<>();
        SearchResult searchResult = new SearchResult(title, id, "", score, "");
        searchResultList.add(searchResult);
        rankingView.updateRankingList(searchResultList);

        PageResult pageResult = new PageResult(title, id, "bar", 0, null, "");
        when(apiHelper.retrievePage(id)).thenReturn(pageResult);

        rankingView.getRankingList().setSelectedIndex(0);
        rankingView.pressSearchButton();
        Thread.sleep(1000);

        Assert.assertEquals(score, searchView.getSelectedScore());
    }

    @Test
    public void deleteScoreSearchResult() throws InterruptedException {
        List<SearchResult> searchResultList = new ArrayList<>();
        SearchResult searchResult = new SearchResult("foo", 1, "", 1, "");
        searchResultList.add(searchResult);
        when(searchResultDataBase.getSearchResults()).thenReturn(new ArrayList<>(searchResultList));
        getSearchResultsModel.getSavedSearchResults();
        searchView.setSelectedResult(searchResult);
        searchView.getStarsPanel().updateScore(0);
        searchView.getStarsPanel().getListener().onEvent();
        Thread.sleep(500);
        Assert.assertEquals(0, JListToList(rankingView.getRankingList()).size());
    }
}
