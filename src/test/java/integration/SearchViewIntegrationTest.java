package integration;

import models.SearchResult;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.repos.apis.APIHelper;
import models.repos.databases.CatalogDataBase;
import models.repos.databases.SearchResultDataBase;
import models.search.SearchTermModel;
import models.search.UpdateSearchResultsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import presenters.SearchPresenter;
import views.SearchView;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchViewIntegrationTest {
    private SearchView searchView;
    private SearchPresenter searchPresenter;
    private SearchTermModel searchTermModel;
    private RetrievePageModel retrievePageModel;
    private SavePageModel savePageModel;
    private UpdateSearchResultsModel updateSearchResultsModel;
    private APIHelper apiHelper;
    private CatalogDataBase catalogDataBase;
    private SearchResultDataBase searchResultDataBase;

    @Before
    public void setup() {
        apiHelper = mock(APIHelper.class);
        catalogDataBase = mock(CatalogDataBase.class);
        searchResultDataBase = mock(SearchResultDataBase.class);
        searchView = new SearchView();
        searchTermModel = new SearchTermModel(searchResultDataBase, apiHelper);
        retrievePageModel = new RetrievePageModel(apiHelper);
        savePageModel = new SavePageModel(catalogDataBase);
        updateSearchResultsModel = new UpdateSearchResultsModel(searchResultDataBase);
        searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel, updateSearchResultsModel);
        searchPresenter.setSearchView(searchView);
    }

    @Test
    public void searchTerm() throws InterruptedException {
        String termToSearch = "The X-Files";

        Collection<SearchResult> searchResultList = new ArrayList<>();
        SearchResult expectedSearchResult = new SearchResult(termToSearch, 1 ,"");
        searchResultList.add(expectedSearchResult);
        when(apiHelper.searchTerm(termToSearch)).thenReturn(searchResultList);

        searchView.setSearchTextField(termToSearch);
        searchView.pressSearchButton();
        Thread.sleep(500);

        Assert.assertEquals(expectedSearchResult, searchView.getSearchResults().iterator().next());
    }

    @Test
    public void searchTermWithScore() throws InterruptedException {
        String termToSearch = "The X-Files";
        int expectedScore = 10;

        Collection<SearchResult> searchResultList = new ArrayList<>();
        SearchResult searchResult = new SearchResult(termToSearch, 1 ,"");
        searchResultList.add(searchResult);
        when(apiHelper.searchTerm(termToSearch)).thenReturn(searchResultList);

        SearchResult savedSearchResult = new SearchResult(termToSearch, 1, "", expectedScore, "");
        when(searchResultDataBase.getSearchResultByTitle(termToSearch)).thenReturn(savedSearchResult);

        searchView.setSearchTextField(termToSearch);
        searchView.pressSearchButton();
        Thread.sleep(500);

        Assert.assertEquals(expectedScore, searchView.getSearchResults().iterator().next().getScore());
    }

    @Test
    public void searchTermNoResult() throws InterruptedException {
        String termToSearch = "The X-Files";
        when(apiHelper.searchTerm(termToSearch)).thenReturn(new ArrayList<>());

        searchView.setSearchTextField(termToSearch);
        searchView.pressSearchButton();
        Thread.sleep(500);

        Assert.assertNull(searchView.getSearchResults());
    }

    @Test
    public void changeScore() throws InterruptedException {
        int newscore = 10;
        SearchResult searchResult = new SearchResult("foo", 1, "", 1, "");
        searchView.setSelectedResult(searchResult);
        searchView.getStarsPanel().updateScore(newscore);
        searchView.getStarsPanel().getListener().onEvent();
        Thread.sleep(500);
        Assert.assertEquals(newscore, searchView.getSelectedResult().getScore());
    }
}
