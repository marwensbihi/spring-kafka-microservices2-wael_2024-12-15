package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.FactureAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureAchatRepository extends JpaRepository<FactureAchat, Long> {
}