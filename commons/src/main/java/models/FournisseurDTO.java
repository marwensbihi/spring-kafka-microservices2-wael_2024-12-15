package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FournisseurDTO {

    @JsonProperty("fournisseur_id")
    private Long fournisseurID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("adresse")
    private String adresse;

    @JsonProperty("email")
    private String email;

    @JsonProperty("numero_tel")
    private String numeroTel;
}
