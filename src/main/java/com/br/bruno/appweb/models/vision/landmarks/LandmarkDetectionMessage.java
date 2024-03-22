package com.br.bruno.appweb.models.vision.landmarks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.LocationInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LandmarkDetectionMessage {

    private String imageUrl;
    private List<LandmarkData> landmarkData;

    public LandmarkDetectionMessage(String imageUrl, List<AnnotateImageResponse> responses) {
        this.imageUrl = imageUrl;
        this.landmarkData = mapResponsesToLandmarkData(filterHighestScoreLandmarks(responses));
    }

    public List<AnnotateImageResponse> filterHighestScoreLandmarks(List<AnnotateImageResponse> responses) {
        List<AnnotateImageResponse> filteredResponses = new ArrayList<>();

        for (AnnotateImageResponse response : responses) {
            if (!response.hasError()) {
                double highestScore = Double.MIN_VALUE;
                EntityAnnotation highestScoreLandmark = null;

                for (EntityAnnotation annotation : response.getLandmarkAnnotationsList()) {
                    double score = annotation.getScore();
                    if (score > highestScore) {
                        highestScore = score;
                        highestScoreLandmark = annotation;
                    }
                }

                if (highestScoreLandmark != null) {
                    AnnotateImageResponse filteredResponse = AnnotateImageResponse.newBuilder()
                            .addLandmarkAnnotations(highestScoreLandmark)
                            .build();
                    filteredResponses.add(filteredResponse);
                }
            }
        }

        return filteredResponses;
    }

    private List<LandmarkData> mapResponsesToLandmarkData(List<AnnotateImageResponse> responses) {
        List<LandmarkData> landmarkDataList = new ArrayList<>();
        for (AnnotateImageResponse response : responses) {
            if (!response.hasError()) {
                for (EntityAnnotation annotation : response.getLandmarkAnnotationsList()) {
                    LocationInfo locationInfo = annotation.getLocationsList().listIterator().next();
                    LandmarkData landmarkData = new LandmarkData();
                    landmarkData.setMid(annotation.getMid());
                    landmarkData.setDescription(annotation.getDescription());
                    landmarkData.setScore(annotation.getScore());
                    landmarkData.setBoundingPoly(mapBoundingPoly(annotation.getBoundingPoly()));
                    landmarkData.setLocations(mapLocations(locationInfo));
                    landmarkDataList.add(landmarkData);
                }
            }
        }
        return landmarkDataList;
    }

    private BoundingPoly mapBoundingPoly(com.google.cloud.vision.v1.BoundingPoly boundingPoly) {
        BoundingPoly poly = new BoundingPoly();
        poly.setVertices(mapVertices(boundingPoly.getVerticesList()));
        poly.setNormalizedVertices(mapNormalizedVertices(boundingPoly.getNormalizedVerticesList()));
        return poly;
    }

    private List<Vertex> mapVertices(List<com.google.cloud.vision.v1.Vertex> verticesList) {
        List<Vertex> vertices = new ArrayList<>();
        for (com.google.cloud.vision.v1.Vertex vertex : verticesList) {
            Vertex v = new Vertex();
            v.setX(vertex.getX());
            v.setY(vertex.getY());
            vertices.add(v);
        }
        return vertices;
    }

    private List<NormalizedVertex> mapNormalizedVertices(List<com.google.cloud.vision.v1.NormalizedVertex> normalizedVerticesList) {
        List<NormalizedVertex> normalizedVertices = new ArrayList<>();
        for (com.google.cloud.vision.v1.NormalizedVertex vertex : normalizedVerticesList) {
            NormalizedVertex v = new NormalizedVertex();
            v.setX(vertex.getX());
            v.setY(vertex.getY());
            normalizedVertices.add(v);
        }
        return normalizedVertices;
    }


    private Locations mapLocations(LocationInfo locationInfo) {
        Locations locations = new Locations();
        locations.setLatLng(mapLatLng(locationInfo.getLatLng()));
        return locations;
    }

    private LatLng mapLatLng(com.google.type.LatLng latLng) {
        LatLng coordinates = new LatLng();
        coordinates.setLatitude(latLng.getLatitude());
        coordinates.setLongitude(latLng.getLongitude());
        return coordinates;
    }
}

