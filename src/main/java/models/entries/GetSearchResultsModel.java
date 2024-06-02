package models.entries;

import models.BaseModel;
import models.SearchResult;
import models.repos.DataBase;
import utils.UIStrings;

import java.util.HashMap;
import java.util.Map;

public class GetSearchResultsModel extends BaseModel {
    private final Map<String, SearchResult> lastResults;
    private SearchResult currentResult;

    public GetSearchResultsModel() {
        lastResults = new HashMap<>();
    }

    public Map<String, SearchResult> getLastResults() {
        return lastResults;
    }

    public SearchResult getCurrentResult() {
        return currentResult;
    }

    public void getSavedSearchResultByTitle(String title) {
        currentResult = lastResults.get(title);
        notifyListeners(UIStrings.EVENTLISTENER_TOPIC_CURRENTRESULT);
    }

    public void getSavedSearchResults() {
        lastResults.clear();
        for (SearchResult result:DataBase.getSearchResults())
            lastResults.put(result.getTitle(), result);
        notifyListeners();
    }
}
