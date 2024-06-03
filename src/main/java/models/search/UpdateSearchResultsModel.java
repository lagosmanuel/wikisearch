package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;

public class UpdateSearchResultsModel extends BaseModel {
    private SearchResult lastUpdatedSearchResult;

    public SearchResult getLastUpdatedSearchResult() {
        return lastUpdatedSearchResult;
    }

    public void updateSearchResult(SearchResult searchResult) {
        SearchResultDataBase.updateSearchResult(searchResult);
        lastUpdatedSearchResult = searchResult;
        notifyListeners();
    }
}
