package com.angMetal.payment.repository;

import com.angMetal.payment.entity.TransactionElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;


public interface TransactionElasticsearchRepository extends ElasticsearchRepository<TransactionElastic, Long> {
    public Optional<TransactionElastic> findById(Long transactionID);

}
