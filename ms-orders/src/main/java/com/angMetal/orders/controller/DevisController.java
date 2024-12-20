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
@RequestMapping(value = "/devis", produces = MediaType.APPLICATION_JSON_VALUE)
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

    // Endpoint to create or update Devis (new or existing)
    @PostMapping
    public ResponseEntity<Devis> createDevis(@RequestBody Devis devis) {
        if (devis.getClient() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Devis savedDevis = devisService.saveDevis(devis);
        return ResponseEntity.ok(savedDevis);
    }

    // Endpoint to update an existing Devis
    @PutMapping("/{id}")
    public ResponseEntity<Devis> updateDevis(@PathVariable Long id, @RequestBody Devis devis) {
        Optional<Devis> existingDevisOpt = devisService.findDevisById(id);
        if (existingDevisOpt.isPresent()) {
            Devis existingDevis = existingDevisOpt.get();
            // Update properties of the existing Devis with new data from the request
            existingDevis.setClient(devis.getClient());
            existingDevis.setDateCreation(devis.getDateCreation());
            existingDevis.setDateExpiration(devis.getDateExpiration());
            // Add any other fields that need to be updated

            Devis updatedDevis = devisService.saveDevis(existingDevis);
            return ResponseEntity.ok(updatedDevis);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete Devis
    @DeleteMapping("/{id}")
    public void deleteDevis(@PathVariable Long id) {
        devisService.deleteDevis(id);
    }
}
