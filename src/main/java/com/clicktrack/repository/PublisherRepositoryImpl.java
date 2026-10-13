package com.clicktrack.repository;

import com.clicktrack.model.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {
    private final List<Publisher> publishers = new ArrayList<>();

    @Override
    public synchronized List<Publisher> findAll() {
        return new ArrayList<>(publishers);
    }

    @Override
    public synchronized Optional<Publisher> findById(Long id) {
        return publishers.stream()
                .filter(publisher -> publisher.getId().equals(id))
                .findFirst();
    }
}