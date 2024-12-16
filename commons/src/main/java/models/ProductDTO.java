package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "product")
public class ProductDTO {

    @JsonProperty("product_id")
    private Long productID;

    @JsonProperty("prix_unitaire")
    private Double prixUnitaire;

    @JsonProperty("quantite")
    private int quantity;

    @JsonProperty("taxe")
    private Double taxe;

    @JsonProperty("insert_date")
    private Date insertDate;
}
