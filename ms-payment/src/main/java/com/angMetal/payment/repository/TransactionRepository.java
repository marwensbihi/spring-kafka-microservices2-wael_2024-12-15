package com.angMetal.payment.repository;

import models.TransactionMySQL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionMySQL, Long> {
}
