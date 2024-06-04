package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.APIHelper;
import models.repos.databases.SearchResultDataBase;

import java.util.*;

public class SearchTermModel extends BaseModel {
    private Collection<SearchResult> lastSearchResults;

    public Collection<SearchResult> getLastSearchResults() {
        return lastSearchResults;
    }

    public void searchTerm(String term) {
        lastSearchResults = APIHelper.getInstance().searchTerm(term);
        for (SearchResult searchResult:lastSearchResults) {
            SearchResult savedSearchResult = SearchResultDataBase.getSearchResultByTitle(searchResult.getTitle());
            if (savedSearchResult != null) searchResult.setScore(savedSearchResult.getScore());
        }
        notifyListeners();
    }
}
