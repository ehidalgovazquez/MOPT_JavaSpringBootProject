package com.example.softlearning.core.entity.book.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.physicals.Storable;
import com.example.softlearning.core.entity.sharedkernel.model.physicals.model.PhysicalData;
import com.example.softlearning.core.entity.sharedkernel.model.products.Product;

public class Book extends Product implements Storable {
    protected String author;
    protected LocalDate publicationDate, availabilityDate;
    protected PhysicalData dimensions;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Book() {}

    // Factory method - Método factoría
    public static Book getInstance(int id, String name, double price, boolean available, String author, String publicationDate, String availabilityDate, double weight, double width, double height, double depth, boolean isFragile) throws BuildException {
        Book b = new Book();
        String message = b.BookDataValidation(id, name, price, available, author, publicationDate, availabilityDate, weight, width, height, depth, isFragile);
        if(!message.isEmpty()){
            throw new BuildException (message);
        }
        return b;
    }

    protected String BookDataValidation(int id, String name, double price, boolean available, String author, String publicationDate, String availabilityDate, double weight, double width, double height, double depth, boolean isFragile) {
        String errorMessage = ProductDataValidation(id, name, price, available);
        if (setAuthor(author) != 0) {
            errorMessage += "Bad Author";
        }
        try{
            setPublicationDate(publicationDate);
        } catch (DateTimeException e) {
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Publication Date: " + e.getMessage();
        }
        try{
            setAvailabilityDate(availabilityDate);
        } catch (DateTimeException e) {
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Availability Date: " + e.getMessage();
        }
        try{
            setPhysicalData(weight, width, height, depth, isFragile);
        } catch (BuildException e) {
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad PhysicalData: " + e.getMessage();
        }
        return errorMessage;
    }

    public String getAuthor() {
        return author;
    }

    public int setAuthor(String author) {
        if (Check.minStringChars(author, 2)) {
            this.author = author;
            return 0;
        }
        return -1;
    }

    public String getPublicationDate() {
        return this.publicationDate.format(formatter);
    }

    public void setPublicationDate(String publicationDate) throws DateTimeException {
        try{
            this.publicationDate = LocalDate.parse(publicationDate,formatter);
        } catch (DateTimeException e) {
            throw e;
        }
    }

    public String getAvailabilityDate() {
        return this.availabilityDate.format(formatter);
    }

    public void setAvailabilityDate(String availabilityDate) throws DateTimeException {
        try{
            this.availabilityDate = LocalDate.parse(availabilityDate,formatter);
        } catch (DateTimeException e) {
            throw e;
        }
    }

    public void setPhysicalData(double weight, double width, double height, double depth, boolean isFragile) throws BuildException {
        try {
            this.dimensions = PhysicalData.getInstance(weight, width, height, depth, isFragile);
        } catch (BuildException e) {
            throw e;
        }
    }

    @Override
    public String getPhysicalData() {
        return this.dimensions.getPhysicalData();
    }

    @Override
    public String getDimensions() {
        return this.dimensions.getDimensions();
    }

    @Override
    public double getWeight() {
        return this.dimensions.getWeight();
    }

    @Override
    public double getWidth() {
        return this.dimensions.getWidth();
    }

    @Override
    public double getHeight() {
        return this.dimensions.getHeight();
    }

    @Override
    public double getDepth() {
        return this.dimensions.getDepth();
    }

    @Override
    public boolean getIsFragile() {
        return this.dimensions.getIsFragile();
    }

    @Override
    public void setDimensions(double width, double height, double depth) throws BuildException {
        try {
            this.dimensions.setDimensions(width, height, depth);
        } catch (BuildException e) {
            throw e;
        }
    }

    @Override
    public String toString() {
        return "\nBook: \n Author = " + getAuthor() + "\n Publication Date = " + getPublicationDate() + "\n Availability Date = " + getAvailabilityDate() + "\n Dimensions = " + dimensions.getDimensions();
    }

    public String getBookData(){
        return super.getProductData() + "\nAuthor = " + getAuthor() + "\nPublication Date = " + getPublicationDate() + "\nAvailability Date = " + getAvailabilityDate() + "\nDimensions = " + dimensions.getDimensions();
    }
}