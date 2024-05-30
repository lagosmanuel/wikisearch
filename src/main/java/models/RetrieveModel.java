package models;

import models.repos.APIHelper;

import java.util.ArrayList;
import java.util.Collection;

public class RetrieveModel {
    Collection<EventListener> listeners;
    String lastResult; //TODO: esto está bien?

    public RetrieveModel() {
        listeners = new ArrayList<>();
    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    protected void notifyListeners() {
        for (EventListener listener:listeners) {
            listener.onEvent();
        }
    }

    public String getLastResult() {
        return lastResult;
    }

    public void retrievePage(String pageId) {
        lastResult = APIHelper.getInstance().retrievePage(pageId);
        notifyListeners();
    }
}
