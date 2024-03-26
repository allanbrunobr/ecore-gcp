package com.br.bruno.appweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsável por lidar com as requisições relacionadas ao menu da aplicação.
 * O MenuController gerencia as funcionalidades relacionadas à navegação e exibição de menus.
 */
@Controller
public class MenuController {


  /**
   * Logs out the currently logged in user.
   *
   * @return the name of the view to render after logging out
   */
  @RequestMapping("/logout")
  public String logout() {
    return "index";
  }

  /**
   * Returns the name of the view to render for the main page.
   *
   * @return the name of the view to render
   */
  @RequestMapping("/main")
  public String main() {
    return "main";
  }

  /**
   * Creates a user.
   *
   * <p>This method is responsible for rendering the view "user/createUser".
   *
   * @return the name of the view to render for creating a user
   */
  @RequestMapping("/user")
  public String createUser() {
    return "user/createUser";
  }

  /**
   * Executes the vision face detection functionality.
   *
   * @return the name of the view to render for vision face detection
   */
  @RequestMapping("/visionFaceDetection")
  public String visionFaceDetection() {
    return "ai/vision/vision-face";
  }

  /**
   * Executes the vision landmark detection functionality.
   *
   * @return the name of the view to render for vision landmark detection
   */
  @RequestMapping("/visionLandmarkDetection")
  public String visionLandmarkDetection() {
    return "ai/vision/vision-landmark";
  }

  /**
   * Retrieves the name of the view to render for the sentiment analysis page.
   *
   * <p>This method is responsible for rendering the view
   * "ai/sentiment-analysis/sentiment-analysis".
   *
   * @return the name of the view to render for sentiment analysis
   */
  @RequestMapping("/sentiment")
  public String sentiment() {
    return "ai/sentiment-analysis/sentiment-analysis";
  }
}
