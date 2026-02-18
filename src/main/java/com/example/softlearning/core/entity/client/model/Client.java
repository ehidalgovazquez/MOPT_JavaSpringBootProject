package com.example.softlearning.core.entity.client.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.stakeholders.Person;
import com.example.softlearning.core.entity.sharedkernel.model.stakeholders.Stakeholder;

public class Client extends Person implements Stakeholder {
    protected int idClient;
    protected LocalDate registrationDate;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Client() {}

    // Factory method - Método factoría
    public static Client getInstance(String idPerson, String email, String phone, String address, String namePerson, int idClient, String registrationDate) throws BuildException {
        Client c = new Client();
        String message = c.ClientDataValidation(idPerson, email, phone, address, namePerson, idClient, registrationDate);
        if(!message.isEmpty()){
            throw new BuildException (message);
        }
        return c;
    }

    protected String ClientDataValidation(String idPerson, String email, String phone, String address, String namePerson, int idClient, String registrationDate) {
        String errorMessage = PersonDataValidation(idPerson, email, phone, address, namePerson);
        if(setidClient(idClient) == false){
            errorMessage += "Bad idClient;";
        }
        try{
            setRegistrationDate(registrationDate);
        } catch (DateTimeException e) {
            errorMessage += "Bad Date: " + e.getMessage() + ";";
        }
        return errorMessage;
    }

    public int getIdClient() {
        return idClient;
    }

    public boolean setidClient(int idClient) {
        if(Check.minValue(idClient, 1000)) {
            this.idClient = idClient;
            return true;
        }
        return false;
    }

    public String getRegistrationDate() {
        return this.registrationDate.format(formatter);
    }

    public void setRegistrationDate(String registrationDate) throws DateTimeException {
        try{
            this.registrationDate = LocalDate.parse(registrationDate,formatter);
        } catch (DateTimeException e) {
            throw e;
        }
    }

    public int yearsOld(){
        return LocalDate.now().getYear() - this.registrationDate.getYear();
    }

    // En producción no es recomendable mostrar toda la información de un objeto
    @Override
    public String toString() {
        return "\nClient: \n getIdPerson = " + this.getIdPerson() + "\n idClient = " + this.getIdClient() + "\n Email = "
                + this.getEmail() + "\n RegistrationDate = " + this.getRegistrationDate() + "\n Phone = " + this.getPhone()
                + "\n Address = " + this.getAddress() + "\n NamePerson = " + this.getNamePerson() + "\n";
    }

    // Sobreescritura del método getContactData de la clase Person
    public String getContactData(){
        return super.getPersonData() + "\nidClient = " + this.getIdClient();
    }

    @Override
    public String ident() {
        return String.valueOf(this.getIdClient());
    }

    @Override
    public String name() {
        return this.getNamePerson();
    }

    @Override
    public String contactData() {
        return this.getContactData();
    }

}
