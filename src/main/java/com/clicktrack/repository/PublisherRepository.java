package com.clicktrack.repository;

import com.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {
    List<Publisher> findAll();

    Optional<Publisher> findById(Long id);
}