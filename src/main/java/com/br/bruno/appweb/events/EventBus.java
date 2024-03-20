package com.br.bruno.appweb.events;

import com.br.bruno.appweb.interfaces.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventBus is a simple event bus that allows objects to publish events and subscribe to them.
 */
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