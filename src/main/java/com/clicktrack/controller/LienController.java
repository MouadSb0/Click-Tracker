package com.clicktrack.controller;

import com.clicktrack.model.Lien;
import com.clicktrack.service.LienService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur Spring MVC pour la création des liens de tracking associés aux éditeurs.
 * Endpoint pris en charge : POST /api/publishers/{id}/liens (retourne HTTP 201 Created).
 */
@Controller
@RequestMapping("/api/publishers")
public class LienController {

    private final LienService lienService;

    /**
     * Constructeur avec injection du service LienService.
     *
     * @param lienService Service métier des liens
     */
    public LienController(LienService lienService) {
        this.lienService = lienService;
    }

    /**
     * Endpoint POST /api/publishers/{id}/liens
     * Crée un nouveau lien de tracking rattaché à l'éditeur spécifié.
     * Répond aux exigences du cahier des charges :
     * - En tant que publisher, je crée un lien de tracking vers une URL de destination, rattaché à une offre.
     * - Code de réponse HTTP : 201 Created.
     *
     * @param publisherId Identifiant du publisher
     * @param lien        Données du lien reçues dans le corps de requête JSON
     * @return Le lien créé avec statut HTTP 201
     */
    @PostMapping("/{id}/liens")
    public ResponseEntity<Lien> createLien(
            @PathVariable("id") Long publisherId,
            @RequestBody Lien lien
    ) {
        Lien createdLien = lienService.createLien(publisherId, lien);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLien);
    }
}