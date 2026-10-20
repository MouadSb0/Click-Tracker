package com.clicktrack.controller;

import com.clicktrack.dto.OffreDto;
import com.clicktrack.model.Categorie;
import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.service.LienService;
import com.clicktrack.service.OffreService;
import com.clicktrack.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration MVC pour {@link PublisherController}.
 * Valide les endpoints /api/publishers, /api/publishers/{id}/liens et /api/publishers/{id}/offres.
 */
@ExtendWith(MockitoExtension.class)
class PublisherControllerTest {

    @Mock
    private PublisherService publisherService;

    @Mock
    private LienService lienService;

    @Mock
    private OffreService offreService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        PublisherController controller = new PublisherController(publisherService, lienService, offreService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET /api/publishers devrait retourner la liste des éditeurs avec statut 200")
    void testGetAllPublishers() throws Exception {
        Publisher p1 = new Publisher(1L, "Alami", "Sara", "sara@example.com", Categorie.INFLUENCEUR);
        when(publisherService.getAllPublishers()).thenReturn(Collections.singletonList(p1));

        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nom").value("Alami"))
                .andExpect(jsonPath("$[0].prenom").value("Sara"))
                .andExpect(jsonPath("$[0].categorie").value("INFLUENCEUR"));
    }

    @Test
    @DisplayName("GET /api/publishers/{id}/liens devrait retourner les liens d'un éditeur avec statut 200")
    void testGetLiensByPublisher() throws Exception {
        Lien lien = new Lien(1L, "Casque audio X200", "https://example.com/audio", 10, 1L);
        when(lienService.getLiensByPublisher(1L)).thenReturn(Collections.singletonList(lien));

        mockMvc.perform(get("/api/publishers/1/liens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].offre").value("Casque audio X200"))
                .andExpect(jsonPath("$[0].clicks").value(10));
    }

    @Test
    @DisplayName("GET /api/publishers/{id}/offres devrait retourner les offres agrégées avec statut 200")
    void testGetOffresByPublisher() throws Exception {
        OffreDto o1 = new OffreDto("Casque audio X200", 1, 10);
        OffreDto o2 = new OffreDto("Montre connectée Z5", 1, 2);
        when(offreService.getOffresByPublisher(1L)).thenReturn(Arrays.asList(o1, o2));

        mockMvc.perform(get("/api/publishers/1/offres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].offre").value("Casque audio X200"))
                .andExpect(jsonPath("$[0].nombreLiens").value(1))
                .andExpect(jsonPath("$[0].clicks").value(10))
                .andExpect(jsonPath("$[1].offre").value("Montre connectée Z5"))
                .andExpect(jsonPath("$[1].clicks").value(2));
    }
}
