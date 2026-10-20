package com.clicktrack.controller;

import com.clicktrack.model.Lien;
import com.clicktrack.service.LienService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration MVC pour {@link LienController}.
 * Valide le endpoint POST /api/publishers/{id}/liens (retour attendu : 201 Created).
 */
@ExtendWith(MockitoExtension.class)
class LienControllerTest {

    @Mock
    private LienService lienService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LienController controller = new LienController(lienService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("POST /api/publishers/{id}/liens devrait créer un lien et retourner statut 201 Created")
    void testCreateLien() throws Exception {
        Lien savedLien = new Lien(3L, "Offre Spéciale Été", "https://example.com/promo", 0, 1L);

        when(lienService.createLien(eq(1L), any(Lien.class))).thenReturn(savedLien);

        String jsonPayload = """
                {
                    "offre": "Offre Spéciale Été",
                    "urlDestination": "https://example.com/promo"
                }
                """;

        mockMvc.perform(post("/api/publishers/1/liens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.offre").value("Offre Spéciale Été"))
                .andExpect(jsonPath("$.urlDestination").value("https://example.com/promo"))
                .andExpect(jsonPath("$.clicks").value(0))
                .andExpect(jsonPath("$.publisherId").value(1));
    }
}
