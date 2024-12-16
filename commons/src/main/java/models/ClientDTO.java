package models;

import enums.TypeClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "client")
public class ClientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("client_id") // Optional: Customize JSON property name if needed
    private Long clientID;

    @JsonProperty("name")  // Optional: Customize JSON property name
    private String name;

    @JsonProperty("adresse")  // Optional: Customize JSON property name
    private String adresse;

    @JsonProperty("email")  // Optional: Customize JSON property name
    private String email;

    @JsonProperty("numero_tel")  // Optional: Customize JSON property name
    private String numeroTel;

    @Enumerated(EnumType.STRING)  // Ensures the enum is stored as a string in the database
    @JsonProperty("type_client")  // Optional: Customize JSON property name
    private TypeClient typeClient;

    // Default constructor
    public ClientDTO() {
    }

    // Constructor with parameters
    public ClientDTO(Long clientID, String name, String adresse, String email, String numeroTel, TypeClient typeClient) {
        this.clientID = clientID;
        this.name = name;
        this.adresse = adresse;
        this.email = email;
        this.numeroTel = numeroTel;
        this.typeClient = typeClient;
    }

    // Getters and Setters
    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public TypeClient getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(TypeClient typeClient) {
        this.typeClient = typeClient;
    }
}
