package models.repos.apis;

import models.PageResult;
import models.SearchResult;
import java.util.Collection;

public interface APIHelper {
    Collection<SearchResult> searchTerm(String term);
    PageResult retrievePage(int pageId);
}
