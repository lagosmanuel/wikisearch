package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;

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

    public void getSavedSearchResults() {
        lastResults.clear();
        for (SearchResult result: SearchResultDataBase.getSearchResults())
            lastResults.put(result.getTitle(), result);
        notifyListeners();
    }
}
