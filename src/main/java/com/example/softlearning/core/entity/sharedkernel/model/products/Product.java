package com.example.softlearning.core.entity.sharedkernel.model.products;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;

public abstract class Product implements Marketable {
    protected int id;
    protected String name;
    protected double price;
    protected boolean available;

    protected Product(){}

    protected String ProductDataValidation(int id, String name, double price, boolean available) {
        String errorMessage = "";
        if(setId(id) != 0){
            errorMessage += "Bad Id";
        }
        if(setName(name) != 0){
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Name";
        }
        if(setPrice(price) != 0){
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Price";
        }
        if(setAvailable(available) != 0){
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Available";
        }
        return errorMessage;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        if(Check.minValue(id, 0)){
            this.id = id;
            return 0;
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public int setName(String name) {
        if(Check.minStringChars(name, 2)){
            this.name = name;
            return 0;
        }
        return -1;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int setPrice(double price) {
        if(Check.minValue(price, 0)){
            this.price = price;
            return 0;
        }
        return -1;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public int setAvailable(boolean available) {
        this.available = available;
        return 0;
    }
    

    @Override
    public String toString(){
        return "\nProduct: \n ID = " + getId() + "\n Name = " + getName() + "\n Price = " + getPrice();
    }

    public String getProductData(){
        return "\nID = " + getId() + "\nName = " + getName() + "\nPrice = " + getPrice();
    }
}