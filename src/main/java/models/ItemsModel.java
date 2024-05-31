package models;

import models.repos.DataBase;

import java.util.Collection;

public class ItemsModel extends BaseModel {
    private Collection<SearchResult> lastResults;

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void getItems() {
        lastResults = DataBase.getItems();
        notifyListeners();
    }
}
