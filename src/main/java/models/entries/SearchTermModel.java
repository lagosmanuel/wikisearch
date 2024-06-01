package models.entries;

import models.BaseModel;
import models.SearchResult;
import models.repos.APIHelper;
import models.repos.DataBase;

import java.util.*;

public class SearchTermModel extends BaseModel {
    private Collection<SearchResult> lastResults;

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void searchTerm(String term) {
        lastResults = APIHelper.getInstance().searchTerm(term);
        for (SearchResult result: lastResults)
            result.setScore(DataBase.getScore(result.getPageID()));

        notifyListeners();
    }
}
