package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.FactureVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FactureVenteRepository extends JpaRepository<FactureVente, Long> {

}