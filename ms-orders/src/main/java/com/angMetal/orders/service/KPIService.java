package com.angMetal.orders.service;

import com.angMetal.orders.entity.FactureAchat;
import com.angMetal.orders.entity.FactureVente;
import com.angMetal.orders.entity.Product;
import com.angMetal.orders.entity.payloads.KPIResponse;
import com.angMetal.orders.repositories.FactureAchatRepository;
import com.angMetal.orders.repositories.FactureVenteRepository;
import com.angMetal.orders.repositories.ProductRepository;
import com.angMetal.orders.repositories.TransactionRepository;
import models.TransactionMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KPIService {

    @Autowired
    private FactureAchatRepository factureAchatRepository;

    @Autowired
    private FactureVenteRepository factureVenteRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Retrieve all transactions
    public List<TransactionMySQL> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public KPIResponse getKPIs() {
        List<TransactionMySQL> transactions = transactionRepository.findAll(); // Get all transactions
        List<FactureVente> ventes = factureVenteRepository.findAll(); // Fetch all sales invoices
        List<FactureAchat> achats = factureAchatRepository.findAll(); // Fetch all purchase invoices

        // Initialize KPI counters
        double totalSales = 0.0;
        double totalPurchases = 0.0;
        double totalTransactions = 0.0;
        int debitCount = 0;
        int creditCount = 0;

        // Variables for additional KPIs
        double totalProfitMargin = 0.0;
        double purchaseToSalesRatio = 0.0;
        double averagePurchasePrice = 0.0;
        double averageSalePrice = 0.0;

        Map<String, Double> productSales = new HashMap<>();
        Map<String, Double> productPurchases = new HashMap<>();
        Map<String, Double> supplierPurchases = new HashMap<>();
        Map<String, Double> clientSales = new HashMap<>();

        // Calculate totals and gather data for KPIs
        for (FactureVente vente : ventes) {
            totalSales += vente.getMontantTotal();

            // Track purchases by supplier
            String clientName = vente.getClient().getName();
            clientSales.put(clientName, clientSales.getOrDefault(clientName, 0.0) + vente.getMontantTotal());

            // Process products and track sales
            for (Product product : vente.getProducts()) {
                String productName = product.getName();
                double saleAmount = product.getPrixUnitaire() * product.getQuantiteEnStock();
                productSales.put(productName, productSales.getOrDefault(productName, 0.0) + saleAmount);
            }

            if ("CREDIT".equals(vente.getPaymentType().toString())) {
                creditCount++;
            }
        }

        for (FactureAchat achat : achats) {
            totalPurchases += achat.getMontantTotal();

            // Track purchases by supplier
            String supplierName = achat.getFournisseur().getName();
            supplierPurchases.put(supplierName, supplierPurchases.getOrDefault(supplierName, 0.0) + achat.getMontantTotal());

            // Process products and track purchases
            for (Product product : achat.getProducts()) {
                String productName = product.getName();
                double purchaseAmount = product.getPrixUnitaire() * product.getQuantiteEnStock();
                productPurchases.put(productName, productPurchases.getOrDefault(productName, 0.0) + purchaseAmount);
            }

            if ("CREDIT".equals(achat.getPaymentType().toString())) {
                debitCount++;
            }
        }

        // Calculate total transactions (sum of both total sales and purchases)
        totalTransactions = totalSales + totalPurchases;

        // Calculate profit margin
        if (totalPurchases != 0) {
            totalProfitMargin = (totalSales - totalPurchases) / totalPurchases * 100;
        }

        // Calculate Purchase to Sales Ratio (only if totalSales is non-zero)
        if (totalSales != 0) {
            purchaseToSalesRatio = totalPurchases / totalSales;
        }

        // Calculate Average Purchase Price and Average Sale Price
        if (!achats.isEmpty()) {
            averagePurchasePrice = totalPurchases / achats.size();
        }
        if (!ventes.isEmpty()) {
            averageSalePrice = totalSales / ventes.size();
        }

        // Prepare the KPI response
        KPIResponse response = new KPIResponse();
        response.setTotalSales(totalSales);
        response.setTotalPurchases(totalPurchases);
        response.setTotalTransactions(totalTransactions);
        response.setDebitCount(debitCount);
        response.setCreditCount(creditCount);
        response.setProfitMargin(totalProfitMargin);
        response.setPurchaseToSalesRatio(purchaseToSalesRatio);
        response.setAveragePurchasePrice(averagePurchasePrice);
        response.setAverageSalePrice(averageSalePrice);
        response.setProductSales(productSales);
        response.setProductPurchases(productPurchases);
        response.setSupplierPurchases(supplierPurchases);
        response.setClientSales(clientSales);

        return response;
    }
}
