package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;

public class DeletePageModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;

    public DeletePageModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public void deletePage(String title) {
        catalogDataBase.deletePageByTitle(title);
        notifyListeners();
    }
}
