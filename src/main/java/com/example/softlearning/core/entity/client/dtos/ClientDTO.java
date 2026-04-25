package com.example.softlearning.core.entity.client.dtos;

import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientDTO {

    @Id
    @Column(name = "id_client")
    private int idClient;
    
    @Column(name = "id_person")
    private String idPerson;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "name_person")
    private String namePerson;
    
    @Column(name = "registration_date")
    private String registrationDate;
    
    // Para evitar la recursividad infinita al serializar el cliente con sus pedidos, se ignora la propiedad orders
    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderJpaDTO> orders = new ArrayList<>();

    public ClientDTO() {
    }

    @JsonCreator
    public ClientDTO(
        //@JsonProperty se usa para mapear los nombres, los getters y los setters de las propiedades JSON/XML
        @JsonProperty("idClient") int idClient, 
        @JsonProperty("idPerson") String idPerson, 
        @JsonProperty("email") String email, 
        @JsonProperty("phone") String phone, 
        @JsonProperty("address") String address, 
        @JsonProperty("namePerson") String namePerson, 
        @JsonProperty("registrationDate") String registrationDate
    ) {
        this.idClient = idClient;
        this.idPerson = idPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.namePerson = namePerson;
        this.registrationDate = registrationDate;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "ClientDTO [" +
            "idClient=" + idClient +
            ", idPerson=" + idPerson +
            ", email=" + email +
            ", phone=" + phone +
            ", address=" + address +
            ", namePerson=" + namePerson +
            ", registrationDate=" + registrationDate +
            "]";
    }
}