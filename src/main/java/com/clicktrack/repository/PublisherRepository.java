package com.clicktrack.repository;

import com.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

/**
 * Interface du dépôt pour la gestion des entités {@link Publisher}.
 * Définit les opérations d'accès et de persistance des éditeurs en mémoire.
 */
public interface PublisherRepository {

    /**
     * Enregistre un éditeur dans le dépôt.
     * Si l'identifiant est nul, un nouvel ID lui est attribué automatiquement.
     *
     * @param publisher L'éditeur à enregistrer
     * @return L'éditeur enregistré avec son identifiant
     */
    Publisher save(Publisher publisher);

    /**
     * Récupère la liste complète de tous les éditeurs.
     *
     * @return Liste de tous les éditeurs
     */
    List<Publisher> findAll();

    /**
     * Recherche un éditeur par son identifiant unique.
     *
     * @param id L'identifiant de l'éditeur recherché
     * @return Un {@link Optional} contenant l'éditeur s'il existe, vide sinon
     */
    Optional<Publisher> findById(Long id);
}