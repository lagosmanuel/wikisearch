package models;

import models.repos.DataBase;

import java.util.ArrayList;
import java.util.Collection;

public class DeleteModel {
    private final Collection<EventListener> listeners;

    public DeleteModel() {
        listeners = new ArrayList<>();
    }

    public void addEventLister(EventListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (EventListener listener : listeners) {
            listener.onEvent();
        }
    }

    public void deletePage(String pageTitle) {
        DataBase.deleteEntry(pageTitle);
        notifyListeners();
    }
}