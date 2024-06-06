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
    public void retrievePage() {
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
        retrievePageModel.retrievePage(pageID);
        Assert.assertEquals(expectedPageResult, retrievePageModel.getLastResult());
    }

    @Test
    public void retrievePageNegativeId() {
        int pageID = -1;
        retrievePageModel.retrievePage(pageID);
        Assert.assertNull(retrievePageModel.getLastResult());
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
        retrievePageModel.retrievePage(pageID);
        Assert.assertTrue(notified.get());
    }
}
