package models;

import utils.UIStrings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseModel {
    private final Map<String, Collection<EventListener>> listeners;

    public BaseModel() {
        listeners = new HashMap<>();
    }

    public void addEventListener(EventListener listener) {
        if (!listeners.containsKey(UIStrings.EVENTLISTENER_TOPIC_DEFAULT)) listeners.put(UIStrings.EVENTLISTENER_TOPIC_DEFAULT, new ArrayList<>());
        listeners.get(UIStrings.EVENTLISTENER_TOPIC_DEFAULT).add(listener);
    }

    public void addEventListener(EventListener listener, String topic) {
        if (!listeners.containsKey(topic)) listeners.put(topic, new ArrayList<>());
        listeners.get(topic).add(listener);
    }

    public void notifyListeners(String topic) {
        if (!listeners.containsKey(topic)) return;
        for (EventListener listener:listeners.get(topic)) listener.onEvent();
    }

    public void notifyListeners() {
        for (EventListener listener:listeners.get(UIStrings.EVENTLISTENER_TOPIC_DEFAULT)) listener.onEvent();
    }
}
