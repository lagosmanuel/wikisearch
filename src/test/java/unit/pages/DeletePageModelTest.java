package unit.pages;

import models.PageResult;
import models.pages.DeletePageModel;
import models.repos.databases.CatalogDataBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;

public class DeletePageModelTest {
    private DeletePageModel deletePageModel;
    private CatalogDataBase catalogDataBase;

    @Before
    public void setup() {
        catalogDataBase = mock(CatalogDataBase.class);
        deletePageModel = new DeletePageModel(catalogDataBase);
    }

    @Test
    public void deletePageByTitle() {
        AtomicBoolean notified = new AtomicBoolean(false);
        PageResult pageResult = new PageResult(
                "The X-Files",
                30304,
                "la mejor serie del mundo",
                0,
                new byte[0],
                "http://en.wikipedia.com/the_x_files"
        );
        deletePageModel.addEventListener(() -> notified.set(true));
        deletePageModel.deletePageByTitle(pageResult.getTitle());
        Assert.assertTrue(notified.get());
    }

    @Test
    public void deletePageNullTitle() {
        AtomicBoolean notified = new AtomicBoolean(false);
        deletePageModel.addEventListener(() -> notified.set(true));
        deletePageModel.deletePageByTitle(null);
        Assert.assertTrue(notified.get());
    }
}
