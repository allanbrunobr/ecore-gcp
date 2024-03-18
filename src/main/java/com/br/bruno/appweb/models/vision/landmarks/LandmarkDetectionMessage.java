package com.br.bruno.appweb.models.vision.landmarks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandmarkDetectionMessage {

    private String imageUrl;
    private List<LandmarkData> landmarkData;
}
