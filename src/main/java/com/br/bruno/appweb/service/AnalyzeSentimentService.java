package com.br.bruno.appweb.service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v2.Document;
import com.google.cloud.language.v2.LanguageServiceClient;
import com.google.cloud.language.v2.LanguageServiceSettings;
import com.google.cloud.language.v2.Sentiment;
import java.io.FileInputStream;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service to analyze sentiment of a text.
 */
@Service
public class AnalyzeSentimentService {

  /***  Analyze the sentiment of a text.
  *
  * @param text The text to analyze.
  * @return The sentiment of the text.
  * @throws Exception on errors while closing the client.
  */
  @Async
    public CompletableFuture<Sentiment> analyzeSentiment(String text) throws Exception {
    CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(GoogleCredentials
                .fromStream(
                        new FileInputStream(
                          "src/main/resources/keys/"
                                  + "appubsub-admin-springboot-project-03da67dd523b.json")));
    LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(credentialsProvider)
                .build();

    try (LanguageServiceClient language = LanguageServiceClient.create(settings)) {
      Document doc = Document.newBuilder().setContent(text)
              .setType(Document.Type.PLAIN_TEXT).build();
      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
      if (sentiment == null) {
        throw new RuntimeException("Sentiment analysis failed.");
      }
      return CompletableFuture.completedFuture(sentiment);
    }
  }
}