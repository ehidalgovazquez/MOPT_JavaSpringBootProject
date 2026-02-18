package com.example.softlearning.core.entity.order.model;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;

public class OrderDetail {
    protected String ref;
    protected int amount;
    protected double discount, price;

    public OrderDetail(){}

    public String OrderDetailDataValidation(String ref, int amount, double price, double discount) {
        String errorMessage = "";
        if (setRef(ref) != 0) {
            errorMessage += "Bad ref;";
        }

        if (setAmount(amount) != 0) {
            errorMessage += "Bad amount;";
        }

        if (setPrice(price) != 0) {
            errorMessage += "Bad price;";
        }

        if (setDiscount(discount) != 0) {
            errorMessage += "Bad discount;";
        }

        return errorMessage;
    }

    public String getRef() {
        return ref;
    }

    public int setRef(String ref) {
        if(Check.minStringChars(ref, 0)){
            this.ref = ref;
            return 0;
        }
        return -1;
    }

    public int getAmount() {
        return amount;
    }

    public int setAmount(int amount) {
        if(Check.minValue(amount, 0)){
            this.amount = amount;
            return 0;
        }
        return -1;
    }

    public double getPrice() {
        return price;
    }

    public int setPrice(double price) {
        if(Check.minValue(price, 0)){
            this.price = price;
            return 0;
        }
        return -1;
    }

    public double getDiscount() {
        return discount;
    }

    public int setDiscount(double discount) {
        if(Check.isValidValue(discount, 0, 1)){
            this.discount = discount;
            return 0;
        }
        return -1;
    }

    public double getDetailCost() {
        return this.getAmount() * this.getPrice() * (1 - this.getDiscount());
    }
}