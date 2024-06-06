package unit.pages;

import models.PageResult;
import models.pages.SavePageModel;
import models.repos.databases.CatalogDataBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;

public class SavePageModelTest {
    private SavePageModel savePageModel;
    private CatalogDataBase catalogDataBase;

    @Before
    public void setup() {
        catalogDataBase = mock(CatalogDataBase.class);
        savePageModel = new SavePageModel(catalogDataBase);
    }

    @Test
    public void savePage() {
        AtomicBoolean notified = new AtomicBoolean(false);
        PageResult expectedPageResult = new PageResult(
                "The X-Files",
                30304,
                "la mejor serie del mundo",
                0,
                new byte[0],
                "http://en.wikipedia.com/the_x_files"
        );
        savePageModel.addEventListener(() -> notified.set(true));
        savePageModel.savePage(expectedPageResult);
        Assert.assertTrue(notified.get());
    }

    @Test
    public void saveNullPage() {
        AtomicBoolean notified = new AtomicBoolean(false);
        savePageModel.addEventListener(() -> notified.set(true));
        savePageModel.savePage(null);
        Assert.assertTrue(notified.get());
    }
}
