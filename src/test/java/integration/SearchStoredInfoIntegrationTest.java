package integration;

import models.PageResult;
import models.pages.*;
import models.repos.apis.APIHelper;
import models.repos.databases.CatalogDataBase;
import models.repos.databases.SearchResultDataBase;
import models.search.SearchTermModel;
import models.search.UpdateSearchResultsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import presenters.SearchPresenter;
import presenters.StoredInfoPresenter;
import views.MainView;
import views.SearchView;
import views.StoredInfoView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchStoredInfoIntegrationTest {
    private MainView mainView;
    private APIHelper apiHelper;
    private SearchResultDataBase searchResultDataBase;
    private CatalogDataBase catalogDataBase;
    private StoredInfoPresenter storedInfoPresenter;
    private SearchPresenter searchPresenter;
    private SearchView searchView;
    private StoredInfoView storedInfoView;
    private SearchTermModel searchTermModel;
    private RetrievePageModel retrievePageModel;
    private SavePageModel savePageModel;
    private DeletePageModel deletePageModel;
    private UpdateSearchResultsModel updateSearchResultsModel;
    private LoadSavedPageModel loadSavedPageModel;
    private SavedPageTitlesModel savedPageTitlesModel;

    @Before
    public void setup() {
        mainView = mock(MainView.class);
        catalogDataBase = mock(CatalogDataBase.class);
        searchResultDataBase = mock(SearchResultDataBase.class);;
        apiHelper = mock(APIHelper.class);
        savePageModel = new SavePageModel(catalogDataBase);
        deletePageModel = new DeletePageModel(catalogDataBase);
        loadSavedPageModel = new LoadSavedPageModel(catalogDataBase);
        savedPageTitlesModel = new SavedPageTitlesModel(catalogDataBase);
        retrievePageModel = new RetrievePageModel(apiHelper);
        searchTermModel = new SearchTermModel(searchResultDataBase, apiHelper);
        updateSearchResultsModel = new UpdateSearchResultsModel(searchResultDataBase);
        searchView = new SearchView();
        searchPresenter = new SearchPresenter(searchTermModel, retrievePageModel, savePageModel, updateSearchResultsModel);
        searchPresenter.setSearchView(searchView);;
        storedInfoView = new StoredInfoView();
        storedInfoPresenter = new StoredInfoPresenter(savePageModel, deletePageModel, loadSavedPageModel, savedPageTitlesModel);
        storedInfoPresenter.setStoredInfoView(storedInfoView);
    }

    @Test
    public void loadTitles() throws InterruptedException {
        String title = "foo";
        List<String> titlesLists = new ArrayList<>();
        titlesLists.add(title);
        when(catalogDataBase.getPageTitles()).thenReturn(titlesLists);

        PageResult pageResult = new PageResult(title, 1, "bar", 0, null, "");
        when(catalogDataBase.getPageResultByTitle(title)).thenReturn(pageResult);

        savedPageTitlesModel.getSavedPageTitles();
        Thread.sleep(500);

        Assert.assertEquals(title, storedInfoView.getSelectedItem());
    }

    @Test
    public void loadPage() throws InterruptedException {
        String title = "foo";
        String extract = "bar";
        PageResult pageResult = new PageResult(title, 1, extract, 0, null, "");
        when(catalogDataBase.getPageResultByTitle(title)).thenReturn(pageResult);

        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        comboBoxModel.addElement(title);
        storedInfoView.getComboBox().setModel(comboBoxModel);
        storedInfoPresenter.onSelectedItem();
        Thread.sleep(500);

        Assert.assertTrue(storedInfoView.getText().contains(extract));
    }
}
