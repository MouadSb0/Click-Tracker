package com.clicktrack.service;

import com.clicktrack.model.Lien;
import com.clicktrack.repository.LienRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service métier {@link LienService}.
 * Coordonne les actions sur les liens de tracking en déléguant la persistance à {@link LienRepository}.
 */
public class LienServiceImpl implements LienService {

    private final LienRepository lienRepository;

    /**
     * Constructeur avec injection de la dépendance du dépôt par constructeur.
     *
     * @param lienRepository Dépôt des liens
     */
    public LienServiceImpl(LienRepository lienRepository) {
        this.lienRepository = lienRepository;
    }

    /**
     * Associe le publisherId au lien, s'assure de l'initialisation du compteur et persiste le lien.
     *
     * @param publisherId Identifiant du publisher
     * @param lien        Lien à créer
     * @return Lien sauvegardé avec son ID
     */
    @Override
    public Lien createLien(Long publisherId, Lien lien) {
        lien.setPublisherId(publisherId);
        return lienRepository.save(lien);
    }

    /**
     * Retourne tous les liens créés par le publisher spécifié.
     *
     * @param publisherId Identifiant du publisher
     * @return Liste des liens correspondants
     */
    @Override
    public List<Lien> getLiensByPublisher(Long publisherId) {
        return lienRepository.findByPublisherId(publisherId);
    }

    /**
     * Recherche un lien par son ID unique.
     *
     * @param id Identifiant du lien
     * @return Optional contenant le lien si trouvé
     */
    @Override
    public Optional<Lien> getLienById(Long id) {
        return lienRepository.findById(id);
    }

    /**
     * Déclenche l'incrémentation atomique des clics sur le lien ciblé et retourne sa version à jour.
     *
     * @param id Identifiant du lien
     * @return Le lien après incrémentation
     */
    @Override
    public Lien click(Long id) {
        lienRepository.incrementClicks(id);
        return lienRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lien not found: " + id));
    }
}