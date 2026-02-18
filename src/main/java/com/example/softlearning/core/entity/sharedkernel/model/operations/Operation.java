package com.example.softlearning.core.entity.sharedkernel.model.operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.GeneralDateTimeException;
import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;

public class Operation {
    protected String ref;
    protected String description;
    protected LocalDateTime startDate, finishDate;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss");

    protected Operation() {}

    protected String OperationDataValidation(String ref, String description, String startDate) {
        String errorMessage = "";

        if(setRef(ref) != 0){
            errorMessage += "Bad ref;";
        }
        
        setDescription(description);

        try {
            setStartDate(startDate);
        } catch (GeneralDateTimeException ex) {
            errorMessage += ex.getMessage();
        }

        if(!errorMessage.isEmpty()){
            return errorMessage;
        }
        return "";
    }

    public int setRef(String ref) {
        if(Check.minStringChars(ref, 0)){
            this.ref = ref;
            return 0;
        }
        return -1;
    }

    public String getRef() {
        return ref;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartDate(String startDate) throws GeneralDateTimeException {
        try{
            this.startDate = LocalDateTime.parse(startDate,formatter);
        } catch (GeneralDateTimeException e) {
            throw e;
        }
    }

    public String getStartDate() {
        if(this.startDate == null){
            return null;
        }
        return this.startDate.format(formatter);
    }

    public void setFinishDate(String finishDate) throws GeneralDateTimeException {
        try{
            this.finishDate = LocalDateTime.parse(finishDate,formatter);
        } catch (GeneralDateTimeException e) {
            throw e;
        }
    }

    public String getFinishDate() {
        if(this.finishDate == null){
            return null;
        }
        return this.finishDate.format(formatter);
    }
}
