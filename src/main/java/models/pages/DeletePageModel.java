package models.pages;

import models.BaseModel;
import models.repos.DataBase;

public class DeletePageModel extends BaseModel {
    public void deletePage(String title) {
        DataBase.deleteEntry(title);
        notifyListeners();
    }
}
