package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;
import java.util.Collection;

public class SavedPageTitlesModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;
    private Collection<String> lastTitleResults;

    public SavedPageTitlesModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public void getSavedPageTitles() {
        lastTitleResults = catalogDataBase.getPageTitles();
        notifyListeners();
    }

    public Collection<String> getLastTitleResults() {
        return lastTitleResults;
    }
}
