package com.clicktrack.controller;

import com.clicktrack.model.Categorie;
import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.service.LienService;
import com.clicktrack.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration MVC pour {@link TrackingController}.
 * Valide :
 * - Le tracking JSON avec incrémentation et affichage des informations (statut 200)
 * - La gestion des liens inexistants (statut 404)
 * - Le bonus de redirection HTTP 302 vers l'URL de destination
 */
@ExtendWith(MockitoExtension.class)
class TrackingControllerTest {

    @Mock
    private LienService lienService;

    @Mock
    private PublisherService publisherService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TrackingController controller = new TrackingController(lienService, publisherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET /t/{id} devrait incrémenter le clic et retourner les informations du lien en JSON avec statut 200")
    void testTrackSuccessJsonMode() throws Exception {
        Lien existingLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 10, 1L);
        Lien clickedLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 11, 1L);
        Publisher publisher = new Publisher(1L, "Alami", "Sara", "sara.alami@example.com", Categorie.INFLUENCEUR);

        when(lienService.getLienById(1L)).thenReturn(Optional.of(existingLien));
        when(publisherService.getById(1L)).thenReturn(Optional.of(publisher));
        when(lienService.click(1L)).thenReturn(clickedLien);

        mockMvc.perform(get("/t/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.urlRedirection").value("https://www.exemple.com/casque-audio"))
                .andExpect(jsonPath("$.publisher").value("Sara Alami"))
                .andExpect(jsonPath("$.offre").value("Casque audio X200"))
                .andExpect(jsonPath("$.clicks").value(11));

        verify(lienService, times(1)).click(1L);
    }

    @Test
    @DisplayName("GET /t/{id} devrait retourner 404 si le lien n'existe pas")
    void testTrackLienNotFound() throws Exception {
        when(lienService.getLienById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/t/999"))
                .andExpect(status().isNotFound());

        verify(lienService, never()).click(anyLong());
    }

    @Test
    @DisplayName("GET /t/{id} devrait retourner 404 si le publisher associé au lien n'existe pas")
    void testTrackPublisherNotFound() throws Exception {
        Lien existingLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 10, 999L);
        when(lienService.getLienById(1L)).thenReturn(Optional.of(existingLien));
        when(publisherService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/t/1"))
                .andExpect(status().isNotFound());

        verify(lienService, never()).click(anyLong());
    }

    @Test
    @DisplayName("GET /t/{id}?redirect=true (Bonus) devrait retourner HTTP 302 avec redirection vers l'URL de destination")
    void testTrackBonusRedirectParam() throws Exception {
        Lien existingLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 10, 1L);
        Lien clickedLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 11, 1L);
        Publisher publisher = new Publisher(1L, "Alami", "Sara", "sara.alami@example.com", Categorie.INFLUENCEUR);

        when(lienService.getLienById(1L)).thenReturn(Optional.of(existingLien));
        when(publisherService.getById(1L)).thenReturn(Optional.of(publisher));
        when(lienService.click(1L)).thenReturn(clickedLien);

        mockMvc.perform(get("/t/1").param("redirect", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "https://www.exemple.com/casque-audio"));

        verify(lienService, times(1)).click(1L);
    }

    @Test
    @DisplayName("GET /t/{id} avec en-tête navigateur (text/html) devrait rediriger en 302 vers l'URL de destination")
    void testTrackBonusRedirectBrowserAccept() throws Exception {
        Lien existingLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 10, 1L);
        Lien clickedLien = new Lien(1L, "Casque audio X200", "https://www.exemple.com/casque-audio", 11, 1L);
        Publisher publisher = new Publisher(1L, "Alami", "Sara", "sara.alami@example.com", Categorie.INFLUENCEUR);

        when(lienService.getLienById(1L)).thenReturn(Optional.of(existingLien));
        when(publisherService.getById(1L)).thenReturn(Optional.of(publisher));
        when(lienService.click(1L)).thenReturn(clickedLien);

        mockMvc.perform(get("/t/1").header(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "https://www.exemple.com/casque-audio"));

        verify(lienService, times(1)).click(1L);
    }
}
