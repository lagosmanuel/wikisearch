package unit.search;

import models.SearchResult;
import models.repos.databases.SearchResultDataBase;
import models.search.GetSearchResultsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSearchResultsModelTest {
    private GetSearchResultsModel getSearchResultsModel;
    private SearchResultDataBase searchResultDataBase;

    @Before
    public void setup() {
        searchResultDataBase = mock(SearchResultDataBase.class);
        getSearchResultsModel = new GetSearchResultsModel(searchResultDataBase);
    }

    @Test
    public void getSavedSearchResults() {
        Collection<SearchResult> searchResultsList = new LinkedList<>();
        SearchResult expectedSearchResult = new SearchResult("the x-files", 30304, "la mejor serie del mundo", 10, "today");
        searchResultsList.add(expectedSearchResult);
        when(searchResultDataBase.getSearchResults()).thenReturn(searchResultsList);
        getSearchResultsModel.getSavedSearchResults();
        Assert.assertEquals(expectedSearchResult, getSearchResultsModel.getLastResults().iterator().next());
    }

    @Test
    public void getEmptySearchResults() {
        when(searchResultDataBase.getSearchResults()).thenReturn(new ArrayList<>());
        getSearchResultsModel.getSavedSearchResults();
        Assert.assertEquals(0, getSearchResultsModel.getLastResults().size());
    }

    @Test
    public void getNullSearchResults() {
        when(searchResultDataBase.getSearchResults()).thenReturn(null);
        getSearchResultsModel.getSavedSearchResults();
        Assert.assertEquals(0, getSearchResultsModel.getLastResults().size());
    }

    @Test
    public void getSavedSearchResultsNotify() {
        AtomicBoolean notified = new AtomicBoolean(false);
        getSearchResultsModel.addEventListener(() -> notified.set(true));
        getSearchResultsModel.getSavedSearchResults();
        Assert.assertTrue(notified.get());
    }
}
