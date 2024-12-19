package com.angMetal.orders.controller;

import com.angMetal.orders.service.KPIService;
import com.angMetal.orders.entity.payloads.KPIResponse;
import models.TransactionMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/kpi", produces = MediaType.APPLICATION_JSON_VALUE)
public class KPIController {

    @Autowired
    private KPIService kpiService;

    @GetMapping("/transactions")
    public List<TransactionMySQL> getAllTransactions() {
        return kpiService.getAllTransactions();
    }

    @GetMapping("/kpis")
    public KPIResponse getKPIs() {
        return kpiService.getKPIs();  // Return calculated KPIs
    }

}
