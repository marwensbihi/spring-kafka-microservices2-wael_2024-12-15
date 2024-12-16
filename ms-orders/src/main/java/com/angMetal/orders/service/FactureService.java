package com.angMetal.orders.service;

import com.angMetal.orders.entity.*;
import com.angMetal.orders.kafka.FactureProducer;
import com.angMetal.orders.payloads.request.FactureRequest;
import com.angMetal.orders.repositories.*;
import enums.PaymentType;
import models.FactureEvent;
import enums.FactureType;
import lombok.RequiredArgsConstructor;
import models.ProductDTO;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureProducer factureProducer;
    private final FactureVenteRepository factureVenteRepository;
    private final FactureAchatRepository factureAchatRepository;
    private final ProductRepository productRepository;
    private final FournisseurRepository fournisseurRepository;

    private final ClientRepository clientRepository;

    /**
     * Create a new vente facture and send the corresponding event to Kafka.
     *
     * @param factureRequest The vente facture to be created.
     * @return The created vente facture.
     */
    @Transactional
    public FactureVente processFactureVente(FactureRequest factureRequest) {

        FactureVente savedFactureVente = createFactureVente(factureRequest);

        // Update product stock after sale
        //updateProductStock(factureRequest, FactureType.VENTE);

        // Create FactureEvent for vente facture
        FactureEvent factureEvent = buildFactureEvent(savedFactureVente, FactureType.VENTE, factureRequest);

        // Send the facture event to Kafka
        factureProducer.sendFactureEvent(factureEvent);

        return savedFactureVente;
    }

    private FactureVente createFactureVente(FactureRequest dto) {
        // Find the client by clientId
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        // Get the list of product IDs from the request DTO
        List<Long> productIds = dto.getProductIds();

        // Find all products by their IDs
        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new ResourceNotFoundException("One or more products not found");
        }

        // Create a new VenteFacture entity and set its fields
        FactureVente factureVente = new FactureVente();
        factureVente.setMontantTotal(dto.getMontantTotal());
        factureVente.setDateEmission(dto.getDateEmission());
        factureVente.setDateEcheance(dto.getDateEcheance());
        factureVente.setPaymentType(PaymentType.CREDIT);
        factureVente.setClient(client);
        factureVente.setProducts(products);

        // Save and return the factureVente
        return factureVenteRepository.save(factureVente);
    }

    /**
     * Create a new achat facture and send the corresponding event to Kafka.
     *
     * @param factureRequest The achat facture to be created.
     * @return The created achat facture.
     */
    @Transactional
    public FactureAchat processAchatFacture(FactureRequest factureRequest) {

        FactureAchat savedFactureAchat = createAchatFacture(factureRequest);

        // Update product stock after purchase
        //updateProductStock(factureRequest, FactureType.ACHAT);

        // Create FactureEvent for achat facture
        FactureEvent factureEvent = buildFactureEvent(savedFactureAchat, FactureType.ACHAT, factureRequest);

        // Send the facture event to Kafka
        factureProducer.sendFactureEvent(factureEvent);

        return savedFactureAchat;
    }

    private FactureAchat createAchatFacture(FactureRequest dto) {
        // Find the fournisseur by fournisseurId
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur not found"));

        // Find the products by product IDs
        List<Product> products = productRepository.findAllById(dto.getProductIds());
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found");
        }

        // Create a new FactureAchat entity and set its fields
        FactureAchat factureAchat = new FactureAchat();
        factureAchat.setMontantTotal(dto.getMontantTotal());
        factureAchat.setDateEmission(dto.getDateEmission());
        factureAchat.setDateEcheance(dto.getDateEcheance());
        factureAchat.setPaymentType(PaymentType.CREDIT);
        factureAchat.setFournisseur(fournisseur);
        factureAchat.setProducts(products);

        // Save the factureAchat entity
        return factureAchatRepository.save(factureAchat);
    }

    /**
     * Update product stock based on facture type (VENTE or ACHAT).
     *
     * @param factureRequest The list of products involved in the facture.
     * @param factureType    The type of facture (VENTE or ACHAT).
     */
    private void updateProductStock(FactureRequest factureRequest, FactureType factureType) {
        for (FactureRequest.ProductWithQuantity product : factureRequest.getProducts()) {
            Optional<Product> productOptional = productRepository.findById(product.getProductId());
            if (productOptional.isPresent()) {
                Product existingProduct = productOptional.get();
                if (factureType == FactureType.VENTE) {
                    // Decrease stock for vente
                    existingProduct.setQuantiteEnStock(existingProduct.getQuantiteEnStock() - product.getQuantity());
                } else if (factureType == FactureType.ACHAT) {
                    // Increase stock for achat
                    existingProduct.setQuantiteEnStock(existingProduct.getQuantiteEnStock() + product.getQuantity());
                }
                // Save the updated product
                productRepository.save(existingProduct);
            }
        }
    }

    /**
     * Build a FactureEvent from a facture (vente or achat).
     *
     * @param facture     The facture (vente or achat).
     * @param factureType The type of facture (VENTE or ACHAT).
     * @return The corresponding FactureEvent.
     */
    private FactureEvent buildFactureEvent(Object facture, FactureType factureType, FactureRequest factureRequest) {
        FactureEvent factureEvent = new FactureEvent();

        if (facture instanceof FactureVente) {
            FactureVente venteFacture = (FactureVente) facture;
            factureEvent.setFactureId(venteFacture.getFactureID());
            factureEvent.setMontantTotal(venteFacture.getMontantTotal());
            factureEvent.setDateEmission(venteFacture.getDateEmission());
            factureEvent.setDateEcheance(venteFacture.getDateEcheance());
            factureEvent.setClientId(venteFacture.getClient().getClientID());
            factureEvent.setPaymentType(venteFacture.getPaymentType());
            factureEvent.setBanqueId(factureRequest.getBanqueId());
            factureEvent.setPaymentType(PaymentType.CREDIT);

        } else if (facture instanceof FactureAchat) {
            FactureAchat achatFacture = (FactureAchat) facture;
            factureEvent.setFactureId(achatFacture.getBillID());
            factureEvent.setMontantTotal(achatFacture.getMontantTotal());
            factureEvent.setFournisseurId(achatFacture.getFournisseur().getFournisseurID());
            factureEvent.setDateEmission(achatFacture.getDateEmission());
            factureEvent.setDateEcheance(achatFacture.getDateEcheance());
            factureEvent.setPaymentType(achatFacture.getPaymentType());
            factureEvent.setPaymentType(achatFacture.getPaymentType());
            factureEvent.setBanqueId(factureRequest.getBanqueId());
            factureEvent.setPaymentType(PaymentType.DEBIT);
        }

        factureEvent.setProducts(factureRequest.getProducts().stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductID(product.getProductId());
                    productDTO.setQuantity(product.getQuantity());
                    return productDTO;
                })
                .collect(Collectors.toList()));
        factureEvent.setFactureType(factureType);

        return factureEvent;
    }


    /**
     * Retrieve a vente facture by its ID.
     *
     * @param factureID The ID of the vente facture.
     * @return The vente facture.
     */
    public Optional<FactureVente> getVenteFactureById(Long factureID) {
        return factureVenteRepository.findById(factureID);
    }

    /**
     * Retrieve an achat facture by its ID.
     *
     * @param billID The ID of the achat facture.
     * @return The achat facture.
     */
    public Optional<FactureAchat> getAchatFactureById(Long billID) {
        return factureAchatRepository.findById(billID);
    }
}
