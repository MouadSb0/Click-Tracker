package com.clicktrack.model;

/**
 * Énumération des différentes catégories de publishers (créateurs de contenu).
 * Représente le profil ou canal principal de l'éditeur.
 */
public enum Categorie {
    /**
     * Créateur de contenu actif sur les réseaux sociaux (Instagram, TikTok, etc.).
     */
    INFLUENCEUR,

    /**
     * Créateur de contenu vidéo sur la plateforme YouTube.
     */
    YOUTUBEUR,

    /**
     * Rédacteur ou auteur de contenu sur un blog web (alias historique court).
     */
    BLOGEUR,

    /**
     * Rédacteur ou auteur de contenu sur un blog web (orthographe standard).
     */
    BLOGUEUR
}