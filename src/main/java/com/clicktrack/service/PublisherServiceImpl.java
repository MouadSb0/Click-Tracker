package com.clicktrack.service;

import com.clicktrack.model.Publisher;
import com.clicktrack.repository.PublisherRepository;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Optional<Publisher> getById(Long id) {
        return publisherRepository.findById(id);
    }
}