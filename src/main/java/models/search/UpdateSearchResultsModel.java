package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.databases.SearchResultDataBase;

public class UpdateSearchResultsModel extends BaseModel {
    private SearchResult lastSearchResult;

    public SearchResult getLastSearchResult() {
        return lastSearchResult;
    }

    public void updateSearchResult(SearchResult searchResult) {
        SearchResultDataBase.updateSearchResult(searchResult);
        lastSearchResult = searchResult;
        notifyListeners();
    }
}
