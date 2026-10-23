package com.clicktrack.config;

import com.clicktrack.model.Categorie;
import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.repository.LienRepository;
import com.clicktrack.repository.PublisherRepository;

/**
 * Composant responsable de l'initialisation des données de test au démarrage de l'application.
 * Conforme aux spécifications du Sprint 3 - Brief 1 (phase init du cycle de vie du bean Spring).
 * Injecté par constructeur et configuré via {@code init-method="init"} dans applicationContext.xml.
 */
public class DataInitializer {

    private final PublisherRepository publisherRepository;
    private final LienRepository lienRepository;

    /**
     * Constructeur avec injection des dépendances vers les interfaces des dépôts.
     *
     * @param publisherRepository Dépôt des éditeurs
     * @param lienRepository      Dépôt des liens
     */
    public DataInitializer(PublisherRepository publisherRepository, LienRepository lienRepository) {
        this.publisherRepository = publisherRepository;
        this.lienRepository = lienRepository;
    }

    /**
     * Méthode d'initialisation appelée automatiquement par le conteneur Spring lors de la phase init.
     * Charge les publishers de test et leurs liens initiaux pour permettre la validation fonctionnelle et les tests.
     */
    public void init() {

        Publisher p1 = new Publisher(1L, "Alami", "Sara", "sara.alami@example.com", Categorie.INFLUENCEUR);
        Publisher p2 = new Publisher(2L, "Bennani", "Youssef", "youssef.bennani@example.com", Categorie.YOUTUBEUR);
        Publisher p3 = new Publisher(3L, "El Idrissi", "Imane", "imane.elidrissi@example.com", Categorie.BLOGEUR);

        publisherRepository.save(p1);
        publisherRepository.save(p2);
        publisherRepository.save(p3);


        Lien lien1 = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 10, 1L);
        Lien lien2 = new Lien(2L, "Montre connectée Z5", "https://www.exemple.com/montre-z5", 2, 1L);

        lienRepository.save(lien1);
        lienRepository.save(lien2);
    }
}
