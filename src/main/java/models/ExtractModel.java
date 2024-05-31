package models;

import models.repos.DataBase;

public class ExtractModel extends BaseModel {
    private String lastExtract;

    public String getLastExtract() {
        return lastExtract;
    }

    public void extract(String title) {
        lastExtract = DataBase.getExtract(title);
        notifyListeners();
    }
}
