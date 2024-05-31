package models;

import models.repos.DataBase;

public class SaveModel extends BaseModel {
    public void savePage(String title, String text) {
        DataBase.saveInfo(title, text);  //Dont forget the ' sql problem TODO: qué es el ' sql problem?
        notifyListeners();
    }
}
