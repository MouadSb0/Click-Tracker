package com.clicktrack.service;

import com.clicktrack.dto.OffreDto;
import com.clicktrack.model.Lien;
import com.clicktrack.repository.LienRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour {@link OffreServiceImpl}.
 * Valide la règle de gestion : « Une offre peut avoir plusieurs liens : ses clicks sont la somme des clicks de ses liens. »
 */
@ExtendWith(MockitoExtension.class)
class OffreServiceTest {

    @Mock
    private LienRepository lienRepository;

    private OffreService offreService;

    @BeforeEach
    void setUp() {
        offreService = new OffreServiceImpl(lienRepository);
    }

    @Test
    @DisplayName("Devrait agréger les clics et compter les liens pour chaque offre distincte")
    void testGetOffresByPublisherAggregatesCorrectly() {
        // Deux liens pour la même offre "Casque audio X200" (10 clics + 5 clics)
        Lien lien1 = new Lien(1L, "Casque audio X200", "https://example.com/audio1", 10, 1L);
        Lien lien2 = new Lien(2L, "Casque audio X200", "https://example.com/audio2", 5, 1L);
        // Un lien pour une autre offre "Montre connectée Z5" (2 clics)
        Lien lien3 = new Lien(3L, "Montre connectée Z5", "https://example.com/montre", 2, 1L);

        when(lienRepository.findByPublisherId(1L)).thenReturn(Arrays.asList(lien1, lien2, lien3));

        List<OffreDto> result = offreService.getOffresByPublisher(1L);

        assertEquals(2, result.size(), "Il doit y avoir 2 offres distinctes agrégées");

        OffreDto offreCasque = result.stream().filter(o -> "Casque audio X200".equals(o.getOffre())).findFirst().orElseThrow();
        assertEquals(2, offreCasque.getNombreLiens(), "Casque audio X200 doit avoir 2 liens");
        assertEquals(15, offreCasque.getClicks(), "Les clics doivent être la somme (10 + 5 = 15)");

        OffreDto offreMontre = result.stream().filter(o -> "Montre connectée Z5".equals(o.getOffre())).findFirst().orElseThrow();
        assertEquals(1, offreMontre.getNombreLiens(), "Montre connectée Z5 doit avoir 1 lien");
        assertEquals(2, offreMontre.getClicks(), "Montre connectée Z5 doit avoir 2 clics");
    }

    @Test
    @DisplayName("Devrait retourner une liste vide si le publisher n'a aucun lien")
    void testGetOffresByPublisherEmpty() {
        when(lienRepository.findByPublisherId(99L)).thenReturn(Collections.emptyList());

        List<OffreDto> result = offreService.getOffresByPublisher(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
