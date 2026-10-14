package com.clicktrack.service;

import com.clicktrack.dto.OffreDto;

import java.util.List;

/**
 * Interface du service métier pour la gestion et l'agrégation des offres par publisher.
 * Permet de suivre les performances des offres à travers leurs différents liens.
 */
public interface OffreService {

    /**
     * Calcule et retourne la liste des offres d'un publisher avec le nombre de liens et le total de clics par offre.
     * Règle de gestion : « Une offre peut avoir plusieurs liens : ses clicks sont la somme des clicks de ses liens. »
     * Règle fonctionnelle : « En tant que publisher, je liste mes offres avec leur nombre de clicks. »
     *
     * @param publisherId Identifiant du publisher
     * @return Liste d'objets {@link OffreDto} contenant le résumé par offre
     */
    List<OffreDto> getOffresByPublisher(Long publisherId);
}