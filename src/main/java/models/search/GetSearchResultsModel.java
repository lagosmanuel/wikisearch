package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;

import java.util.ArrayList;
import java.util.Collection;

public class GetSearchResultsModel extends BaseModel {
    private final Collection<SearchResult> lastResults;

    public GetSearchResultsModel() {
        lastResults = new ArrayList<>();
    }

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void getSavedSearchResults() {
        lastResults.clear();
        for (SearchResult result: SearchResultDataBase.getSearchResults())
            lastResults.add(result);
        notifyListeners();
    }
}
