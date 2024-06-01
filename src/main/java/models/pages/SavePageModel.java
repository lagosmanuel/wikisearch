package models.pages;

import models.BaseModel;
import models.repos.DataBase;

public class SavePageModel extends BaseModel {
    public void savePage(String title, String text) {
        DataBase.saveInfo(title, text);  //Dont forget the ' sql problem TODO: qué es el ' sql problem?
        notifyListeners();
    }
}
