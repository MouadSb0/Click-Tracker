package com.clicktrack.config;

import com.clicktrack.model.Categorie;
import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.repository.LienRepository;
import com.clicktrack.repository.LienRepositoryImpl;
import com.clicktrack.repository.PublisherRepository;
import com.clicktrack.repository.PublisherRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link DataInitializer}.
 * Vérifie le chargement conforme des données de test au démarrage de l'application.
 */
class DataInitializerTest {

    private PublisherRepository publisherRepository;
    private LienRepository lienRepository;
    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        publisherRepository = new PublisherRepositoryImpl();
        lienRepository = new LienRepositoryImpl();
        dataInitializer = new DataInitializer(publisherRepository, lienRepository);
    }

    @Test
    @DisplayName("Devrait initialiser les 3 publishers et les 2 liens de test")
    void testInitPopulatesData() {
        dataInitializer.init();

        List<Publisher> publishers = publisherRepository.findAll();
        assertEquals(3, publishers.size(), "Il doit y avoir 3 publishers de test initialisés");

        Optional<Publisher> p1 = publisherRepository.findById(1L);
        assertTrue(p1.isPresent());
        assertEquals("Alami", p1.get().getNom());
        assertEquals("Sara", p1.get().getPrenom());
        assertEquals(Categorie.INFLUENCEUR, p1.get().getCategorie());

        Optional<Publisher> p2 = publisherRepository.findById(2L);
        assertTrue(p2.isPresent());
        assertEquals("Bennani", p2.get().getNom());
        assertEquals(Categorie.YOUTUBEUR, p2.get().getCategorie());

        Optional<Publisher> p3 = publisherRepository.findById(3L);
        assertTrue(p3.isPresent());
        assertEquals("El Idrissi", p3.get().getNom());
        assertEquals(Categorie.BLOGEUR, p3.get().getCategorie());

        List<Lien> liensP1 = lienRepository.findByPublisherId(1L);
        assertEquals(2, liensP1.size(), "Publisher 1 doit avoir 2 liens initialisés");

        Lien l1 = lienRepository.findById(1L).orElseThrow();
        assertEquals("Casque audio X200", l1.getOffre());
        assertEquals(10, l1.getClicks());

        Lien l2 = lienRepository.findById(2L).orElseThrow();
        assertEquals("Montre connectée Z5", l2.getOffre());
        assertEquals(2, l2.getClicks());
    }
}
