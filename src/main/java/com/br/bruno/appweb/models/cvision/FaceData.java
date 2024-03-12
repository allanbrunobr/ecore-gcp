package com.br.bruno.appweb.models.cvision;

public class FaceData {
    private int id;
    private String joyLikelihood;
    private String angerLikelihood;
    private String sorrowLikelihood;
    private String surpriseLikelihood;

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoyLikelihood() {
        return joyLikelihood;
    }

    public void setJoyLikelihood(String joyLikelihood) {
        this.joyLikelihood = joyLikelihood;
    }

    public String getAngerLikelihood() {
        return angerLikelihood;
    }

    public void setAngerLikelihood(String angerLikelihood) {
        this.angerLikelihood = angerLikelihood;
    }

    public String getSorrowLikelihood() {
        return sorrowLikelihood;
    }

    public void setSorrowLikelihood(String sorrowLikelihood) {
        this.sorrowLikelihood = sorrowLikelihood;
    }

    public String getSurpriseLikelihood() {
        return surpriseLikelihood;
    }

    public void setSurpriseLikelihood(String surpriseLikelihood) {
        this.surpriseLikelihood = surpriseLikelihood;
    }
}
