package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;

public class SavedTitlesModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;
    private Object[] lastResults;

    public SavedTitlesModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public void getSavedTitles() {
        lastResults = catalogDataBase.getPageTitles().stream().sorted().toArray();
        notifyListeners();
    }

    public Object[] getLastResults() {
        return lastResults;
    }
}
