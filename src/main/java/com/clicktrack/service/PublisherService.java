package com.clicktrack.service;

import com.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    List<Publisher> getAllPublishers();

    Optional<Publisher> getById(Long id);
}