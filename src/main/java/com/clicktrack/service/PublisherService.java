package com.clicktrack.service;

import com.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

/**
 * Interface de la couche service métier pour la gestion des Publishers.
 * Définit les règles métier applicables aux créateurs de contenu.
 */
public interface PublisherService {

    /**
     * Récupère la liste intégrale des publishers enregistrés dans la plateforme.
     *
     * @return Liste de tous les éditeurs
     */
    List<Publisher> getAllPublishers();

    /**
     * Récupère un publisher par son identifiant unique.
     *
     * @param id Identifiant du publisher
     * @return Optional contenant le publisher si trouvé
     */
    Optional<Publisher> getById(Long id);
}