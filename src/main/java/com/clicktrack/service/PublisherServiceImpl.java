package com.clicktrack.service;

import com.clicktrack.model.Publisher;
import com.clicktrack.repository.PublisherRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service métier {@link PublisherService}.
 * Fait le lien entre la couche contrôleur et la couche d'accès aux données {@link PublisherRepository}.
 * Respecte l'injection par constructeur et la dépendance vers l'interface du dépôt.
 */
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    /**
     * Constructeur avec injection du dépôt PublisherRepository.
     *
     * @param publisherRepository Le dépôt des publishers
     */
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    /**
     * Retourne la totalité des publishers connus.
     *
     * @return Liste de publishers
     */
    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    /**
     * Recherche un publisher par son identifiant.
     *
     * @param id Identifiant recherché
     * @return Optional contenant le publisher s'il existe
     */
    @Override
    public Optional<Publisher> getById(Long id) {
        return publisherRepository.findById(id);
    }
}