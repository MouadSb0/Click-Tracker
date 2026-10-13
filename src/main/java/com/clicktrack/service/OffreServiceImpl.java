package com.clicktrack.service;

import com.clicktrack.dto.OffreDto;
import com.clicktrack.model.Lien;
import com.clicktrack.repository.LienRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OffreServiceImpl implements OffreService {
    private final LienRepository lienRepository;

    public OffreServiceImpl(LienRepository lienRepository) {
        this.lienRepository = lienRepository;
    }

    @Override
    public List<OffreDto> getOffresByPublisher(Long publisherId) {
        Map<String, OffreDto> offres = new LinkedHashMap<>();
        List<Lien> liens = lienRepository.findByPublisherId(publisherId);

        for (Lien lien : liens) {
            OffreDto offreDto = offres.computeIfAbsent(
                    lien.getOffre(),
                    offre -> new OffreDto(offre, 0, 0)
            );
            offreDto.setNombreLiens(offreDto.getNombreLiens() + 1);
            offreDto.setClicks(offreDto.getClicks() + lien.getClicks());
        }

        return new ArrayList<>(offres.values());
    }
}