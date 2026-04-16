package com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos;

import jakarta.persistence.Embeddable;

@Embeddable
public class PhysicalDataJpaDTO {
    private double weight;
    private double width;
    private double height;
    private double depth;
    private boolean isFragile;

    protected PhysicalDataJpaDTO() {}

    public PhysicalDataJpaDTO(double weight, double width, double height, double depth, boolean isFragile) {
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.isFragile = isFragile;
    }

    public double getWeight() { return weight; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }
    public boolean getIsFragile() { return isFragile; }
}