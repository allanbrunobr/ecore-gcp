package com.br.bruno.appweb.events;

import com.br.bruno.appweb.interfaces.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private Map<Class<?>, List<EventListener>> subscribers;

    public EventBus() {
        this.subscribers = new HashMap<>();
    }

    public <T> void subscribe(Class<T> eventType, EventListener<T> listener) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T> void publish(T event) {
        List<EventListener> listeners = subscribers.get(event.getClass());
        if (listeners != null) {
            for (EventListener listener : listeners) {
                listener.onEvent(event);
            }
        }
    }
}