package com.clicktrack.model;

/**
 * Entité représentant un Publisher (créateur de contenu, influenceur, etc.).
 * Un publisher diffuse des liens de tracking vers différentes offres commerciales.
 */
public class Publisher {

    /**
     * Identifiant unique du publisher.
     */
    private Long id;

    /**
     * Nom de famille du publisher.
     */
    private String nom;

    /**
     * Prénom du publisher.
     */
    private String prenom;

    /**
     * Adresse email de contact du publisher.
     */
    private String email;

    /**
     * Catégorie d'activité du publisher (INFLUENCEUR, YOUTUBEUR, BLOGEUR/BLOGUEUR).
     */
    private Categorie categorie;

    /**
     * Constructeur par défaut sans argument (requis pour les mécanismes de sérialisation/désérialisation JSON).
     */
    public Publisher() {
    }

    /**
     * Constructeur complet initialisant tous les champs d'un publisher.
     *
     * @param id        Identifiant unique
     * @param nom       Nom de famille
     * @param prenom    Prénom
     * @param email     Adresse email
     * @param categorie Catégorie de créateur
     */
    public Publisher(Long id, String nom, String prenom, String email, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.categorie = categorie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}