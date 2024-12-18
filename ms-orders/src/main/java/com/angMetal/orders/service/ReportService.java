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

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report createReport(Report report) {
        // Ensure that the entity is saved/merged correctly
        return reportRepository.save(report); // This will handle both create and update
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Optional<Report> updateReport(Long id, Report report) {
        if (reportRepository.existsById(id)) {
            // Make sure to set the report ID to the correct value before saving
            report.setReportId(id);
            return Optional.of(reportRepository.save(report)); // Save will handle update automatically
        }
        return Optional.empty();
    }

    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
