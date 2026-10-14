package com.clicktrack.repository;

import com.clicktrack.model.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implémentation en mémoire du dépôt {@link PublisherRepository}.
 * Stocke les éditeurs dans une {@link ArrayList}.
 * Toutes les méthodes sont synchronisées afin de garantir la sécurité d'accès multi-thread (concurrence).
 */
public class PublisherRepositoryImpl implements PublisherRepository {

    /**
     * Liste interne en mémoire pour stocker les éditeurs.
     */
    private final List<Publisher> publishers = new ArrayList<>();

    /**
     * Générateur thread-safe d'identifiants uniques pour les éditeurs.
     */
    private final AtomicLong idCounter = new AtomicLong();

    /**
     * Enregistre ou met à jour un éditeur dans la liste en mémoire de façon thread-safe.
     * Si l'identifiant est null, un nouvel identifiant auto-incrémenté est assigné.
     *
     * @param publisher L'éditeur à enregistrer
     * @return L'éditeur enregistré
     */
    @Override
    public synchronized Publisher save(Publisher publisher) {
        if (publisher.getId() == null) {
            publisher.setId(idCounter.incrementAndGet());
        } else {
            idCounter.accumulateAndGet(publisher.getId(), Math::max);
        }

        for (int i = 0; i < publishers.size(); i++) {
            if (publishers.get(i).getId().equals(publisher.getId())) {
                publishers.set(i, publisher);
                return publisher;
            }
        }

        publishers.add(publisher);
        return publisher;
    }

    /**
     * Retourne une copie de la liste de tous les éditeurs pour éviter toute modification concurrente externe.
     *
     * @return Nouvelle liste contenant les éditeurs
     */
    @Override
    public synchronized List<Publisher> findAll() {
        return new ArrayList<>(publishers);
    }

    /**
     * Recherche un éditeur par son identifiant unique.
     *
     * @param id Identifiant recherché
     * @return Optional contenant l'éditeur s'il est trouvé
     */
    @Override
    public synchronized Optional<Publisher> findById(Long id) {
        return publishers.stream()
                .filter(publisher -> publisher.getId().equals(id))
                .findFirst();
    }
}