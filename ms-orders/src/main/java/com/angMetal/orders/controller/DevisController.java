package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Devis;
import com.angMetal.orders.service.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/devis", produces = MediaType.APPLICATION_JSON_VALUE )
public class DevisController {

    private final DevisService devisService;

    @Autowired
    public DevisController(DevisService devisService) {
        this.devisService = devisService;
    }

    // Endpoint to get all Devis
    @GetMapping
    public List<Devis> getAllDevis() {
        return devisService.findAllDevis();
    }

    // Endpoint to get a Devis by ID
    @GetMapping("/{id}")
    public Optional<Devis> getDevisById(@PathVariable Long id) {
        return devisService.findDevisById(id);
    }

    @PostMapping
    public ResponseEntity<Devis> createOrUpdateDevis(@RequestBody Devis devis) {
        if (devis.getClient() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Devis savedDevis = devisService.saveDevis(devis);
        return ResponseEntity.ok(savedDevis);
    }

    @DeleteMapping("/{id}")
    public void deleteDevis(@PathVariable Long id) {
        devisService.deleteDevis(id);
    }
}
