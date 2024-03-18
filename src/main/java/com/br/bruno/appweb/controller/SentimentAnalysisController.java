package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.models.sentiment.SentimentDescription;
import com.br.bruno.appweb.service.AnalyzeSentimentService;
import com.google.cloud.language.v2.Sentiment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.CompletableFuture;

@RestController
public class SentimentAnalysisController {

    private final AnalyzeSentimentService analyzeSentimentService;

    public SentimentAnalysisController(AnalyzeSentimentService analyzeSentimentService) {
        this.analyzeSentimentService = analyzeSentimentService;
    }

    @PostMapping("/sentimentAnalysis")
    public ModelAndView sentimentAnalysis(@RequestParam("dados") String dados) {
        ModelAndView modelAndView = new ModelAndView("ai/sentiment-analysis/sentiment-analysis-result");
        try {
            CompletableFuture<Sentiment> sentimentFuture = analyzeSentimentService.analyzeSentiment(dados);
            Sentiment sentiment = sentimentFuture.get(); // Espera pelo resultado

            SentimentDescription sentimentDescription = new SentimentDescription();
            String description = sentimentDescription.getSentimentDescription(sentiment.getScore(), sentiment.getMagnitude());

            modelAndView.addObject("sentiment", sentiment);
            modelAndView.addObject("description", description);
        } catch (Exception e) {
        }
        return modelAndView;
    }


 }

