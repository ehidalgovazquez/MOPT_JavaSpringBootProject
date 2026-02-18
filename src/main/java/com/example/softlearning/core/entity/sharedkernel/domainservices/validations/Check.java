package com.example.softlearning.core.entity.sharedkernel.domainservices.validations;

public class Check {
    public static boolean minStringChars (String str, double minChars){
        return checkString(str) && str.trim().length() >= minChars;
    }

    public static boolean maxStringChars (String str, double maxChars){
        return checkString(str) && str.trim().length() <= maxChars;
    }

    public static boolean checkString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidString(String str, double minChars, double maxChars) {
        return  minStringChars(str, minChars) && maxStringChars(str, maxChars);
    }
    
    public static boolean minValue (double value, double minValue){
        return value >= minValue;
    }

    public static boolean maxValue (double value, double maxValue){
        return  value <= maxValue;
    }

    public static boolean isValidValue(double value, double minValue, double maxValue) {
        return  minValue(value, minValue) && maxValue(value, maxValue);
    }

}
