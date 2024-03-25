package com.br.bruno.appweb.models.sentiment;

import lombok.NoArgsConstructor;

/**
 * Represents a class that provides sentiment description based on a given score and magnitude.
 */
@NoArgsConstructor
public class SentimentDescription {

  /**
   * Retrieves the description of a sentiment based on the given score and magnitude.
   *
   * @param score The sentiment score, between -1 and 1.
   * @param magnitude The sentiment magnitude, between 0 and infinity.
   * @return The description of the sentiment.
   */
  public String getSentimentDescription(double score, double magnitude) {
    String scoreDescription;
    String magnitudeDescription;

    if (score >= -1 && score < -0.5) {
      scoreDescription = "muito negativo";
    } else if (score >= -0.5 && score < 0) {
      scoreDescription = "negativo";
    } else if (score == 0) {
      scoreDescription = "neutro";
    } else if (score > 0 && score <= 0.5) {
      scoreDescription = "positivo";
    } else {
      scoreDescription = "muito positivo";
    }

    if (magnitude >= 0 && magnitude < 0.5) {
      magnitudeDescription = "fraca";
    } else if (magnitude >= 0.5 && magnitude < 1) {
      magnitudeDescription = "moderada";
    } else if (magnitude >= 1 && magnitude < 2) {
      magnitudeDescription = "forte";
    } else if (magnitude >= 2 && magnitude < 3) {
      magnitudeDescription = "muito Forte";
    } else {
      magnitudeDescription = "Extremamente Forte";
    }

    return "O sentimento expresso é "
                + scoreDescription
                + " com uma intensidade "
                + magnitudeDescription + ".";
  }
}
