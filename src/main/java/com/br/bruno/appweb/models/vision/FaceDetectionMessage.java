package com.br.bruno.appweb.models.vision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceDetectionMessage {
    private String imageUrl;
    private List<FaceData> faceData;
}

