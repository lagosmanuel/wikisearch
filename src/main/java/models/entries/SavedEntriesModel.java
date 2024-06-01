package models.entries;

import models.BaseModel;
import models.SearchResult;
import models.repos.DataBase;

import java.util.Collection;

public class SavedEntriesModel extends BaseModel {
    private Collection<SearchResult> lastResults;

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void getSavedEntries() {
        lastResults = DataBase.getEntries();
        for (SearchResult searchResult : lastResults)
            searchResult.setScore((int) (Math.random() * 10));
        notifyListeners();
    }
}
