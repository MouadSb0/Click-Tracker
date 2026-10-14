package com.clicktrack.controller;

import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.service.LienService;
import com.clicktrack.service.PublisherService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contrôleur de tracking pour le suivi des clics sur les liens raccourcis/trackés.
 * Endpoint principal : GET /t/{id}.
 * À chaque ouverture du lien, incrémente le compteur de clics de manière atomique et thread-safe.
 */
@Controller
@RequestMapping("/t")
public class TrackingController {

    private final LienService lienService;
    private final PublisherService publisherService;

    /**
     * Constructeur avec injection des dépendances métier par constructeur.
     *
     * @param lienService      Service de gestion des liens
     * @param publisherService Service de gestion des éditeurs
     */
    public TrackingController(LienService lienService, PublisherService publisherService) {
        this.lienService = lienService;
        this.publisherService = publisherService;
    }

    /**
     * Traite un clic de tracking sur un lien donné par son identifiant.
     * 1. Vérifie l'existence du lien et de son éditeur associé (renvoie 404 si introuvable).
     * 2. Incrémente le compteur de clics de manière thread-safe (règle de non-perte de clics simultanés).
     * 3. Renvoie les informations au format JSON (urlRedirection, publisher, offre, clicks).
     *    Bonus : Supporte la redirection HTTP 302 vers l'URL de destination lorsque demandée (paramètre ?redirect=true ou en-tête navigateur text/html).
     *
     * @param id           Identifiant unique du lien de tracking
     * @param redirect     Paramètre optionnel permettant de forcer la redirection HTTP 302
     * @param acceptHeader En-tête HTTP Accept pour détecter un navigateur web
     * @return Réponse HTTP 200 (JSON) ou HTTP 302 (Redirection vers URL de destination), ou 404 si introuvable
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> track(
            @PathVariable("id") Long id,
            @RequestParam(value = "redirect", required = false, defaultValue = "false") boolean redirect,
            @RequestHeader(value = "Accept", required = false) String acceptHeader
    ) {
        Lien existingLien = lienService.getLienById(id).orElse(null);
        if (existingLien == null) {
            return ResponseEntity.notFound().build();
        }

        Publisher publisher = publisherService.getById(existingLien.getPublisherId()).orElse(null);
        if (publisher == null) {
            return ResponseEntity.notFound().build();
        }

        // Incrémentation protégée contre la concurrence
        Lien lien = lienService.click(id);

        // Bonus : Redirection vers la page de destination (HTTP 302)
        // Activé si demandé explicitement via ?redirect=true ou lors d'une navigation web (text/html)
        boolean shouldRedirect = redirect || (acceptHeader != null && acceptHeader.contains("text/html") && !acceptHeader.contains("application/json"));
        if (shouldRedirect) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(lien.getUrlDestination()))
                    .build();
        }

        // Mode standard API JSON avec en-tête Location et corps de réponse complet
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("urlRedirection", lien.getUrlDestination());
        response.put("publisher", publisher.getPrenom() + " " + publisher.getNom());
        response.put("offre", lien.getOffre());
        response.put("clicks", lien.getClicks());

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, lien.getUrlDestination())
                .body(response);
    }
}