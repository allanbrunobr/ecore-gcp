package com.br.bruno.appweb.service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v2.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AnalyzeSentimentService {

    // Instantiate
    @Async
    public CompletableFuture<Sentiment> analyzeSentiment(String text) throws Exception {
        CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/keys/appubsub-admin-springboot-project-03da67dd523b.json")));
        LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(credentialsProvider)
                .build();

        try (LanguageServiceClient language = LanguageServiceClient.create(settings)) {
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
            if (sentiment == null) {
                throw new RuntimeException("Sentiment analysis failed.");
            }
            return CompletableFuture.completedFuture(sentiment);  // Return completed Future

        }
    }
}