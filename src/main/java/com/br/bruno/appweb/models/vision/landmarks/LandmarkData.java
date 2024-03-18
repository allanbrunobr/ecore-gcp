package com.br.bruno.appweb.models.vision.landmarks;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandmarkData {
    private String mid;
    private String description;
    private double score;
    private BoundingPoly boundingPoly;
    private Location location;

}

