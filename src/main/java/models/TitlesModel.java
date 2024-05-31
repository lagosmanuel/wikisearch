package models;

import models.repos.DataBase;

public class TitlesModel extends BaseModel {
    private Object[] lastResult;

    public void getTitles() {
        lastResult = DataBase.getTitles().stream().sorted().toArray();
        notifyListeners();
    }

    public Object[] getLastResult() {
        return lastResult;
    }
}
