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

@Controller
@RequestMapping("/api/publishers")
public class LienController {
    private final LienService lienService;

    public LienController(LienService lienService) {
        this.lienService = lienService;
    }

    @PostMapping("/{id}/liens")
    public ResponseEntity<Lien> createLien(
            @PathVariable("id") Long publisherId,
            @RequestBody Lien lien
    ) {
        Lien createdLien = lienService.createLien(publisherId, lien);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLien);
    }
}