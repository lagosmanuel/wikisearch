package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;

public class DeletePageModel extends BaseModel {
    public void deletePage(String title) {
        CatalogDataBase.deletePageByTitle(title);
        notifyListeners();
    }
}
