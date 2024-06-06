package unit.pages;

import models.pages.SavedPageTitlesModel;
import models.repos.databases.CatalogDataBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SavedPageTitlesModelTest {
    private SavedPageTitlesModel savedPageTitlesModel;
    private CatalogDataBase catalogDataBase;

    @Before
    public void setup() {
        catalogDataBase = mock(CatalogDataBase.class);
        savedPageTitlesModel = new SavedPageTitlesModel(catalogDataBase);
    }

    @Test
    public void getSavedPageTitles() {
        Collection<String> savedTitlesList = new LinkedList<>();
        String expectedSearchResult = "The X-Files";
        savedTitlesList.add(expectedSearchResult);
        when(catalogDataBase.getPageTitles()).thenReturn(savedTitlesList);
        savedPageTitlesModel.getSavedPageTitles();
        Assert.assertEquals(expectedSearchResult, savedPageTitlesModel.getLastTitleResults().iterator().next());
    }

    @Test
    public void getSavedPageTitlesEmpty() {
        when(catalogDataBase.getPageTitles()).thenReturn(new LinkedList<>());
        savedPageTitlesModel.getSavedPageTitles();
        Assert.assertEquals(0, savedPageTitlesModel.getLastTitleResults().size());
    }

    @Test
    public void getSavedPageTitlesNotify() {
        AtomicBoolean notified = new AtomicBoolean(false);
        savedPageTitlesModel.addEventListener(() -> notified.set(true));
        savedPageTitlesModel.getSavedPageTitles();
        Assert.assertTrue(notified.get());
    }
}
