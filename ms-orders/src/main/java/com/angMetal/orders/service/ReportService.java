package com.angMetal.orders.service;

import com.angMetal.orders.entity.Report;
import com.angMetal.orders.repositories.ReportRepository;
import com.angMetal.orders.exception.ReportNotFoundException; // Custom exception for report not found
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    // Retrieve all reports
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Retrieve a report by its ID
    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report with id " + id + " not found"));
    }

    // Create a new report
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    // Update an existing report
    public Report updateReport(Long id, Report updatedReport) {
        Report existingReport = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report with id " + id + " not found"));

        existingReport.setTypeRapport(updatedReport.getTypeRapport());
        existingReport.setPeriodeDebut(updatedReport.getPeriodeDebut());
        existingReport.setPeriodeFin(updatedReport.getPeriodeFin());
        existingReport.setBudget(updatedReport.getBudget());

        return reportRepository.save(existingReport);
    }

    // Delete a report by ID
    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
