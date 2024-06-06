package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;
import java.util.ArrayList;
import java.util.Collection;

public class GetSearchResultsModel extends BaseModel {
    private final SearchResultDataBase searchResultDataBase;
    private final Collection<SearchResult> lastSearchResults;

    public GetSearchResultsModel(SearchResultDataBase searchResultDataBase) {
        this.searchResultDataBase = searchResultDataBase;
        lastSearchResults = new ArrayList<>();
    }

    public Collection<SearchResult> getLastSearchResults() {
        return lastSearchResults;
    }

    public void getSavedSearchResults() {
        lastSearchResults.clear();
        Collection<SearchResult> searchResults = searchResultDataBase.getSearchResults();
        if (searchResults != null) lastSearchResults.addAll(searchResults);
        notifyListeners();
    }
}
