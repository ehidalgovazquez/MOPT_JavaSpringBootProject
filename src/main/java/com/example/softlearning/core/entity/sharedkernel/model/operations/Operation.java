package com.example.softlearning.core.entity.sharedkernel.model.operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.GeneralDateTimeException;

public class Operation {
    protected String ref;
    protected String description;
    protected LocalDateTime startDate, finishDate;
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        if (startDate == null || startDate.isBlank()) {
            throw new GeneralDateTimeException("Bad start date");
        }
        this.startDate = parseDateTime(startDate);
    }

    public String getStartDate() {
        if(this.startDate == null){
            return null;
        }
        return this.startDate.format(OUTPUT_FORMATTER);
    }

    public void setFinishDate(String finishDate) throws GeneralDateTimeException {
        if (finishDate == null || finishDate.isBlank()) {
            throw new GeneralDateTimeException("Bad finish date");
        }
        
        this.finishDate = parseDateTime(finishDate);
    }

    public String getFinishDate() {
        if(this.finishDate == null){
            return null;
        }
        return this.finishDate.format(OUTPUT_FORMATTER);
    }

    protected LocalDateTime parseDateTime(String value) throws GeneralDateTimeException {
        try {
            return LocalDateTime.parse(value, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new GeneralDateTimeException("Bad date: '" + value + "'. Expected format yyyy-MM-dd HH:mm:ss");
        }
    }
}
