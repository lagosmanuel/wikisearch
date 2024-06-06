package unit.pages;

import models.PageResult;
import models.pages.RetrievePageModel;
import models.repos.apis.APIHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrievePageModelTest {
    private RetrievePageModel retrievePageModel;
    private APIHelper apiHelper;

    @Before
    public void setup() {
        apiHelper = mock(APIHelper.class);
        retrievePageModel = new RetrievePageModel(apiHelper);
    }

    @Test
    public void retrievePageByID() {
        int pageID = 30304;
        PageResult expectedPageResult = new PageResult(
            "The X-Files",
            pageID,
            "la mejor serie del mundo",
            0,
            new byte[0],
            "http://en.wikipedia.com/the_x_files"
        );
        when(apiHelper.retrievePage(pageID)).thenReturn(expectedPageResult);
        retrievePageModel.retrievePageByID(pageID);
        Assert.assertEquals(expectedPageResult, retrievePageModel.getLastPageResult());
    }

    @Test
    public void retrievePageNegativeId() {
        int pageID = -1;
        retrievePageModel.retrievePageByID(pageID);
        Assert.assertNull(retrievePageModel.getLastPageResult());
    }

    @Test
    public void retrievePageNotify() {
        AtomicBoolean notified = new AtomicBoolean(false);
        int pageID = 30304;
        PageResult expectedPageResult = new PageResult(
                "The X-Files",
                pageID,
                "la mejor serie del mundo",
                0,
                new byte[0],
                "http://en.wikipedia.com/the_x_files"
        );
        when(apiHelper.retrievePage(pageID)).thenReturn(expectedPageResult);
        retrievePageModel.addEventListener(() -> { notified.set(true); });
        retrievePageModel.retrievePageByID(pageID);
        Assert.assertTrue(notified.get());
    }
}
