package com.angMetal.orders.service;

import com.angMetal.orders.entity.Devis;
import com.angMetal.orders.entity.Product;
import com.angMetal.orders.repositories.DevisRepository;
import com.angMetal.orders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DevisService {

    private final DevisRepository devisRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DevisService(DevisRepository devisRepository, ProductRepository productRepository) {
        this.devisRepository = devisRepository;
        this.productRepository = productRepository;
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
    @Transactional
    public Devis saveDevis(Devis devis) {
        // Ensure products are saved before associating with Devis
        if (devis.getProducts() != null) {
            for (Product product : devis.getProducts()) {
                if (product.getProductID() == null) {
                    // Save product if it's new (ID is null)
                    productRepository.save(product);
                }
            }
        }

        // Save the Devis object (cascade will handle product saving if needed)
        return devisRepository.save(devis);
    }

    // Delete a Devis by ID
    public void deleteDevis(Long id) {
        devisRepository.deleteById(id);
    }
}
