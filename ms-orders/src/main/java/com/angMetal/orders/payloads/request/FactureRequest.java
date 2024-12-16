package com.angMetal.orders.payloads.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactureRequest {

    @JsonProperty("banqueId")
    @NotNull(message = "Banque ID is required.")
    private Long banqueId;

    @JsonProperty("source")
    @NotNull(message = "Source is required.")
    private String source;

    @JsonProperty("montantTotal")
    @NotNull(message = "Montant total is required.")
    private Double montantTotal;

    @JsonProperty("dateEmission")
    @NotNull(message = "Date emission is required.")
    private Date dateEmission;

    @JsonProperty("dateEcheance")
    @NotNull(message = "Date echeance is required.")
    private Date dateEcheance;

    @JsonProperty("fournisseurId")
    private Long fournisseurId;

    @JsonProperty("clientId")
    private Long clientId;

    @JsonProperty("products")
    @NotNull(message = "Products with quantities are required.")
    private List<ProductWithQuantity> products;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductWithQuantity {
        @JsonProperty("productId")
        private Long productId;

        @JsonProperty("quantity")
        private Integer quantity;
    }

    public List<Long> getProductIds() {
        return this.products.stream()
                .map(ProductWithQuantity::getProductId)  // Extract the productId from each ProductWithQuantity
                .collect(Collectors.toList());  // Collect them into a list
    }
}