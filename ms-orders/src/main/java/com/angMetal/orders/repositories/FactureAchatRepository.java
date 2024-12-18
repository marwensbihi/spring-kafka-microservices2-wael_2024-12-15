package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.FactureAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureAchatRepository extends JpaRepository<FactureAchat, Long> {
    // Calculate the total cost of purchases (similar to total revenue for sales)
 /*   @Query("SELECT SUM(fa.montantTotal) FROM facture_achat fa")
    double calculateTotalPurchases();

    // Count total number of purchase orders
    @Query("SELECT COUNT(fa) FROM facture_achat fa")
    long countPurchaseOrders();*/
}