package com.br.bruno.appweb.config;

import com.br.bruno.appweb.events.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do EventBus.
 */
@Configuration
public class EventBusConfig {

  @Bean
    public EventBus eventBus() {
    return new EventBus();
  }
}