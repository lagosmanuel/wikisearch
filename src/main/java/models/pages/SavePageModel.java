package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.databases.CatalogDataBase;

public class SavePageModel extends BaseModel {
    public void savePage(PageResult pageResult) {
        CatalogDataBase.updatePage(pageResult);
        notifyListeners();
    }
}
