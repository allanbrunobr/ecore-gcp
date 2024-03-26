package com.br.bruno.appweb.interfaces;

/**
 * Interface para eventos.
 * Esta interface define um listener para eventos.
 */
public interface EventListener<T> {
  /**
   * Executed when an event occurs.
   *
   * @param event the event object
   */
  void onEvent(T event);
}