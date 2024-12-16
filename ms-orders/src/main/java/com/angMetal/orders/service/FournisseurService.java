package com.angMetal.orders.service;

import com.angMetal.orders.entity.Fournisseur;
import com.angMetal.orders.repositories.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    // Get all fournisseurs
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    // Get a fournisseur by ID
    public Fournisseur getFournisseurById(Long id) {
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return fournisseur.orElse(null);
    }

    // Create a new fournisseur
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    // Update an existing fournisseur
    public Fournisseur updateFournisseur(Long id, Fournisseur fournisseurDetails) {
        Optional<Fournisseur> existingFournisseur = fournisseurRepository.findById(id);
        if (existingFournisseur.isPresent()) {
            Fournisseur fournisseur = existingFournisseur.get();
            fournisseur.setName(fournisseurDetails.getName());
            fournisseur.setAdresse(fournisseurDetails.getAdresse());
            fournisseur.setEmail(fournisseurDetails.getEmail());
            fournisseur.setNumeroTel(fournisseurDetails.getNumeroTel());
            return fournisseurRepository.save(fournisseur);
        }
        return null;
    }

    // Delete a fournisseur
    public boolean deleteFournisseur(Long id) {
        Optional<Fournisseur> existingFournisseur = fournisseurRepository.findById(id);
        if (existingFournisseur.isPresent()) {
            fournisseurRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
