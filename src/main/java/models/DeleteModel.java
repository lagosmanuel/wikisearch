package models;

import models.repos.DataBase;

public class DeleteModel extends BaseModel {
    public void deletePage(String pageTitle) {
        DataBase.deleteEntry(pageTitle);
        notifyListeners();
    }
}
