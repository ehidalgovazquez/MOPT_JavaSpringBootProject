package com.example.softlearning.core.entity.book.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpanishBookDTO {
    private final int id;
    private final String name, author, publicationDate, availabilityDate;
    private final double price, weight, width, height, depth;
    private final boolean available, isFragile;

    @JsonCreator
    public SpanishBookDTO(
        // @JsonProperty se usa para mapear los nombres, los getters y los setters de las propiedades JSON/XML
        @JsonProperty("id") int id,
        @JsonProperty("nombre") String name,
        @JsonProperty("precio") double price,
        @JsonProperty("disponible") boolean available,
        @JsonProperty("autor") String author,
        @JsonProperty("fechaPublicacion") String publicationDate,
        @JsonProperty("fechaDisponible") String availabilityDate,
        @JsonProperty("peso") double weight,
        @JsonProperty("anchura") double width,
        @JsonProperty("altura") double height,
        @JsonProperty("profundida") double depth,
        @JsonProperty("esFragil") boolean isFragile
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
        return "SpanishBookDTO [" +
            "id=" + id +
            ", nombre=" + name +
            ", autor=" + author +
            ", fechaPublicacion=" + publicationDate +
            ", fechaDisponible=" + availabilityDate +
            ", precio=" + price +
            ", peso=" + weight +
            ", anchura=" + width +
            ", altura=" + height +
            ", profundidad=" + depth +
            ", disponible=" + available +
            ", esFragil=" + isFragile +
            "]";
    }

}