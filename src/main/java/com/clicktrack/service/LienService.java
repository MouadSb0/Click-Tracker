package com.clicktrack.service;

import com.clicktrack.model.Lien;

import java.util.List;
import java.util.Optional;

/**
 * Interface du service métier pour la gestion des liens de tracking.
 * Fournit les opérations de création, de consultation et d'enregistrement des clics.
 */
public interface LienService {

    /**
     * Crée un nouveau lien de tracking rattaché au publisher spécifié.
     * Règle fonctionnelle : « En tant que publisher, je crée un lien de tracking vers une URL de destination, rattaché à une offre. »
     *
     * @param publisherId Identifiant du publisher créateur
     * @param lien        Données du lien à créer (offre, urlDestination)
     * @return Le lien créé avec son identifiant et compteur de clics initialisé
     */
    Lien createLien(Long publisherId, Lien lien);

    /**
     * Liste tous les liens de tracking d'un publisher.
     * Règle fonctionnelle : « En tant que publisher, je liste mes liens avec leur nombre de clicks. »
     *
     * @param publisherId Identifiant du publisher
     * @return Liste des liens associés
     */
    List<Lien> getLiensByPublisher(Long publisherId);

    /**
     * Récupère un lien par son identifiant unique.
     *
     * @param id Identifiant du lien
     * @return Optional contenant le lien si trouvé
     */
    Optional<Lien> getLienById(Long id);

    /**
     * Enregistre un clic sur le lien identifié de façon atomique et retourne l'état actualisé.
     * Règle de gestion : « À chaque ouverture d'un lien de tracking, le click est compté. »
     *
     * @param id Identifiant du lien cliqué
     * @return Le lien actualisé avec son nouveau total de clics
     */
    Lien click(Long id);
}