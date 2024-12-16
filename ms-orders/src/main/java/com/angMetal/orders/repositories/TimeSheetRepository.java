package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepository extends JpaRepository<TimeSheet,Long> {
}
