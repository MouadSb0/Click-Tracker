package com.clicktrack.service;

import com.clicktrack.dto.OffreDto;
import com.clicktrack.model.Lien;
import com.clicktrack.repository.LienRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation du service métier {@link OffreService}.
 * Effectue l'agrégation statistique des offres par publisher à partir des liens enregistrés dans {@link LienRepository}.
 */
public class OffreServiceImpl implements OffreService {

    private final LienRepository lienRepository;

    /**
     * Constructeur avec injection du dépôt des liens.
     *
     * @param lienRepository Dépôt des liens
     */
    public OffreServiceImpl(LienRepository lienRepository) {
        this.lienRepository = lienRepository;
    }

    /**
     * Calcule l'agrégation des offres pour un publisher :
     * regroupe les liens par nom d'offre, compte le nombre de liens distincts et totalise leurs clics.
     * Utilise une {@link LinkedHashMap} pour préserver l'ordre d'insertion des offres.
     *
     * @param publisherId Identifiant du publisher
     * @return Liste ordonnée de DTOs {@link OffreDto}
     */
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