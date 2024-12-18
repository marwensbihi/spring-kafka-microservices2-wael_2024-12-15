package com.angMetal.orders.controller;

import com.angMetal.orders.service.KPIService;
import com.angMetal.orders.entity.payloads.KPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kpi")
public class KPIController {

    @Autowired
    private KPIService kpiService;

    // Endpoint to get total revenue
  /*  @GetMapping("/total-revenue")
    public KPIResponse getTotalRevenue() {
        return kpiService.calculateTotalRevenue();
    }

    // Endpoint to get total orders count by status
    @GetMapping("/orders-by-status")
    public KPIResponse getOrdersByStatus() {
        return kpiService.getOrdersByStatus();
    }

    // Endpoint to get top-selling products
    @GetMapping("/top-selling-products")
    public KPIResponse getTopSellingProducts() {
        return kpiService.getTopSellingProducts();
    }

    // Endpoint to get average order value
    @GetMapping("/average-order-value")
    public KPIResponse getAverageOrderValue() {
        return kpiService.calculateAverageOrderValue();
    }
*/
    // Add more KPIs as needed...
}
