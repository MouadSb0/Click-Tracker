package com.clicktrack.service;

import com.clicktrack.model.Lien;
import com.clicktrack.repository.LienRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour {@link LienServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class LienServiceTest {

    @Mock
    private LienRepository lienRepository;

    private LienService lienService;

    @BeforeEach
    void setUp() {
        lienService = new LienServiceImpl(lienRepository);
    }

    @Test
    @DisplayName("Devrait associer publisherId et sauvegarder le lien")
    void testCreateLien() {
        Lien input = new Lien(null, "Casque audio X200", "https://example.com/audio", 0, null);
        Lien expected = new Lien(1L, "Casque audio X200", "https://example.com/audio", 0, 10L);

        when(lienRepository.save(input)).thenReturn(expected);

        Lien created = lienService.createLien(10L, input);

        assertEquals(10L, input.getPublisherId());
        assertEquals(expected, created);
        verify(lienRepository, times(1)).save(input);
    }

    @Test
    @DisplayName("Devrait lister les liens d'un publisher")
    void testGetLiensByPublisher() {
        Lien lien = new Lien(1L, "Casque audio X200", "https://example.com/audio", 5, 1L);
        when(lienRepository.findByPublisherId(1L)).thenReturn(Collections.singletonList(lien));

        List<Lien> result = lienService.getLiensByPublisher(1L);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getClicks());
        verify(lienRepository, times(1)).findByPublisherId(1L);
    }

    @Test
    @DisplayName("Devrait incrémenter les clics et retourner le lien mis à jour")
    void testClick() {
        Lien updatedLien = new Lien(1L, "Casque audio X200", "https://example.com/audio", 11, 1L);

        when(lienRepository.incrementClicks(1L)).thenReturn(11);
        when(lienRepository.findById(1L)).thenReturn(Optional.of(updatedLien));

        Lien result = lienService.click(1L);

        assertEquals(11, result.getClicks());
        verify(lienRepository, times(1)).incrementClicks(1L);
        verify(lienRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si le lien cliqué est introuvable après incrément")
    void testClickThrowsExceptionWhenNotFound() {
        when(lienRepository.incrementClicks(99L)).thenReturn(1);
        when(lienRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> lienService.click(99L));
    }
}
