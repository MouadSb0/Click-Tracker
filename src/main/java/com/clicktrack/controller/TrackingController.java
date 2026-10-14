package com.clicktrack.controller;

import com.clicktrack.model.Lien;
import com.clicktrack.model.Publisher;
import com.clicktrack.service.LienService;
import com.clicktrack.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/t")
public class TrackingController {
    private final LienService lienService;
    private final PublisherService publisherService;

    public TrackingController(LienService lienService, PublisherService publisherService) {
        this.lienService = lienService;
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> track(@PathVariable("id") Long id) {
        Lien existingLien = lienService.getLienById(id).orElse(null);
        if (existingLien == null) {
            return ResponseEntity.notFound().build();
        }

        Publisher publisher = publisherService.getById(existingLien.getPublisherId()).orElse(null);
        if (publisher == null) {
            return ResponseEntity.notFound().build();
        }

        Lien lien = lienService.click(id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("urlRedirection", lien.getUrlDestination());
        response.put("publisher", publisher.getNom() + " " + publisher.getPrenom());
        response.put("offre", lien.getOffre());
        response.put("clicks", lien.getClicks());

        return ResponseEntity.ok(response);
    }
}