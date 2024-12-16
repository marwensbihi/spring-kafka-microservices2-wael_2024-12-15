package com.angMetal.orders.controller;

import com.angMetal.orders.payloads.request.FactureRequest;
import com.angMetal.orders.entity.FactureAchat;
import com.angMetal.orders.entity.FactureVente;
import com.angMetal.orders.repositories.FactureVenteRepository;
import com.angMetal.orders.service.FactureService;
import com.angMetal.orders.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.angMetal.orders.repositories.FactureAchatRepository;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;
    private final KafkaProducerService kafkaProducerService;
    @Autowired
    private final  FactureAchatRepository factureAchatRepository;
    @Autowired
    private final FactureVenteRepository factureVenteRepository;

    /**
     * Endpoint to create a new facture vente.
     *
     * @param factureAchat FactureVente object.
     * @return The created FactureVente.
     */
    @PostMapping("/vente")
    public FactureVente createFactureVente(@RequestBody @Valid FactureRequest factureAchat) {
        // Save factureVente in database
        FactureVente savedFacture = factureService.processFactureVente(factureAchat);

        return savedFacture;
    }

    /**
     * Endpoint to get all facture achat.
     *
     * @return all The FactureAchat.
     */

    @GetMapping("/vente")
    public List<FactureVente> getAllFactureVente() {
        return factureVenteRepository.findAll();
    }

    /**
     * Endpoint to create a new facture achat.
     *
     * @param factureAchat FactureAchat object.
     * @return The created FactureAchat.
     */
    @PostMapping("/achat")
    public FactureAchat createFactureAchat(@RequestBody @Valid FactureRequest factureAchat) {
        // Save factureAchat in database
        FactureAchat savedFacture = factureService.processAchatFacture(factureAchat);

        return savedFacture;
    }

    /**
     * Endpoint to get all facture achat.
     *
     * @return all The FactureAchat.
     */
    @GetMapping("/achat")
    public List<FactureAchat> getAllFactureAchat() {


        return factureAchatRepository.findAll();
    }
}
