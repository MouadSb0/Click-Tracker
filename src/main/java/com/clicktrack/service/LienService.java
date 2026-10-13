package com.clicktrack.service;

import com.clicktrack.model.Lien;

import java.util.List;
import java.util.Optional;

public interface LienService {
    Lien createLien(Long publisherId, Lien lien);

    List<Lien> getLiensByPublisher(Long publisherId);

    Optional<Lien> getLienById(Long id);

    Lien click(Long id);
}