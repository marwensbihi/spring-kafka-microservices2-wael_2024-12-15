package com.angMetal.orders.entity.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
public class KPIResponse {
    private double totalSales;
    private double totalPurchases;
    private double totalTransactions;
    private int debitCount;
    private int creditCount;
    private double profitMargin;
    private double purchaseToSalesRatio;
    private double averagePurchasePrice;
    private double averageSalePrice;
    private Map<String, Double> productSales;
    private Map<String, Double> productPurchases;
    private Map<String, Double> supplierPurchases;
    private Map<String, Double> clientSales;

    public KPIResponse(double totalSales, double totalPurchases, double totalTransactions, int debitCount, int creditCount, double profitMargin, double purchaseToSalesRatio, double averagePurchasePrice, double averageSalePrice, Map<String, Double> productSales, Map<String, Double> productPurchases, Map<String, Double> supplierPurchases, Map<String, Double> clientSales) {
        this.totalSales = totalSales;
        this.totalPurchases = totalPurchases;
        this.totalTransactions = totalTransactions;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.profitMargin = profitMargin;
        this.purchaseToSalesRatio = purchaseToSalesRatio;
        this.averagePurchasePrice = averagePurchasePrice;
        this.averageSalePrice = averageSalePrice;
        this.productSales = productSales;
        this.productPurchases = productPurchases;
        this.supplierPurchases = supplierPurchases;
        this.clientSales = clientSales;
    }


    public KPIResponse() {

    }
}
