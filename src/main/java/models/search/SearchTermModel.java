package models.search;

import models.BaseModel;
import models.SearchResult;
import models.repos.APIHelper;
import models.repos.databases.SearchResultDataBase;

import java.util.*;

public class SearchTermModel extends BaseModel {
    private Collection<SearchResult> lastResults;

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void searchTerm(String term) {
        lastResults = APIHelper.getInstance().searchTerm(term);
        for (SearchResult result: lastResults) {
            SearchResult searchResult = SearchResultDataBase.getSearchResultByTitle(result.getTitle());
            if (searchResult != null) result.setScore(searchResult.getScore());
        }
        notifyListeners();
    }
}
