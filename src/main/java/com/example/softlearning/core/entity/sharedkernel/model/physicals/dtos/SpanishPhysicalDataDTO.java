package com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpanishPhysicalDataDTO {

    @JsonProperty("peso")
    public double weight;

    @JsonProperty("ancho")
    public double width;

    @JsonProperty("alto")
    public double height;

    @JsonProperty("profundida")
    public double depth;

    @JsonProperty("esFragil")
    public boolean isFragile;
    
    protected SpanishPhysicalDataDTO() {

    }

    public SpanishPhysicalDataDTO(
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
