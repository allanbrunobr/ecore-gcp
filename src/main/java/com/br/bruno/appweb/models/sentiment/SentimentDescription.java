package com.br.bruno.appweb.models.sentiment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SentimentDescription {

    public String getSentimentDescription(double score, double magnitude) {
        String scoreDescription;
        String magnitudeDescription;

        // Descrição do Score
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

        // Descrição da Magnitude
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

        // Construir a descrição final
        return "O sentimento expresso é " + scoreDescription + " com uma intensidade " + magnitudeDescription + ".";
    }

}
