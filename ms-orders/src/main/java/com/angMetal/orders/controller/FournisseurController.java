package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Fournisseur;
import com.angMetal.orders.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fournisseurs", produces = MediaType.APPLICATION_JSON_VALUE)
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    // Get all fournisseurs
    @GetMapping
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurService.getAllFournisseurs();
    }

    // Get a fournisseur by ID
    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
        if (fournisseur != null) {
            return ResponseEntity.ok(fournisseur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new fournisseur
    @PostMapping
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurService.createFournisseur(fournisseur);
    }

    // Update an existing fournisseur
    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(id, fournisseur);
        if (updatedFournisseur != null) {
            return ResponseEntity.ok(updatedFournisseur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a fournisseur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        boolean isDeleted = fournisseurService.deleteFournisseur(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
