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
    public void searchTermSuccess() throws InterruptedException {
        String termToSearch = "The X-Files";
        SearchResult expectedSearchResult = new SearchResult(termToSearch, 30304 ,"la mejor serie del mundo");
        Collection<SearchResult> searchResultList = new ArrayList<>();
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
        SearchResult searchResult = new SearchResult(termToSearch, 30304 ,"la mejor serie del mundo");
        Collection<SearchResult> searchResultList = new ArrayList<>();
        searchResultList.add(searchResult);
        when(apiHelper.searchTerm(termToSearch)).thenReturn(searchResultList);
        when(searchResultDataBase.getSearchResultByTitle(termToSearch)).thenReturn(new SearchResult(termToSearch, 30304, "la mejor serie del mundo", expectedScore, "now"));
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
}
