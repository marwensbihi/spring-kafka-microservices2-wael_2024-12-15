package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Banque;
import com.angMetal.orders.repositories.BanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/banques", produces = MediaType.APPLICATION_JSON_VALUE)
public class BanqueController {

    @Autowired
    private BanqueRepository banqueRepository;

    // Create a new Banque
    @PostMapping
    public ResponseEntity<Banque> createBanque(@RequestBody Banque banque) {
        Banque savedBanque = banqueRepository.save(banque);
        return ResponseEntity.ok(savedBanque);
    }

    // Get all Banques
    @GetMapping
    public List<Banque> getAllBanques() {
        return banqueRepository.findAll();
    }

    // Get a Banque by ID
    @GetMapping("/{id}")
    public ResponseEntity<Banque> getBanqueById(@PathVariable Long id) {
        Optional<Banque> banque = banqueRepository.findById(id);
        return banque.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a Banque
    @PutMapping("/{id}")
    public ResponseEntity<Banque> updateBanque(@PathVariable Long id, @RequestBody Banque banqueDetails) {
        Optional<Banque> optionalBanque = banqueRepository.findById(id);

        if (optionalBanque.isPresent()) {
            Banque banque = optionalBanque.get();
            banque.setName(banqueDetails.getName());
            banque.setSoldeInitial(banqueDetails.getSoldeInitial());
            banque.setSoldeActuel(banqueDetails.getSoldeActuel());
            Banque updatedBanque = banqueRepository.save(banque);
            return ResponseEntity.ok(updatedBanque);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Banque
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanque(@PathVariable Long id) {
        Optional<Banque> banque = banqueRepository.findById(id);

        if (banque.isPresent()) {
            banqueRepository.delete(banque.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}