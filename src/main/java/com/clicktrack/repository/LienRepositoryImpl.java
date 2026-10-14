package com.clicktrack.repository;

import com.clicktrack.model.Lien;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implémentation en mémoire du dépôt {@link LienRepository}.
 * Stocke les données dans une {@link ArrayList} protégée par synchronisation.
 * Garantit qu'aucun clic ni aucune donnée ne soit perdu lors d'appels simultanés (concurrence).
 */
public class LienRepositoryImpl implements LienRepository {

    /**
     * Liste interne en mémoire pour conserver les liens créés.
     */
    private final List<Lien> liens = new ArrayList<>();

    /**
     * Compteur atomique pour la génération thread-safe des identifiants uniques de liens.
     */
    private final AtomicLong idCounter = new AtomicLong();

    /**
     * Enregistre un nouveau lien ou met à jour un lien existant.
     * Cette méthode est synchronisée pour prévenir tout conflit d'accès concurrent lors de l'ajout/mise à jour.
     *
     * @param lien Le lien à sauvegarder
     * @return Le lien enregistré
     */
    @Override
    public synchronized Lien save(Lien lien) {
        if (lien.getId() == null) {
            lien.setId(idCounter.incrementAndGet());
        } else {
            idCounter.accumulateAndGet(lien.getId(), Math::max);
        }

        for (int index = 0; index < liens.size(); index++) {
            if (liens.get(index).getId().equals(lien.getId())) {
                liens.set(index, lien);
                return lien;
            }
        }

        liens.add(lien);
        return lien;
    }

    /**
     * Recherche un lien par son identifiant de manière thread-safe.
     *
     * @param id Identifiant du lien
     * @return Optional contenant le lien trouvé ou vide
     */
    @Override
    public synchronized Optional<Lien> findById(Long id) {
        return liens.stream()
                .filter(lien -> lien.getId().equals(id))
                .findFirst();
    }

    /**
     * Retourne tous les liens d'un publisher spécifique sous forme de nouvelle liste isolée.
     *
     * @param publisherId Identifiant du publisher
     * @return Liste filtrée des liens
     */
    @Override
    public synchronized List<Lien> findByPublisherId(Long publisherId) {
        List<Lien> result = new ArrayList<>();
        for (Lien lien : liens) {
            if (lien.getPublisherId().equals(publisherId)) {
                result.add(lien);
            }
        }
        return result;
    }

    /**
     * Incrémente le compteur de clics du lien ciblé de manière synchronisée.
     * Respecte l'exigence : « Gestion de la concurrence : plusieurs visiteurs peuvent cliquer en même temps,
     * l'incrémentation des clicks est protégée afin qu'aucun click ne soit perdu. »
     *
     * @param id Identifiant du lien
     * @return Nouveau nombre total de clics
     */
    @Override
    public synchronized int incrementClicks(Long id) {
        for (Lien lien : liens) {
            if (lien.getId().equals(id)) {
                int clicks = lien.getClicks() + 1;
                lien.setClicks(clicks);
                return clicks;
            }
        }
        throw new IllegalArgumentException("Lien not found: " + id);
    }
}