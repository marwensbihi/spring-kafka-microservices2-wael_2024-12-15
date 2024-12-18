package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRepository extends JpaRepository<Timesheet,Long> {
}
