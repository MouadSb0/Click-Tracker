package com.clicktrack.controller;

import com.clicktrack.dto.OffreDto;
import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.service.LienService;
import com.clicktrack.service.OffreService;
import com.clicktrack.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Contrôleur Spring MVC exposant les ressources REST liées aux Publishers.
 * Mappings pris en charge :
 * - GET /api/publishers : liste de tous les publishers
 * - GET /api/publishers/{id}/liens : liste des liens d'un publisher
 * - GET /api/publishers/{id}/offres : liste des offres agrégées d'un publisher
 */
@Controller
@ResponseBody
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    private final LienService lienService;
    private final OffreService offreService;

    /**
     * Constructeur injectant les services requis par Spring.
     *
     * @param publisherService Service de gestion des publishers
     * @param lienService      Service de gestion des liens
     * @param offreService     Service de gestion et d'agrégation des offres
     */
    public PublisherController(
            PublisherService publisherService,
            LienService lienService,
            OffreService offreService
    ) {
        this.publisherService = publisherService;
        this.lienService = lienService;
        this.offreService = offreService;
    }

    /**
     * Endpoint GET /api/publishers
     * Récupère la liste de tous les éditeurs enregistrés.
     *
     * @return Liste JSON des éditeurs (statut HTTP 200)
     */
    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    /**
     * Endpoint GET /api/publishers/{id}/liens
     * Récupère la liste des liens de tracking créés par un éditeur spécifique.
     *
     * @param id Identifiant unique du publisher
     * @return Liste JSON des liens de l'éditeur (statut HTTP 200)
     */
    @GetMapping("/{id}/liens")
    public List<Lien> getLiensByPublisher(@PathVariable("id") Long id) {
        return lienService.getLiensByPublisher(id);
    }

    /**
     * Endpoint GET /api/publishers/{id}/offres
     * Récupère la synthèse des offres d'un éditeur avec le nombre de liens et le total de clics par offre.
     *
     * @param id Identifiant unique du publisher
     * @return Liste JSON des offres agrégées (statut HTTP 200)
     */
    @GetMapping("/{id}/offres")
    public List<OffreDto> getOffresByPublisher(@PathVariable("id") Long id) {
        return offreService.getOffresByPublisher(id);
    }
}