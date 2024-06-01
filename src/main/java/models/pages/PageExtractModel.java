package models.pages;

import models.BaseModel;
import models.repos.DataBase;

public class PageExtractModel extends BaseModel {
    private String lastResult;

    public String getLastResult() {
        return lastResult;
    }

    public void getPageExtract(String title) {
        lastResult = DataBase.getExtract(title);
        notifyListeners();
    }
}
