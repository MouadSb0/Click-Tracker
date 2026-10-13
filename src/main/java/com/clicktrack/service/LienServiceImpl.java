package com.clicktrack.service;

import com.clicktrack.model.Lien;
import com.clicktrack.repository.LienRepository;

import java.util.List;
import java.util.Optional;

public class LienServiceImpl implements LienService {
    private final LienRepository lienRepository;

    public LienServiceImpl(LienRepository lienRepository) {
        this.lienRepository = lienRepository;
    }

    @Override
    public Lien createLien(Long publisherId, Lien lien) {
        lien.setPublisherId(publisherId);
        return lienRepository.save(lien);
    }

    @Override
    public List<Lien> getLiensByPublisher(Long publisherId) {
        return lienRepository.findByPublisherId(publisherId);
    }

    @Override
    public Optional<Lien> getLienById(Long id) {
        return lienRepository.findById(id);
    }

    @Override
    public Lien click(Long id) {
        lienRepository.incrementClicks(id);
        return lienRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lien not found: " + id));
    }
}