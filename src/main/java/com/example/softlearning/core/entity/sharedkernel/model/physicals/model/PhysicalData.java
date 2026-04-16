package com.example.softlearning.core.entity.sharedkernel.model.physicals.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.example.softlearning.core.entity.sharedkernel.domainservices.validations.Check;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

public class PhysicalData {
    protected double weight, width, height, depth;
    protected boolean isFragile;

    public PhysicalData() {}

    public static PhysicalData getInstance(double weight, double width, double height, double depth, boolean isFragile) throws BuildException {
        PhysicalData p = new PhysicalData();
        String message = p.PhysicalDataValidation(weight, width, height, depth, isFragile);
        if(!message.isEmpty()){
            throw new BuildException (message);
        }
        return p;
    }

    protected String PhysicalDataValidation(double weight, double width, double height, double depth, boolean isFragile) {
        String errorMessage = "";
        try {
            setDimensions(width, height, depth);
        } catch (BuildException ex) {
            errorMessage += ex.getMessage();
        }
        if (setWeight(weight) != 0) {
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Weight";
        }
        setIsFragile(isFragile);
        return errorMessage;
    }

    public static PhysicalData getInstance(String packageinfo) throws BuildException {
        PhysicalData p = new PhysicalData();
        p.PhysicalDataValidation(packageinfo);
        return p;
    }

    protected void PhysicalDataValidation(String packageinfo) throws BuildException {
        String[] pairs = packageinfo.split(";");
        String[] expectedKeys = {"width", "height", "depth", "weight", "isFragile"};
        Set<String> missingKeys = new HashSet<>(Arrays.asList(expectedKeys));

        double vWidth = 0;
        double vHeight = 0;
        double vDepth = 0;
        double vWeight = 0;
        boolean visFragile = false;

        for (String pair : pairs) {
            pair = pair.trim();
            if(!pair.isEmpty()){
                if (!pair.contains(":")) {
                    throw new BuildException("Formato inválido, falta ':' en: " + pair);
                }

                String[] kv = pair.split(":", 2);
                String key = kv[0].trim();
                String value = kv[1].trim();

                boolean validKey = false;
                for (String expected : expectedKeys) {
                    if (expected.equals(key)) {
                        validKey = true;
                    }
                }

                if (!validKey) {
                    throw new BuildException("Clave desconocida: " + key);
                }

                missingKeys.remove(key);

                switch (key) {
                    case "width"   -> vWidth = Double.parseDouble(value);
                    case "height"  -> vHeight = Double.parseDouble(value);
                    case "depth"   -> vDepth = Double.parseDouble(value);
                    case "weight"  -> vWeight = Double.parseDouble(value);
                    case "isFragile" -> visFragile = Boolean.parseBoolean(value);
                }
            }
        }

        if (!missingKeys.isEmpty()) {
            throw new BuildException("Faltan claves: " + missingKeys);
        }

        String error = this.PhysicalDataValidation(vWeight, vWidth, vHeight, vDepth, visFragile);
        if (!error.isEmpty()) {
            throw new BuildException(error);
        }
    }

    public String getDimensions() {
        return "width:" + this.width + ",height:" + this.height + ",depth:" + this.depth;
    }

    public void setDimensions(double width, double height, double depth) throws BuildException {
        String errorMessage = "";
        if (setWidth(width) != 0) {
            errorMessage += "Bad Width";
        }
        if (setHeight(height) != 0) {
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Height";
        }
        if (setDepth(depth) != 0) {
            if(!errorMessage.isEmpty()) {
                errorMessage += "; ";
            }
            errorMessage += "Bad Depth";
        }
        throw new BuildException(errorMessage);
    }

    public double getWeight() {
        return weight;
    }

    public int setWeight(double weight) {
        if (Check.minValue(weight, 0)) {
            this.weight = weight;
            return 0;
        }
        return -1;
    }

    public double getWidth() {
        return width;
    }

    public int setWidth(double width) {
        if (Check.minValue(width, 0)) {
            this.width = width;
            return 0;
        }
        return -1;
    }

    public double getHeight() {
        return height;
    }

    public int setHeight(double height) {
        if (Check.minValue(height, 0)) {
            this.height = height;
            return 0;
        }
        return -1;
    }

    public double getDepth() {
        return depth;
    }

    public int setDepth(double depth) {
        if (Check.minValue(depth, 0)) {
            this.depth = depth;
            return 0;
        }
        return -1;
    }

    public boolean getIsFragile() {
        return isFragile;
    }

    public void setIsFragile(boolean isFragile) {
        this.isFragile = isFragile;
    }

    public String getPhysicalData() {
        return "weight:" + this.weight + ";height:" + this.height + ";width:" + this.width + ";depth:" + this.depth + ";isFragile:" + this.isFragile;
    }
}