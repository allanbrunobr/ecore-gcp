package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.service.TranslatorService;
import com.google.cloud.translate.v3.Translation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class TranslatorController {

    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/translator")
    public ModelAndView translator() {
        return new ModelAndView("translator/translator");
    }
    @GetMapping("/languages")
    public ResponseEntity<List<String>> languages() throws IOException {
        List<String> languages = translatorService.
                getSupportedLanguages();
        return ResponseEntity.ok().body(languages);
    }


    @PostMapping("/translatorText")
    public ModelAndView executeTranslatorText(@RequestParam("textToTranslate") String textToTranslate,
                                              @RequestParam("targetLanguageCode") String targetLanguageCode) {
        ModelAndView modelAndView = new ModelAndView("translator/translator-result");
        StringBuilder translatedTextBuilder = new StringBuilder();

        try {
            CompletableFuture<List<Translation>> translatedTextAsync = translatorService.translateTextAsync(textToTranslate, targetLanguageCode);
            List<Translation> translatedTextList = translatedTextAsync.get();

            for (Translation translation : translatedTextList) {
                translatedTextBuilder.append(translation.getTranslatedText()).append(" ");
            }

            modelAndView.addObject("translatedResult", translatedTextBuilder);
        } catch (Exception e) {
            modelAndView.addObject("error", "Erro ao traduzir o texto: " + e.getMessage());
        }
        return modelAndView;
    }
}
