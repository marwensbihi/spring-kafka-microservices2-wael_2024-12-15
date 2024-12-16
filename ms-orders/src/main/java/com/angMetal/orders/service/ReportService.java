package com.angMetal.orders.service;

import com.angMetal.orders.entity.Report;
import com.angMetal.orders.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Report> report = reportRepository.findById(id);
        return report.orElse(null); // Return null if not found, or handle with custom exception
    }

    // Create a new report
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    // Update an existing report
    public Report updateReport(Long id, Report updatedReport) {
        Optional<Report> existingReport = reportRepository.findById(id);
        if (existingReport.isPresent()) {
            Report report = existingReport.get();
            // Update fields
            report.setTypeRapport(updatedReport.getTypeRapport());
            report.setPeriodeDebut(updatedReport.getPeriodeDebut());
            report.setPeriodeFin(updatedReport.getPeriodeFin());
            report.setBudget(updatedReport.getBudget());
            // Save the updated entity
            return reportRepository.save(report);
        }
        return null;
    }

    // Delete a report by ID
    public boolean deleteReport(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
