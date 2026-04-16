package com.example.softlearning.core.entity.order.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Order")
public class OrderDTO {
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("idClient")
    private int idClient;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("address")
    private String address;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("shopcartDetails")
    @JacksonXmlElementWrapper(localName = "shopcart_details")
    @JacksonXmlProperty(localName = "order_detail")
    private List<OrderDetailDTO> shopcartDetails;
    
    @JsonProperty("paymentDate")
    private String paymentDate;
    @JsonProperty("physicalData")
    private String physicalData;
    @JsonProperty("deliveryDate")
    private String deliveryDate;
    @JsonProperty("finishDate")
    private String finishDate;

    public OrderDTO() {}

    public OrderDTO(String ref, int idClient, String startDate, String description, String address, String name, String phone, List<OrderDetailDTO> shopcartDetails, String paymentDate, String physicalData, String deliveryDate, String finishDate) {
        this.ref = ref;
        this.idClient = idClient;
        this.startDate = startDate;
        this.description = description;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.shopcartDetails = shopcartDetails;
        this.paymentDate = paymentDate;
        this.physicalData = physicalData;
        this.deliveryDate = deliveryDate;
        this.finishDate = finishDate;
    }

    public String getRef() { return ref; }
    public int getIdClient() { return idClient; }
    public String getStartDate() { return startDate; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public List<OrderDetailDTO> getShopcartDetails() { return shopcartDetails; }
    public String getPaymentDate() { return paymentDate; }
    public String getPhysicalData() { return physicalData; }
    public String getDeliveryDate() { return deliveryDate; }
    public String getFinishDate() { return finishDate; }
}