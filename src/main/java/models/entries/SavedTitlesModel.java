package models.entries;

import models.BaseModel;
import models.repos.DataBase;

public class SavedTitlesModel extends BaseModel {
    private Object[] lastResults;

    public void getSavedTitles() {
        lastResults = DataBase.getTitles().stream().sorted().toArray();
        notifyListeners();
    }

    public Object[] getLastResults() {
        return lastResults;
    }
}
