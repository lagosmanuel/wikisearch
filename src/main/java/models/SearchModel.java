package models;

import models.repos.APIHelper;
import models.repos.DataBase;

import java.util.*;

public class SearchModel extends BaseModel {
    private Collection<SearchResult> lastResults; //TODO: esto está bien?

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void searchTerm(String term) {
        lastResults = APIHelper.getInstance().searchTerm(term);
        // #TODO: esta mal recorrerlo de nuevo?
        for (SearchResult result: lastResults)
            result.setScore(DataBase.getScore(result.getPageID()));

        notifyListeners();
    }
}
