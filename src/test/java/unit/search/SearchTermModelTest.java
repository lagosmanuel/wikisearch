package unit.search;

import models.SearchResult;
import models.repos.apis.APIHelper;
import models.repos.databases.SearchResultDataBase;
import models.search.SearchTermModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchTermModelTest {
    private SearchTermModel searchTermModel;
    private SearchResultDataBase searchResultDataBase;
    private APIHelper apiHelper;

    @Before
    public void setup() {
        apiHelper = mock(APIHelper.class);
        searchResultDataBase = mock(SearchResultDataBase.class);
        searchTermModel = new SearchTermModel(searchResultDataBase, apiHelper);
    }

    @Test
    public void searchTerm() {
        String termToSearch = "The X-Files";
        Collection<SearchResult> searchResultList = new ArrayList<>();
        SearchResult expectedSearchResult = new SearchResult(termToSearch, 30304, "la mejor serie del mundo");
        searchResultList.add(expectedSearchResult);
        when(apiHelper.searchTerm(termToSearch)).thenReturn(searchResultList);
        searchTermModel.searchTerm(termToSearch);
        Assert.assertEquals(expectedSearchResult, searchTermModel.getLastSearchResults().iterator().next());
    }

    @Test
    public void searchTermWithScore() {
        String termToSearch = "The X-Files";
        Collection<SearchResult> searchResultList = new ArrayList<>();
        searchResultList.add(new SearchResult(termToSearch, 30304, "la mejor serie del mundo"));
        SearchResult expectedSearchResult = new SearchResult(termToSearch, 30304, "la mejor serie del mundo", 10, "now");
        when(apiHelper.searchTerm(termToSearch)).thenReturn(searchResultList);
        when(searchResultDataBase.getSearchResultByTitle(termToSearch)).thenReturn(expectedSearchResult);
        searchTermModel.searchTerm(termToSearch);
        Assert.assertEquals(expectedSearchResult.getScore(), searchTermModel.getLastSearchResults().iterator().next().getScore());
    }

    @Test
    public void searchEmptyTerm() {
        searchTermModel.searchTerm("");
        Assert.assertEquals(0, searchTermModel.getLastSearchResults().size());
    }

    @Test
    public void searchNullTerm() {
        searchTermModel.searchTerm(null);
        Assert.assertEquals(0, searchTermModel.getLastSearchResults().size());
    }
    
    @Test
    public void searchTermNotify() {
        String termToSearch = "The X-Files";
        Collection<SearchResult> searchResultList = new ArrayList<>();
        SearchResult expectedSearchResult = new SearchResult(termToSearch, 30304, "la mejor serie del mundo");
        searchResultList.add(expectedSearchResult);
        when(apiHelper.searchTerm(termToSearch)).thenReturn(searchResultList);
        searchTermModel.searchTerm(termToSearch);
        Assert.assertEquals(expectedSearchResult, searchTermModel.getLastSearchResults().iterator().next());
    }
}
