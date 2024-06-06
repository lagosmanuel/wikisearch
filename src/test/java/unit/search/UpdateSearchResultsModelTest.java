package unit.search;

import models.SearchResult;
import models.repos.databases.SearchResultDataBase;
import models.search.UpdateSearchResultsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;

public class UpdateSearchResultsModelTest {
    private UpdateSearchResultsModel updateSearchResultsModel;
    private SearchResultDataBase searchResultDataBase;

    @Before
    public void setup() {
        searchResultDataBase = mock(SearchResultDataBase.class);
        updateSearchResultsModel = new UpdateSearchResultsModel(searchResultDataBase);
    }

    @Test
    public void updateSearchResult() {
        SearchResult expectedSearchResult = new SearchResult("The X-Files", 30304, "la mejor serie del mundo", 10, "now");
        updateSearchResultsModel.updateSearchResult(expectedSearchResult);
        Assert.assertEquals(expectedSearchResult, updateSearchResultsModel.getLastUpdatedSearchResult());
    }

    @Test
    public void updateNullSearchResult() {
        updateSearchResultsModel.updateSearchResult(null);
        Assert.assertNull(updateSearchResultsModel.getLastUpdatedSearchResult());
    }

    @Test
    public void updateSearchResultNotify() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        SearchResult searchResult = new SearchResult("The X-Files", 30304, "la mejor serie del mundo", 10, "now");
        updateSearchResultsModel.addEventListener(() -> atomicBoolean.set(true));
        updateSearchResultsModel.updateSearchResult(searchResult);
        Assert.assertTrue(atomicBoolean.get());
    }
}
