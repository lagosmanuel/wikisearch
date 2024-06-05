package models.repos.databases;

import models.SearchResult;
import java.util.Collection;

public interface SearchResultDataBase {
    SearchResult getSearchResultByTitle(String title);
    Collection<SearchResult> getSearchResults();
    void updateSearchResult(SearchResult searchResult);
}
