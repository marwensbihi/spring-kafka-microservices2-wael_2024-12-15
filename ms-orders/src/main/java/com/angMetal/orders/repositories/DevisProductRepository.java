package com.angMetal.orders.repositories;


import com.angMetal.orders.entity.payloads.DevisProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevisProductRepository extends JpaRepository<DevisProduct, Long> {

}
