package com.br.bruno.appweb.models.vision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The FaceDetectionMessage class represents a message containing face detection data.
 * It includes the URL of the image and a list of FaceData objects that represent the detected faces.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceDetectionMessage {
    private String imageUrl;
    private List<FaceData> faceData;
}

