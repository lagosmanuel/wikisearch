package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.databases.CatalogDataBase;

public class LoadSavedPageModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;
    private PageResult lastPageResult;

    public LoadSavedPageModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public PageResult getLastPageResult() {
        return lastPageResult;
    }

    public void loadPageByTitle(String title) {
        lastPageResult = title != null? catalogDataBase.getPageResultByTitle(title):null;
        notifyListeners();
    }
}
