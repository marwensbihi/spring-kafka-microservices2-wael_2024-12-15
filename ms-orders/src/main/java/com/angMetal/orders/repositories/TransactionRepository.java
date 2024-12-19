package com.angMetal.orders.repositories;

import models.TransactionMySQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionMySQL,Long> {

}