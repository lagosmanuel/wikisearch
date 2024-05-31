package models;

import models.repos.DataBase;

import java.util.ArrayList;
import java.util.Collection;

public class ExtractModel {
    private final Collection<EventListener> listeners;
    private String lastExtract;

    public ExtractModel() {
        listeners = new ArrayList<>();
    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (EventListener listener:listeners) {
            listener.onEvent();
        }
    }

    public String getLastExtract() {
        return lastExtract;
    }

    public void extract(String title) {
        lastExtract = DataBase.getExtract(title);
        notifyListeners();
    }
}
