package unit.pages;

import models.PageResult;
import models.pages.LoadPageModel;
import models.repos.databases.CatalogDataBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadPageModelTest {
    private LoadPageModel loadPageModel;
    private CatalogDataBase catalogDataBase;

    @Before
    public void setup() {
        catalogDataBase = mock(CatalogDataBase.class);
        loadPageModel = new LoadPageModel(catalogDataBase);
    }

    @Test
    public void loadPage() {
        String pageTitle = "The X-Files";
        PageResult expectedPageResult = new PageResult(
                pageTitle,
                30304,
                "la mejor serie del mundo",
                0,
                new byte[0],
                "http://en.wikipedia.com/the_x_files"
        );
        when(catalogDataBase.getPageResultByTitle(pageTitle)).thenReturn(expectedPageResult);
        loadPageModel.loadPage(pageTitle);
        Assert.assertEquals(expectedPageResult, loadPageModel.getLastResult());
    }

    @Test
    public void loadPageNullTitle() {
        loadPageModel.loadPage(null);
        Assert.assertNull(loadPageModel.getLastResult());
    }

    @Test
    public void loadPageNotify() {
        AtomicBoolean notified = new AtomicBoolean(false);
        String pageTitle = "The X-Files";
        PageResult expectedPageResult = new PageResult(
                pageTitle,
                30304,
                "la mejor serie del mundo",
                0,
                new byte[0],
                "http://en.wikipedia.com/the_x_files"
        );
        when(catalogDataBase.getPageResultByTitle(pageTitle)).thenReturn(expectedPageResult);
        loadPageModel.addEventListener(() -> notified.set(true));
        loadPageModel.loadPage(pageTitle);
        Assert.assertTrue(notified.get());
    }
}
