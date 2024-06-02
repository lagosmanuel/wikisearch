package models.entries;

import models.BaseModel;
import models.SearchResult;
import models.repos.DataBase;

import java.util.HashMap;
import java.util.Map;

public class GetSearchResultsModel extends BaseModel {
    private final Map<String, SearchResult> lastResults;

    public GetSearchResultsModel() {
        lastResults = new HashMap<>();
    }

    public Map<String, SearchResult> getLastResults() {
        return lastResults;
    }

    public SearchResult getLastResultByTitle(String title) {
        return lastResults.get(title);
    }

    public void getSavedEntries() {
        lastResults.clear();

        for (SearchResult result:DataBase.getSearchResults()) {
            lastResults.put(result.getTitle(), result);
        }

        notifyListeners();
    }
}
