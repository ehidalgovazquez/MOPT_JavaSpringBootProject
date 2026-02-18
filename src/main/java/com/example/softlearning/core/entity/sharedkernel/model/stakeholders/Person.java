package com.example.softlearning.core.entity.sharedkernel.model.stakeholders;


import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;

 public abstract class Person {
    protected String idPerson, email, phone, address, namePerson;

    protected Person(){}

    protected String PersonDataValidation(String idPerson, String email, String phone, String address, String namePerson) {
        String errorMessage = "";
        if(setIdPerson(idPerson) != 0){
            errorMessage += "Bad idPerson;";
        }
        if(setEmail(email) != 0){
            errorMessage += "Bad Email;";
        }
        if(setPhone(phone) != 0){
            errorMessage += "Bad Phone;";
        }
        if(setAddress(address) != 0){
            errorMessage += "Bad Address;";
        }
        if(setNamePerson(namePerson) != 0){
            errorMessage += "Bad NamePerson;";
        }
        return errorMessage;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public int setIdPerson(String idPerson) {
        if(Check.minStringChars(idPerson, 2)){
            this.idPerson = idPerson;
            return 0;
        }
        return -1;
    }

    public String getEmail() {
        return email;
    }

    public int setEmail(String email) {
        if(Check.minStringChars(email, 12)){
            this.email = email;
            return 0;
        }
        return -1;
    }

    public String getPhone() {
        return phone;
    }

    public int setPhone(String phone) {
        if(Check.minStringChars(phone, 9)){
            this.phone = phone;
            return 0;
        }
        return -1;
    }

    public String getAddress() {
        return address;
    }

    public int setAddress(String address) {
        if(Check.minStringChars(address, 10)){
            this.address = address;
            return 0;
        }
        return -1;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public int setNamePerson(String namePerson) {
        if(Check.minStringChars(namePerson, 2)){
            this.namePerson = namePerson;
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "\nPerson: \n IdPerson = " + getIdPerson() + "\n Email = " + getEmail() + "\n Phone = " + getPhone() + "\n Address = " + getAddress() + "\n NamePerson = " + getNamePerson() + "\n";
    }

    public String getPersonData(){
        return "\nIdPerson = " + getIdPerson() + "\nEmail = " + getEmail() + "\nPhone = " + getPhone();
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{");
        sb.append("idPerson=").append(idPerson);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append(", namePerson=").append(namePerson);
        sb.append('}');
        return sb.toString();
    }*/
    
}