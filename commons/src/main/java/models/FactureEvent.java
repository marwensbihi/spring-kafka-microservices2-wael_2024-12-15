package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.FactureType;
import enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactureEvent {
    @JsonProperty("factureId")
    public Long factureId;

    @JsonProperty("clientId")
    public Long clientId;

    @JsonProperty("fournisseurId")
    public Long fournisseurId;

    @JsonProperty("banqueId")
    public Long banqueId;

    @JsonProperty("date_Emission")
    public Date dateEmission;

    @JsonProperty("date_echeance")
    public Date dateEcheance;

    @JsonProperty("productIds")
    public List<ProductDTO> products;

    @JsonProperty("montant_total")
    public Double montantTotal;

    @JsonProperty("payment_type")
    public PaymentType paymentType;

    @JsonProperty("facture_type")
    public FactureType factureType;

}