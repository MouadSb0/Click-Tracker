package com.clicktrack.dto;

public class OffreDto {
    private String offre;
    private int nombreLiens;
    private int clicks;

    public OffreDto() {
    }

    public OffreDto(String offre, int nombreLiens, int clicks) {
        this.offre = offre;
        this.nombreLiens = nombreLiens;
        this.clicks = clicks;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public int getNombreLiens() {
        return nombreLiens;
    }

    public void setNombreLiens(int nombreLiens) {
        this.nombreLiens = nombreLiens;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}