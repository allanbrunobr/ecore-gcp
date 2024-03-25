package com.br.bruno.appweb.models.vision;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.Setter;



/**
 * The FaceDetectionMessage class represents a message containing face detection data.
 * It includes the URL of the image and a list of FaceData objects
 * that represent the detected faces.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceDetectionMessage {
  private String imageUrl;
  private List<FaceData> faceData;
}

