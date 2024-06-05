package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.apis.APIHelper;
import models.repos.databases.SearchResultDataBase;

import java.util.*;

public class SearchTermModel extends BaseModel {
    private final SearchResultDataBase searchResultDataBase;
    private final APIHelper apiHelper;
    private Collection<SearchResult> lastSearchResults;

    public SearchTermModel(SearchResultDataBase searchResultDataBase, APIHelper apiHelper) {
        this.searchResultDataBase = searchResultDataBase;
        this.apiHelper = apiHelper;
    }

    public Collection<SearchResult> getLastSearchResults() {
        return lastSearchResults;
    }

    public void searchTerm(String term) {
        lastSearchResults = apiHelper.searchTerm(term);
        for (SearchResult searchResult:lastSearchResults) {
            SearchResult savedSearchResult = searchResultDataBase.getSearchResultByTitle(searchResult.getTitle());
            if (savedSearchResult != null) searchResult.setScore(savedSearchResult.getScore());
        }
        notifyListeners();
    }
}
