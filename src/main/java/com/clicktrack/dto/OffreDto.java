package com.clicktrack.dto;

/**
 * Objet de transfert de données (DTO) pour la synthèse des offres d'un publisher.
 * Conforme à la règle de gestion : « Une offre peut avoir plusieurs liens : ses clicks sont la somme des clicks de ses liens. »
 */
public class OffreDto {

    /**
     * Nom de l'offre commerciale (ex: "Casque audio X200").
     */
    private String offre;

    /**
     * Nombre total de liens de tracking rattachés à cette offre pour le publisher donné.
     */
    private int nombreLiens;

    /**
     * Somme cumulée des clics de tous les liens associés à cette offre.
     */
    private int clicks;

    /**
     * Constructeur sans argument pour la sérialisation JSON.
     */
    public OffreDto() {
    }

    /**
     * Constructeur complet pour instancier l'agrégat d'une offre.
     *
     * @param offre       Nom de l'offre
     * @param nombreLiens Nombre de liens affiliés à cette offre
     * @param clicks      Nombre cumulé de clics
     */
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