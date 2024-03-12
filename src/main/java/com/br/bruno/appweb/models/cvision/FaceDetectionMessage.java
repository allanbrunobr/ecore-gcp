package com.br.bruno.appweb.models.cvision;

import java.util.List;

public class FaceDetectionMessage {
    private String imageUrl;
    private List<FaceData> faceData;

    // Getters e setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<FaceData> getFaceData() {
        return faceData;
    }

    public void setFaceData(List<FaceData> faceData) {
        this.faceData = faceData;
    }
}

