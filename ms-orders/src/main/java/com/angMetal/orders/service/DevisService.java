package com.angMetal.orders.service;

import com.angMetal.orders.entity.Devis;
import com.angMetal.orders.repositories.DevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DevisService {

    private final DevisRepository devisRepository;

    @Autowired
    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    // Get all Devis records
    public List<Devis> findAllDevis() {
        return devisRepository.findAll();
    }

    // Get a single Devis by ID
    public Optional<Devis> findDevisById(Long id) {
        return devisRepository.findById(id);
    }

    // Save or update a Devis
    public Devis saveDevis(Devis devis) {
        return devisRepository.save(devis);
    }

    // Delete a Devis by ID
    public void deleteDevis(Long id) {
        devisRepository.deleteById(id);
    }
}
