package models;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseModel {
    private final Collection<EventListener> listeners;

    public BaseModel() {
        listeners = new ArrayList<>();
    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (EventListener listener : listeners) {
            listener.onEvent();
        }
    }
}
