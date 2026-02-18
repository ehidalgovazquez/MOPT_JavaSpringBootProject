package com.example.softlearning.core.entity.sharedkernel.model.physicals;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

public interface Storable {
    public void setDimensions(double width, double height, double depth) throws BuildException;
    public String getDimensions();
    public String getPhysicalData();
    public double getWeight();
    public double getWidth();
    public double getHeight();
    public double getDepth();
    public boolean getIsFragile();
}
