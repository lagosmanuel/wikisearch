package models.entries;

import models.BaseModel;
import models.SearchResult;
import models.repos.DataBase;

import java.util.HashMap;
import java.util.Map;

public class GetSearchResultsModel extends BaseModel {
    private final Map<String, SearchResult> lastResults;
    private SearchResult currentResult;

    public GetSearchResultsModel() {
        lastResults = new HashMap<>();
    }

    public Map<String, SearchResult> getLastResults() {
        return lastResults;
    }

    public SearchResult getCurrentResult() {
        return currentResult;
    }

    public void getSavedEntryByTitle(String title) {
        currentResult = lastResults.get(title);
        notifyListeners("single");
    }

    public void getSavedEntries() {
        lastResults.clear();

        for (SearchResult result:DataBase.getSearchResults()) {
            lastResults.put(result.getTitle(), result);
        }

        notifyListeners();
    }
}