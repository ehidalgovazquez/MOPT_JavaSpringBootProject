package com.example.softlearning.core.entity.client.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpanishClientDTO {
    // privado para que nadie mas pueda acceder a el, final para que no pueda cambiar
    private final int idClient;
    private final String idPerson, email, phone, address, namePerson, registrationDate;

    @JsonCreator
    public SpanishClientDTO(
        //@JsonProperty se usa para mapear los nombres, los getters y los setters de las propiedades JSON/XML
        @JsonProperty("idCliente") int idClient, 
        @JsonProperty("idPersona") String idPerson, 
        @JsonProperty("mail") String email, 
        @JsonProperty("telefono") String phone, 
        @JsonProperty("direccion") String address, 
        @JsonProperty("nombrePersona") String namePerson, 
        @JsonProperty("fechaRegistro") String registrationDate
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
            "idCliente=" + idClient +
            ", idPersona=" + idPerson +
            ", mail=" + email +
            ", telefono=" + phone +
            ", direccion=" + address +
            ", nombrePersona=" + namePerson +
            ", fechaRegistro=" + registrationDate +
            "]";
    }

}