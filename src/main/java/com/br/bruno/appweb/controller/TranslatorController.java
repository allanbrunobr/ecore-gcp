package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.service.TranslatorService;
import com.google.cloud.translate.v3.Translation;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * This class represents a controller for handling translation-related requests.
 */
@RestController
public class TranslatorController {

  private final TranslatorService translatorService;

  public TranslatorController(TranslatorService translatorService) {
    this.translatorService = translatorService;
  }

  /**
   * Returns a ModelAndView object for the "/translator" endpoint.
   * The ModelAndView object contains the view name "translator/translator".
   *
   * @return a ModelAndView object for the "/translator" endpoint
   */
  @GetMapping("/translator")
  public ModelAndView translator() {
    return new ModelAndView("translator/translator");
  }

  /**
   *    * This method returns a ResponseEntity object containing a list of supported languages.
   * It is responsible for handling the "/languages" endpoint.
   *
   * @return a ResponseEntity object containing a list of supported languages
   * @throws IOException if an error occurs while retrieving the supported languages
   */
  @GetMapping("/languages")
  public ResponseEntity<List<String>> languages() throws IOException {
    List<String> languages = translatorService
               .getSupportedLanguages();
    return ResponseEntity.ok().body(languages);
  }

  /**
   * Executes the translation of the given text to the specified target language.
   *
   * @param textToTranslate The text to be translated.
   * @param targetLanguageCode The target language code.
   * @return A ModelAndView object representing the translation result page.
   */
  @PostMapping("/translatorText")
  public ModelAndView executeTranslatorText(
                    @RequestParam("textToTranslate") String textToTranslate,
                    @RequestParam("targetLanguageCode") String targetLanguageCode) {
    ModelAndView modelAndView = new ModelAndView("translator/translator-result");
    StringBuilder translatedTextBuilder = new StringBuilder();
    try {
      CompletableFuture<List<Translation>> translatedTextAsync =
              translatorService.translateTextAsync(textToTranslate, targetLanguageCode);
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
