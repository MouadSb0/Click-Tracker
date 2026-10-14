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

@Controller
@ResponseBody
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;
    private final LienService lienService;
    private final OffreService offreService;

    public PublisherController(
            PublisherService publisherService,
            LienService lienService,
            OffreService offreService
    ) {
        this.publisherService = publisherService;
        this.lienService = lienService;
        this.offreService = offreService;
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}/liens")
    public List<Lien> getLiensByPublisher(@PathVariable("id") Long id) {
        return lienService.getLiensByPublisher(id);
    }

    @GetMapping("/{id}/offres")
    public List<OffreDto> getOffresByPublisher(@PathVariable("id") Long id) {
        return offreService.getOffresByPublisher(id);
    }
}