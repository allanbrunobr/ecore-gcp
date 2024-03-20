package com.br.bruno.appweb.interfaces;

/***
 * Interface para eventos.
 *
 * @param <T> Tipo do evento.
 */
public interface EventListener<T> {
    void onEvent(T event);
}