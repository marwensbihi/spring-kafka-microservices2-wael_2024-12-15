package com.angMetal.orders.service;

import com.angMetal.orders.entity.TimeSheet;
import com.angMetal.orders.repositories.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSheetService {

    @Autowired
    private TimeSheetRepository TimeSheetRepository;

    // Retrieve all TimeSheets
    public List<TimeSheet> getAllTimeSheets() {
        return TimeSheetRepository.findAll();
    }

    // Retrieve a TimeSheet by its ID
    public TimeSheet getTimeSheetById(Long id) {
        Optional<TimeSheet> TimeSheet = TimeSheetRepository.findById(id);
        return TimeSheet.orElse(null); // Return null if not found, or handle with custom exception
    }

    // Create a new TimeSheet
    public TimeSheet createTimeSheet(TimeSheet TimeSheet) {
        return TimeSheetRepository.save(TimeSheet);
    }

    // Update an existing TimeSheet
    public TimeSheet updateTimeSheet(Long id, TimeSheet updatedTimeSheet) {
        Optional<TimeSheet> existingTimeSheet = TimeSheetRepository.findById(id);
        if (existingTimeSheet.isPresent()) {
            TimeSheet TimeSheet = existingTimeSheet.get();
            // Update fields
            TimeSheet.setHeuresTravaillees(updatedTimeSheet.getHeuresTravaillees());
            TimeSheet.setDate(updatedTimeSheet.getDate());

            // Save the updated entity
            return TimeSheetRepository.save(TimeSheet);
        }
        return null;
    }

    // Delete a TimeSheet by ID
    public boolean deleteTimeSheet(Long id) {
        Optional<TimeSheet> TimeSheet = TimeSheetRepository.findById(id);
        if (TimeSheet.isPresent()) {
            TimeSheetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
