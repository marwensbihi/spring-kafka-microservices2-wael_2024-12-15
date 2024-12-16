package com.angMetal.payment.repository;

import com.angMetal.payment.entity.TransactionMySQL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionMySQL, Long> {
}
