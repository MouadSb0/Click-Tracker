package com.clicktrack.service;

import com.clicktrack.model.Categorie;
import com.clicktrack.model.Publisher;
import com.clicktrack.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour {@link PublisherServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    private PublisherService publisherService;

    @BeforeEach
    void setUp() {
        publisherService = new PublisherServiceImpl(publisherRepository);
    }

    @Test
    @DisplayName("Devrait retourner tous les éditeurs en déléguant au dépôt")
    void testGetAllPublishers() {
        Publisher p1 = new Publisher(1L, "Alami", "Sara", "sara@example.com", Categorie.INFLUENCEUR);
        Publisher p2 = new Publisher(2L, "Bennani", "Youssef", "youssef@example.com", Categorie.YOUTUBEUR);

        when(publisherRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Publisher> publishers = publisherService.getAllPublishers();

        assertEquals(2, publishers.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner un éditeur par son ID")
    void testGetById() {
        Publisher p = new Publisher(1L, "Alami", "Sara", "sara@example.com", Categorie.INFLUENCEUR);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Publisher> result = publisherService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Alami", result.get().getNom());
        verify(publisherRepository, times(1)).findById(1L);
    }
}
