package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Devis;
import com.angMetal.orders.entity.payloads.DevisProduct;
import com.angMetal.orders.repositories.DevisRepository;
import com.angMetal.orders.service.DevisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/devis", produces = MediaType.APPLICATION_JSON_VALUE)
public class DevisController {

    DevisRepository devisRepository;
    private final DevisService devisService;

    public DevisController(DevisRepository devisRepository, DevisService devisService) {
        this.devisRepository = devisRepository;
        this.devisService = devisService;
    }

    // Get all Devis
    @GetMapping
    public ResponseEntity<List<Devis>> getAllDevis() {
        List<Devis> devisList = devisRepository.findAll();
        return ResponseEntity.ok(devisList);
    }

    // Get Devis by ID
    @GetMapping("/{id}")
    public ResponseEntity<Devis> getDevisById(@PathVariable Long id) {
        return devisRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deleting Devi by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeviById(@PathVariable Long id) {
        Optional<Devis> devi = devisRepository.findById(id);

        if (devi.isPresent()) {
            devisRepository.deleteById(id);  // Perform the delete operation
            return ResponseEntity.ok("Devi with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Devi with ID " + id + " not found.");
        }
    }



    // Create or Update a Devis with products and quantities
    @PostMapping
    public ResponseEntity<Devis> createDevis(@RequestBody Devis devis) {
        // Ensure each DevisProduct is linked to the Devis
        List<DevisProduct> updatedDevisProducts = devis.getDevisProducts();


        // Set updated products back to the Devis
        devis.setDevisProducts(updatedDevisProducts);

        // Save the Devis (cascade should persist products)
        Devis savedDevis = devisRepository.save(devis);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevis);
    }
}