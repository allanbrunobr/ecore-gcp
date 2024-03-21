package com.br.bruno.appweb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapsController {

    @PostMapping("/maps")
    public void handleCoordinates(@RequestParam double latitude, @RequestParam double longitude) {

        System.out.println(latitude);
        System.out.println(longitude);

    }

    }


