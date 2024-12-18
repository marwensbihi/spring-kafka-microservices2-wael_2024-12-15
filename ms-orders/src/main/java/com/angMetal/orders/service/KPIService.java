package com.angMetal.orders.service;

import com.angMetal.orders.entity.payloads.KPIResponse;
import com.angMetal.orders.repositories.FactureAchatRepository;
import com.angMetal.orders.repositories.FactureVenteRepository;

import com.angMetal.orders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KPIService {

    @Autowired
    private FactureAchatRepository factureAchatRepository;

    @Autowired
    private FactureVenteRepository factureVenteRepository;

    @Autowired
    private ProductRepository productRepository;

   /* // Calculate total revenue (sum of 'montant_total' from orders)
    public KPIResponse calculateTotalRevenue() {
        double totalRevenue = factureVenteRepository.calculateTotalRevenue();  // Custom query method
        return new KPIResponse("Total Revenue", totalRevenue);
    }

    // Get orders by status (Pending, Completed, etc.)
    public KPIResponse getOrdersByStatus() {
        // Example query to get order counts by status
        int pendingOrders = orderRepository.countByStatus("Pending");
        int completedOrders = orderRepository.countByStatus("Completed");
        int canceledOrders = orderRepository.countByStatus("Canceled");

        String data = "Pending: " + pendingOrders + ", Completed: " + completedOrders + ", Canceled: " + canceledOrders;

        return new KPIResponse("Orders by Status", data);
    }

    // Get top-selling products
    public KPIResponse getTopSellingProducts() {
        String topProducts = factureVenteRepository.getTopSellingProducts(); // Custom query to get top-selling products
        return new KPIResponse("Top Selling Products", topProducts);
    }

    // Calculate average order value (total revenue / total orders)
   public KPIResponse calculateAverageOrderValue() {
        double totalRevenue = factureVenteRepository.calculateTotalRevenue();
        long totalOrders = factureVenteRepository.countOrders();  // Custom query method

        double avgOrderValue = totalRevenue / totalOrders;
        return new KPIResponse("Average Order Value", avgOrderValue);
    }*/
}
