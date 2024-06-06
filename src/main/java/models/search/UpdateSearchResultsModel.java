package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;

public class UpdateSearchResultsModel extends BaseModel {
    private final SearchResultDataBase searchResultDataBase;
    private SearchResult lastUpdatedSearchResult;

    public UpdateSearchResultsModel(SearchResultDataBase searchResultDataBase) {
        this.searchResultDataBase = searchResultDataBase;
    }

    public SearchResult getLastUpdatedSearchResult() {
        return lastUpdatedSearchResult;
    }

    public void updateSearchResult(SearchResult searchResult) {
        lastUpdatedSearchResult = searchResult;
        if (searchResult != null) searchResultDataBase.updateSearchResult(searchResult);
        notifyListeners();
    }
}
