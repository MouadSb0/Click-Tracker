package com.clicktrack.repository;

import com.clicktrack.model.Lien;

import java.util.List;
import java.util.Optional;

/**
 * Interface du dépôt pour la gestion des entités {@link Lien}.
 * Définit les opérations CRUD en mémoire et la gestion concurrente des clics.
 */
public interface LienRepository {

    /**
     * Enregistre ou met à jour un lien de tracking.
     * Si l'identifiant est null, un ID unique lui est attribué de manière atomique.
     *
     * @param lien Le lien à enregistrer
     * @return Le lien enregistré avec son identifiant
     */
    Lien save(Lien lien);

    /**
     * Recherche un lien par son identifiant unique.
     *
     * @param id Identifiant unique du lien
     * @return Un {@link Optional} contenant le lien trouvé ou vide
     */
    Optional<Lien> findById(Long id);

    /**
     * Récupère la liste de tous les liens appartenant à un éditeur donné.
     *
     * @param publisherId Identifiant du publisher
     * @return Liste des liens correspondants
     */
    List<Lien> findByPublisherId(Long publisherId);

    /**
     * Incrémente de manière atomique et thread-safe le nombre de clics d'un lien.
     * Règle de gestion : « Aucun click ne doit être perdu lors de clics simultanés. »
     *
     * @param id Identifiant du lien à incrémenter
     * @return Le nouveau total de clics après incrémentation
     * @throws IllegalArgumentException si aucun lien ne correspond à l'identifiant fourni
     */
    int incrementClicks(Long id);
}