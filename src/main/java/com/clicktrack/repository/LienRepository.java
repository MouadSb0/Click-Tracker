package com.clicktrack.repository;

import com.clicktrack.model.Lien;

import java.util.List;
import java.util.Optional;

public interface LienRepository {
    Lien save(Lien lien);

    Optional<Lien> findById(Long id);

    List<Lien> findByPublisherId(Long publisherId);

    int incrementClicks(Long id);
}