package com.br.bruno.appweb.service;

import com.google.cloud.language.v2.LanguageServiceClient;
import com.google.cloud.translate.v3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TranslatorService {

    @Value("${project.id}")
    private String projectId;
    @Async
    public CompletableFuture<List<Translation>> translateTextAsync(String text, String targetLanguageCode) throws Exception {

        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, "global");

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

            public String languageDetector(String text) throws Exception {
            try (TranslationServiceClient client = TranslationServiceClient.create()) {
                LocationName parent = LocationName.of(projectId, "global");
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

    public List<String> getSupportedLanguages() throws IOException {
        List<String> languages = new ArrayList<>();
        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, "global");
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

