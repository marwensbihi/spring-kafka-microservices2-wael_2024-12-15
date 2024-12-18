package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Report;
import com.angMetal.orders.entity.Report;
import com.angMetal.orders.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports") // Including versioning for better API management
public class ReportController {

    @Autowired
    private ReportService reportService;


    // Get all reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }



    // Get a report by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = (reportService.getReportById(id));
        if (report.isPresent()) {
            return new ResponseEntity<>(report.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Report>> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        Optional<Report> updatedReport = reportService.updateReport(id, reportDetails);
        if (updatedReport != null) {
            return new ResponseEntity<>(updatedReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a report by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        boolean isDeleted = reportService.deleteReport(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    // Create a new Report
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }


  

}
