package com.clicktrack.repository;

import com.clicktrack.model.Categorie;
import com.clicktrack.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link PublisherRepositoryImpl}.
 * Valide les opérations CRUD et la sécurité d'accès multi-thread.
 */
class PublisherRepositoryTest {

    private PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        publisherRepository = new PublisherRepositoryImpl();
    }

    @Test
    @DisplayName("Devrait sauvegarder un publisher et lui assigner un ID automatiquement")
    void testSaveAndFindById() {
        Publisher p = new Publisher(null, "Alami", "Sara", "sara@example.com", Categorie.INFLUENCEUR);
        Publisher saved = publisherRepository.save(p);

        assertNotNull(saved.getId(), "L'ID doit être généré automatiquement");
        assertEquals(1L, saved.getId());
        assertEquals("Alami", saved.getNom());

        Optional<Publisher> found = publisherRepository.findById(saved.getId());
        assertTrue(found.isPresent(), "Le publisher doit être retrouvé dans le dépôt");
        assertEquals("Sara", found.get().getPrenom());
    }

    @Test
    @DisplayName("Devrait lister tous les publishers enregistrés")
    void testFindAll() {
        publisherRepository.save(new Publisher(null, "Alami", "Sara", "sara@example.com", Categorie.INFLUENCEUR));
        publisherRepository.save(new Publisher(null, "Bennani", "Youssef", "youssef@example.com", Categorie.YOUTUBEUR));

        List<Publisher> list = publisherRepository.findAll();
        assertEquals(2, list.size(), "Il doit y avoir exactement 2 publishers");
    }

    @Test
    @DisplayName("Devrait retourner vide lors de la recherche d'un ID inexistant")
    void testFindByIdNotFound() {
        Optional<Publisher> found = publisherRepository.findById(999L);
        assertFalse(found.isPresent(), "Aucun publisher ne doit correspondre à l'ID 999");
    }

    @Test
    @DisplayName("Devrait supporter les ajouts concurrents sans perte de données")
    void testConcurrentSaves() throws InterruptedException {
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    publisherRepository.save(new Publisher(null, "Nom" + index, "Prenom" + index, "email" + index + "@test.com", Categorie.INFLUENCEUR));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        List<Publisher> all = publisherRepository.findAll();
        assertEquals(threadCount, all.size(), "Tous les publishers créés en parallèle doivent être présents");
    }
}
