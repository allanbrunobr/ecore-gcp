package com.br.bruno.appweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe principal da aplicação.
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class AppWebApplication {

    /**
     * Executes the main application.
     *
     * @param args the command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }

}


