package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.databases.CatalogDataBase;

public class SavePageModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;

    public SavePageModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public void savePage(PageResult pageResult) {
        catalogDataBase.updatePage(pageResult);
        notifyListeners();
    }
}
