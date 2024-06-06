package unit.pages;

import models.PageResult;
import models.pages.LoadSavedPageModel;
import models.repos.databases.CatalogDataBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadSavedPageModelTest {
    private LoadSavedPageModel loadSavedPageModel;
    private CatalogDataBase catalogDataBase;

    @Before
    public void setup() {
        catalogDataBase = mock(CatalogDataBase.class);
        loadSavedPageModel = new LoadSavedPageModel(catalogDataBase);
    }

    @Test
    public void loadPageByTitle() {
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
        loadSavedPageModel.loadPageByTitle(pageTitle);
        Assert.assertEquals(expectedPageResult, loadSavedPageModel.getLastPageResult());
    }

    @Test
    public void loadPageNullTitle() {
        loadSavedPageModel.loadPageByTitle(null);
        Assert.assertNull(loadSavedPageModel.getLastPageResult());
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
        loadSavedPageModel.addEventListener(() -> notified.set(true));
        loadSavedPageModel.loadPageByTitle(pageTitle);
        Assert.assertTrue(notified.get());
    }
}
