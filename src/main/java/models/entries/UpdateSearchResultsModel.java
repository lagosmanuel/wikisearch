package models.entries;

import models.BaseModel;
import models.repos.DataBase;

public class UpdateSearchResultsModel extends BaseModel {

    public void changeScore(String title, int score) {
        DataBase.changeScore(title, score);
        notifyListeners();
    }
}
