package com.example.softlearning.core.entity.book.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookDTO {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "publication_date")
    private String publicationDate;

    @Column(name = "availability_date")
    private String availabilityDate;

    @Column(name = "price")
    private double price;

    @Column(name = "weight")
    private double weight;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;

    @Column(name = "depth")
    private double depth;

    @Column(name = "available")
    private boolean available;

    @Column(name = "is_fragile")
    private boolean isFragile;

    public BookDTO() {
    }

    @JsonCreator
    public BookDTO(
        //@JsonProperty se usa para mapear los nombres, los getters y los setters de las propiedades JSON/XML
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("price") double price,
        @JsonProperty("available") boolean available,
        @JsonProperty("author") String author,
        @JsonProperty("publicationDate") String publicationDate,
        @JsonProperty("availabilityDate") String availabilityDate,
        @JsonProperty("weight") double weight,
        @JsonProperty("width") double width,
        @JsonProperty("height") double height,
        @JsonProperty("depth") double depth,
        @JsonProperty("isFragile") boolean isFragile
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
        this.author = author;
        this.publicationDate = publicationDate;
        this.availabilityDate = availabilityDate;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.isFragile = isFragile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable(){
        return available;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getAvailabilityDate() {
        return availabilityDate;
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

    @Override
    public String toString() {
        return "BookDTO [" +
            "id=" + id +
            ", name=" + name +
            ", author=" + author +
            ", publicationDate=" + publicationDate +
            ", availabilityDate=" + availabilityDate +
            ", price=" + price +
            ", weight=" + weight +
            ", width=" + width +
            ", height=" + height +
            ", depth=" + depth +
            ", available=" + available +
            ", isFragile=" + isFragile +
            "]";
    }

}