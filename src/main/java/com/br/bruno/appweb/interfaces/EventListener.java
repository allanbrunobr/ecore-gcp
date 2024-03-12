package com.br.bruno.appweb.interfaces;

public interface EventListener<T> {
    void onEvent(T event);
}