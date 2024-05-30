package models;

import models.repos.DataBase;

import java.util.ArrayList;
import java.util.Collection;

public class SaveModel {
    protected Collection<EventListener> listeners;

    public SaveModel() {
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

    public void savePage(String title, String text) {
        DataBase.saveInfo(title, text);  //Dont forget the ' sql problem TODO: qué es el ' sql problem?
        notifyListeners();
    }
}
