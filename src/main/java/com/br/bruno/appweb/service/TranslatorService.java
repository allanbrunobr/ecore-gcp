package com.br.bruno.appweb.service;

import com.google.cloud.translate.v3.DetectLanguageRequest;
import com.google.cloud.translate.v3.DetectLanguageResponse;
import com.google.cloud.translate.v3.GetSupportedLanguagesRequest;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.SupportedLanguage;
import com.google.cloud.translate.v3.SupportedLanguages;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * This class represents a service for translating text using Google Cloud Translation API.
 */
@Service
public class TranslatorService {

  /**
   * Representa a localização global.
   */
  public static final String GLOBAL = "global";
  @Value("${project.id}")
  private String projectId;

  /**
   * This method translates a text to a target language using Google Cloud Translation API.
   *
   * @param text The text to translate.
   * @param targetLanguageCode The target language code.
   * @return A list of translations.
   * @throws Exception If an error occurs during language detection.
   */
  @Async
  public CompletableFuture<List<Translation>> translateTextAsync(String text,
                                                                   String targetLanguageCode)
            throws Exception {

    try (TranslationServiceClient client = TranslationServiceClient.create()) {
      LocationName parent = LocationName.of(projectId, GLOBAL);

      TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setSourceLanguageCode(languageDetector(text))
                            .setTargetLanguageCode(targetLanguageCode)
                            .addContents(text)
                            .build();

      TranslateTextResponse response = client.translateText(request);

      return CompletableFuture.completedFuture(response.getTranslationsList());
    }
  }

  /**
   * Detects the language of a given text using the Google Cloud Language API.
   *
   * @param text The text whose language needs to be detected.
   * @return The language code of the detected language.
   * @throws Exception If an error occurs during language detection.
   */
  public String languageDetector(String text) throws Exception {
    try (TranslationServiceClient client = TranslationServiceClient.create()) {
      LocationName parent = LocationName.of(projectId, GLOBAL);
      DetectLanguageRequest request =
          DetectLanguageRequest.newBuilder()
                                .setParent(parent.toString())
                                .setMimeType("text/plain")
                                .setContent(text)
                                .build();
      DetectLanguageResponse response = client.detectLanguage(request);

      return response.getLanguagesList().get(0).getLanguageCode();
    }
  }

  /**
   * Retrieves the list of supported languages from the Google Cloud Translation API.
   *
   * @return A List containing the language codes of the supported languages.
   * @throws IOException if an error occurs while retrieving the supported languages.
   */
  public List<String> getSupportedLanguages() throws IOException {
    List<String> languages = new ArrayList<>();
    try (TranslationServiceClient client = TranslationServiceClient.create()) {
      LocationName parent = LocationName.of(projectId, GLOBAL);
      GetSupportedLanguagesRequest request =
              GetSupportedLanguagesRequest.newBuilder().setParent(parent.toString()).build();
      SupportedLanguages response = client.getSupportedLanguages(request);


      for (SupportedLanguage language : response.getLanguagesList()) {
        languages.add(language.getLanguageCode());
      }
    }
    return languages;
  }
}
