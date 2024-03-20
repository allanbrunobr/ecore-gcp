package com.br.bruno.appweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MenuController
 */
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

    @RequestMapping("/visionFaceDetection")
    public String visionFaceDetection() {
        return "ai/vision/vision-face";
    }

    @RequestMapping("/visionLandmarkDetection")
    public String visionLandmarkDetection() {
        return "ai/vision/vision-landmark";
    }

    @RequestMapping("/sentiment")
    public String sentiment() {
        return "ai/sentiment-analysis/sentiment-analysis";
    }
}
