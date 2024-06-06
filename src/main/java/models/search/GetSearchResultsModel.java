package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;
import java.util.ArrayList;
import java.util.Collection;

public class GetSearchResultsModel extends BaseModel {
    private final SearchResultDataBase searchResultDataBase;
    private final Collection<SearchResult> lastResults;

    public GetSearchResultsModel(SearchResultDataBase searchResultDataBase) {
        this.searchResultDataBase = searchResultDataBase;
        lastResults = new ArrayList<>();
    }

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void getSavedSearchResults() {
        lastResults.clear();
        Collection<SearchResult> searchResults = searchResultDataBase.getSearchResults();
        if (searchResults != null) lastResults.addAll(searchResults);
        notifyListeners();
    }
}
