package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;

public class SavedTitlesModel extends BaseModel {
    private Object[] lastResults;

    public void getSavedTitles() {
        lastResults = CatalogDataBase.getPageTitles().stream().sorted().toArray();
        notifyListeners();
    }

    public Object[] getLastResults() {
        return lastResults;
    }
}
