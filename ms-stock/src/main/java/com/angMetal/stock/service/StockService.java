package com.angMetal.stock.service;

import com.angMetal.stock.entity.Product;
import com.angMetal.stock.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.FactureType;
import lombok.extern.slf4j.Slf4j;
import models.ProductDTO;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import models.FactureEvent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final ProductRepository productRepository;

    private final RestHighLevelClient restHighLevelClient;


    public StockService(ProductRepository productRepository, RestHighLevelClient restHighLevelClient) {
        this.productRepository = productRepository;
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * Process the stock update based on the incoming FactureEvent
     * @param factureEvent The facture event with stock update details
     */
   public void processStockUpdate(FactureEvent factureEvent) {

       logger.info("Stock update received: Type: {}, ID: {}, ",
               factureEvent.getFactureType(), factureEvent.getFactureId());
        // Save the updated product to the database (make sure to update both Elasticsearch and database)
        try {
            updateProductStock(factureEvent);
        } catch (Exception e) {
            logger.error("Error saving product to database", e);
        }
    }

    @Transactional
    public void updateProductStock(FactureEvent factureEvent) {
        for (ProductDTO product : factureEvent.getProducts()) {
            // Select the product by its ID
            Optional<Product> productOptional = productRepository.findById(product.getProductID());
            if (productOptional.isPresent()) {
                Product existingProduct = productOptional.get();
                if (FactureType.VENTE.equals(factureEvent.getFactureType())) {
                    // Decrease stock for vente
                    existingProduct.setQuantiteEnStock(existingProduct.getQuantiteEnStock() - product.getQuantity());
                } else if (FactureType.ACHAT.equals(factureEvent.getFactureType())) {
                    // Increase stock for achat
                    existingProduct.setQuantiteEnStock(existingProduct.getQuantiteEnStock() + product.getQuantity());
                }
                product.setTaxe(existingProduct.getTaxe());
                product.setPrixUnitaire(existingProduct.getPrixUnitaire());
                product.setInsertDate(new Date());
                insertProductElastic(product);
                // Save the updated product
                productRepository.save(existingProduct);
            }
        }
    }

    /**
     * Save the product stock information to Elasticsearch
     * @param product The product to be saved in Elasticsearch
     */
    /*private void saveStockInElasticsearch(ProductDTO product) {
        try {
            elasticsearchRestTemplate.save(product);
        } catch (Exception e) {
            logger.error("Error saving product to Elasticsearch", e);
        }
    }*/

    public void insertProductElastic(ProductDTO product) {
        try {
            // Convert TransactionElastic to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(product);
            log.info("Inserting into elastic index product_index new products : {}",jsonString);
            // Create IndexRequest with index name and document ID (optional)
            IndexRequest indexRequest = new IndexRequest("product_index")
                    .source(jsonString, XContentType.JSON);

            // Insert into Elasticsearch
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
