package com.clicktrack.repository;

import com.clicktrack.model.Lien;
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
 * Tests unitaires pour {@link LienRepositoryImpl}.
 * Valide en particulier la règle métier stricte :
 * « Aucun click ne doit être perdu lors de clics simultanés. »
 */
class LienRepositoryTest {

    private LienRepository lienRepository;

    @BeforeEach
    void setUp() {
        lienRepository = new LienRepositoryImpl();
    }

    @Test
    @DisplayName("Devrait sauvegarder un lien et lui attribuer un ID")
    void testSaveAndFindById() {
        Lien lien = new Lien(null, "Offre Promo", "https://example.com/promo", 0, 1L);
        Lien saved = lienRepository.save(lien);

        assertNotNull(saved.getId(), "L'identifiant doit être généré");
        assertEquals("Offre Promo", saved.getOffre());

        Optional<Lien> found = lienRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("https://example.com/promo", found.get().getUrlDestination());
    }

    @Test
    @DisplayName("Devrait récupérer les liens filtrés par publisherId")
    void testFindByPublisherId() {
        lienRepository.save(new Lien(null, "Offre 1", "https://example.com/1", 0, 1L));
        lienRepository.save(new Lien(null, "Offre 2", "https://example.com/2", 0, 1L));
        lienRepository.save(new Lien(null, "Offre 3", "https://example.com/3", 0, 2L));

        List<Lien> liensP1 = lienRepository.findByPublisherId(1L);
        List<Lien> liensP2 = lienRepository.findByPublisherId(2L);

        assertEquals(2, liensP1.size(), "Publisher 1 doit avoir 2 liens");
        assertEquals(1, liensP2.size(), "Publisher 2 doit avoir 1 lien");
    }

    @Test
    @DisplayName("Devrait incrémenter le nombre de clics avec succès")
    void testIncrementClicks() {
        Lien lien = lienRepository.save(new Lien(null, "Offre Test", "https://example.com/test", 0, 1L));
        int clicks = lienRepository.incrementClicks(lien.getId());

        assertEquals(1, clicks);
        assertEquals(1, lienRepository.findById(lien.getId()).get().getClicks());
    }

    @Test
    @DisplayName("Devrait lever une exception si le lien à incrémenter n'existe pas")
    void testIncrementClicksNotFound() {
        assertThrows(IllegalArgumentException.class, () -> lienRepository.incrementClicks(999L));
    }

    @Test
    @DisplayName("Test de concurrence : 100 clics simultanés ne doivent perdre aucun clic")
    void testConcurrentIncrementClicks() throws InterruptedException {
        // Préparation d'un lien avec 0 clic initial
        Lien lien = lienRepository.save(new Lien(null, "Casque audio X200", "https://www.exemple.com/casque-audio", 0, 1L));
        Long lienId = lien.getId();

        int numberOfClicks = 100;
        int threadPoolSize = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        CountDownLatch doneLatch = new CountDownLatch(numberOfClicks);

        for (int i = 0; i < numberOfClicks; i++) {
            executor.submit(() -> {
                try {
                    lienRepository.incrementClicks(lienId);
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        doneLatch.await();
        executor.shutdown();

        // Vérification stricte : aucun clic ne doit manquer
        Lien finalLien = lienRepository.findById(lienId).orElseThrow();
        assertEquals(numberOfClicks, finalLien.getClicks(), "Exactement 100 clics doivent être comptabilisés sans aucune perte");
    }
}
