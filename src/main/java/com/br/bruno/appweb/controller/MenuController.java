package com.br.bruno.appweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {


    @RequestMapping("/logout")
    public String logout() {
        return "index";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/user")
    public String createUser() {
        return "user/createUser";
    }

    @RequestMapping("/vision")
    public String vision() {
        return "ai/vision/gcloud-vision";
    }

    @RequestMapping("/sentiment")
    public String sentiment() {
        return "ai/sentiment-analysis/sentiment-analysis";
    }
}
