package com.angMetal.orders.controller;

import com.angMetal.orders.entity.Timesheet;
import com.angMetal.orders.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {


    @Autowired
    private TimesheetService timesheetService;

    @GetMapping
    public List<Timesheet> getAllTimesheets() {
        return timesheetService.getAllTimesheets();
    }

    @GetMapping("/{id}")
    public Optional<Timesheet> getTimesheetById(@PathVariable Long id) {
        return timesheetService.getTimesheetById(id);
    }

    @PostMapping
    public Timesheet createTimesheet(@RequestBody Timesheet timesheet) {
        return timesheetService.createTimesheet(timesheet);
    }

    @PutMapping("/{id}")
    public Timesheet updateTimesheet(@PathVariable Long id, @RequestBody Timesheet timesheet) {
        return timesheetService.updateTimesheet(id, timesheet);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTimesheet(@PathVariable Long id) {
        return timesheetService.deleteTimesheet(id);
    }
}