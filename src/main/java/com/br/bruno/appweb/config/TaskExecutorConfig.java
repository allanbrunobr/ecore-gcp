package com.br.bruno.appweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Bean
    @Primary
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Define o tamanho inicial do pool de threads
        executor.setMaxPoolSize(20); // Define o tamanho máximo do pool de threads
        executor.setQueueCapacity(100); // Define a capacidade da fila de espera para tarefas
        executor.setThreadNamePrefix("MyThread-"); // Define o prefixo dos nomes das threads
        executor.initialize(); // Inicializa o executor
        return executor;
    }
}

