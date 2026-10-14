package com.clicktrack.model;

/**
 * Entité représentant un Lien de tracking.
 * Un lien redirige vers une URL de destination, est rattaché à une offre commerciale
 * et appartient à un publisher identifié par publisherId.
 */
public class Lien {

    /**
     * Identifiant unique du lien de tracking.
     */
    private Long id;

    /**
     * Nom ou description de l'offre associée au lien (ex: "Casque audio X200").
     */
    private String offre;

    /**
     * URL cible vers laquelle le visiteur doit être redirigé (ex: "https://www.exemple.com/...").
     */
    private String urlDestination;

    /**
     * Nombre total de clics enregistrés sur ce lien.
     */
    private int clicks;

    /**
     * Identifiant du publisher propriétaire de ce lien.
     */
    private Long publisherId;

    /**
     * Constructeur par défaut requis pour la sérialisation et désérialisation JSON.
     */
    public Lien() {
    }

    /**
     * Constructeur complet permettant d'initialiser l'ensemble des propriétés d'un lien.
     *
     * @param id             Identifiant unique
     * @param offre          Nom de l'offre
     * @param urlDestination URL de destination
     * @param clicks         Nombre de clics initial
     * @param publisherId    Identifiant du publisher associé
     */
    public Lien(Long id, String offre, String urlDestination, int clicks, Long publisherId) {
        this.id = id;
        this.offre = offre;
        this.urlDestination = urlDestination;
        this.clicks = clicks;
        this.publisherId = publisherId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public String getUrlDestination() {
        return urlDestination;
    }

    public void setUrlDestination(String urlDestination) {
        this.urlDestination = urlDestination;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}