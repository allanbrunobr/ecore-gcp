package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.models.sentiment.SentimentDescription;
import com.br.bruno.appweb.service.AnalyzeSentimentService;
import com.google.cloud.language.v2.Sentiment;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



/**
 * Controller class for sentiment analysis.
 */
@RestController
public class SentimentAnalysisController {

  private final AnalyzeSentimentService analyzeSentimentService;

  public SentimentAnalysisController(AnalyzeSentimentService analyzeSentimentService) {
    this.analyzeSentimentService = analyzeSentimentService;
  }

  /**
   * Analyzes the sentiment of a given text.
   *
   * @param dados The text to analyze.
   * @return The ModelAndView object that contains the sentiment analysis result.
   */
  @PostMapping("/sentimentAnalysis")
  public ModelAndView sentimentAnalysis(@RequestParam("dados") String dados) {
    ModelAndView modelAndView = new ModelAndView(
                "ai/sentiment-analysis/sentiment-analysis-result");
    try {
      CompletableFuture<Sentiment> sentimentFuture = analyzeSentimentService
              .analyzeSentiment(dados);
      Sentiment sentiment = sentimentFuture.get();

      SentimentDescription sentimentDescription = new SentimentDescription();
      String description = sentimentDescription
              .getSentimentDescription(sentiment.getScore(), sentiment.getMagnitude());

      modelAndView.addObject("sentiment", sentiment);
      modelAndView.addObject("description", description);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    return modelAndView;
  }
}

