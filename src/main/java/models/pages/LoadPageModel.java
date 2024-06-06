package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.databases.CatalogDataBase;

public class LoadPageModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;
    private PageResult lastResult;

    public LoadPageModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public PageResult getLastResult() {
        return lastResult;
    }

    public void loadPage(String title) {
        lastResult = title != null? catalogDataBase.getPageResultByTitle(title):null;
        notifyListeners();
    }
}
