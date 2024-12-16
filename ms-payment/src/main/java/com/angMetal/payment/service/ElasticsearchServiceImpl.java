package com.angMetal.payment.service;

import com.angMetal.payment.entity.TransactionElastic;
import com.angMetal.payment.entity.TransactionMySQL;
import com.angMetal.payment.repository.TransactionElasticsearchRepository;
import com.angMetal.payment.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ElasticsearchServiceImpl {

    private final TransactionRepository transactionRepository;
    private final TransactionElasticsearchRepository elasticsearchRepository;

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public ElasticsearchServiceImpl(TransactionRepository transactionRepository,
                                    TransactionElasticsearchRepository elasticsearchRepository,
                                    RestHighLevelClient restHighLevelClient) {
        this.transactionRepository = transactionRepository;
        this.elasticsearchRepository = elasticsearchRepository;
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * Save the transaction to both MySQL and Elasticsearch.
     *
     * @param transactionMySQL the transaction entity to be saved in MySQL
     * @return the saved transaction entity in MySQL
     */
    @Transactional  // Ensures MySQL transaction is rolled back if any exception occurs
    public TransactionMySQL saveTransaction(TransactionMySQL transactionMySQL) {
        // Save to MySQL
        TransactionMySQL savedTransaction = transactionRepository.save(transactionMySQL);

        // Map MySQL entity to Elasticsearch entity
        TransactionElastic transactionElastic = mapToElastic(savedTransaction);

        // Save to Elasticsearch
        insertTransactionElastic(transactionElastic);

        return savedTransaction;
    }

    public void insertTransactionElastic(TransactionElastic transactionElastic) {
        try {
            // Convert TransactionElastic to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(transactionElastic);
            log.info("Inserting into elastic index transaction_index new transaction: {}",jsonString);
            // Create IndexRequest with index name and document ID (optional)
            IndexRequest indexRequest = new IndexRequest("transaction_index")
                    .id(transactionElastic.getTransactionID().toString())
                    .source(jsonString, XContentType.JSON);

            // Insert into Elasticsearch
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Map a TransactionMySQL entity to a TransactionElastic entity.
     *
     * @param transactionMySQL the MySQL transaction entity
     * @return the corresponding Elasticsearch entity
     */
    private TransactionElastic mapToElastic(TransactionMySQL transactionMySQL) {
        TransactionElastic transactionElastic = new TransactionElastic();

        transactionElastic.setTransactionID(transactionMySQL.getTransactionID());
        transactionElastic.setMontant(transactionMySQL.getMontant());
        transactionElastic.setDateTransaction(transactionMySQL.getDateTransaction());
        transactionElastic.setTypeDeTransaction(transactionMySQL.getTypeDeTransaction());
        transactionElastic.setFactureVenteId(transactionMySQL.getFactureVenteId());
        transactionElastic.setFactureAchatId(transactionMySQL.getFactureAchatId());

        return transactionElastic;
    }

    /**
     * Index the transaction to Elasticsearch.
     *
     * @param transaction the transaction entity to be indexed
     */
    public void indexTransaction(TransactionElastic transaction) {
        elasticsearchRepository.save(transaction);
    }

}
