package models.pages;

import models.BaseModel;
import models.repos.databases.CatalogDataBase;

public class DeletePageModel extends BaseModel {
    private final CatalogDataBase catalogDataBase;

    public DeletePageModel(CatalogDataBase catalogDataBase) {
        this.catalogDataBase = catalogDataBase;
    }

    public void deletePageByTitle(String title) {
        if (title != null) catalogDataBase.deletePageByTitle(title);
        notifyListeners();
    }
}
