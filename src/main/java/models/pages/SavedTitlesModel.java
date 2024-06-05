package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;

import java.util.Collection;

public class SavedTitlesModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;
    private Collection<String> lastResults;

    public SavedTitlesModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public void getSavedTitles() {
        lastResults = catalogDataBase.getPageTitles();
        notifyListeners();
    }

    public Collection<String> getLastResults() {
        return lastResults;
    }
}
