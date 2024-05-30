package models;

import models.repos.APIHelper;

import java.util.*;

public class SearchModel {
    Collection<EventListener> listeners;
    Collection<SearchResult> lastResults; //TODO: esto está bien?

    public SearchModel() {
        lastResults = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (EventListener listener: listeners) {
            listener.onEvent();
        }
    }

    public Collection<SearchResult> getLastResults() {
        return lastResults;
    }

    public void searchTerm(String term) {
        lastResults = APIHelper.getInstance().searchTerm(term);
        notifyListeners();
    }
}
