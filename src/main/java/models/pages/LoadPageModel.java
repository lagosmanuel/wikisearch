package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.databases.CatalogDataBase;

public class LoadPageModel extends BaseModel {
    private PageResult lastResult;

    public PageResult getLastResult() {
        return lastResult;
    }

    public void getPageExtract(String title) {
        lastResult = CatalogDataBase.getPageByTitle(title);
        notifyListeners();
    }
}
