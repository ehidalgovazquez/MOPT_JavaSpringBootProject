package com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhysicalDataDTO {

    @JsonProperty("weight")
    public double weight;

    @JsonProperty("width")
    public double width;

    @JsonProperty("height")
    public double height;

    @JsonProperty("depth")
    public double depth;

    @JsonProperty("isFragile")
    public boolean isFragile;
    
    protected PhysicalDataDTO() {

    }

    public PhysicalDataDTO(
        double weight,
        double width,
        double height,
        double depth,
        boolean isFragile
    ) {
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.isFragile = isFragile;
    }

    public double getWeight() {
        return weight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
    }

    public boolean getIsFragile() {
        return isFragile;
    }
}
