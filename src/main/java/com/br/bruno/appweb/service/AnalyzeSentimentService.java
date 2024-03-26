package com.br.bruno.appweb.service;

import com.br.bruno.appweb.exceptions.SentimentAnalysisException;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v2.Document;
import com.google.cloud.language.v2.LanguageServiceClient;
import com.google.cloud.language.v2.LanguageServiceSettings;
import com.google.cloud.language.v2.Sentiment;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service to analyze sentiment of a text.
 */
@Service
public class AnalyzeSentimentService {

  private static final Logger logger = LoggerFactory.getLogger(AnalyzeSentimentService.class);

  /***  Analyze the sentiment of a text.
  *
  * @param text The text to analyze.
  * @return The sentiment of the text.
  * @throws Exception on errors while closing the client.
  */
  @Async
    public CompletableFuture<Sentiment> analyzeSentiment(String text) {
    try {
      CredentialsProvider credentialsProvider = getCredentialsProvider();
      LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
              .setCredentialsProvider(credentialsProvider)
              .build();

      try (LanguageServiceClient language = LanguageServiceClient.create(settings)) {
        Document doc = Document.newBuilder().setContent(text)
                .setType(Document.Type.PLAIN_TEXT).build();
        Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
        if (sentiment == null) {
          throw new SentimentAnalysisException("Sentiment analysis failed.");
        }
        return CompletableFuture.completedFuture(sentiment);
      }
    } catch (IOException e) {
      logger.error("Error loading credentials", e);
      throw new SentimentAnalysisException("Error loading credentials", e);
    }
  }

  /**
   * Returns a credentials provider for accessing Google Cloud services.
   *
   * @return A {@link CredentialsProvider} instance.
   * @throws IOException If an I/O error occurs while reading the credentials file.
   */
  private CredentialsProvider getCredentialsProvider() throws IOException {
    String credentialsFilePath =
            "src/main/resources/keys/appubsub-admin-springboot-project-03da67dd523b.json";
    GoogleCredentials credentials = GoogleCredentials
            .fromStream(new FileInputStream(credentialsFilePath));
    return FixedCredentialsProvider.create(credentials);
  }
}