package com.br.bruno.appweb.interfaces;

/**
 * Interface para eventos.
 * Esta interface define um listener para eventos.
 */
public interface EventListener<T> {
  void onEvent(T event);
}