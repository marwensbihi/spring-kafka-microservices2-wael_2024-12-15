package com.angMetal.orders.service;

import com.angMetal.orders.entity.Report;
import com.angMetal.orders.entity.Timesheet;
import com.angMetal.orders.repositories.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TimesheetService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TimesheetRepository timesheetRepository;

    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    public Optional<Timesheet> getTimesheetById(Long id) {
        return timesheetRepository.findById(id);
    }

    @Transactional
    public Timesheet createTimesheet(Timesheet timesheet) {
        Long reportId = timesheet.getReport().getReportId();
        Report report = entityManager.find(Report.class, reportId);

        if (report != null) {
            timesheet.setReport(report);  // Associate the found Report with the Timesheet
        } else {
            throw new RuntimeException("Report not found with ID: " + reportId);
        }

        entityManager.persist(timesheet);
        return timesheet;
    }


    public Timesheet updateTimesheet(Long id, Timesheet timesheet) {
        if (timesheetRepository.existsById(id)) {
            timesheet.setId(id);
            return timesheetRepository.save(timesheet);
        }
        return null;
    }

    public boolean deleteTimesheet(Long id) {
        if (timesheetRepository.existsById(id)) {
            timesheetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}