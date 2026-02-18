package com.example.softlearning.core.entity.order.dtos;

import java.util.ArrayList;

import com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos.PhysicalDataDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Order")
public class OrderDTO {
    @JsonProperty("ref")
    private String ref;
    
    @JsonProperty("idClient")
    @JacksonXmlProperty(localName = "id_client")
    private int idClient;
    
    @JsonProperty("startDate")
    @JacksonXmlProperty(localName = "start_date")
    private String startDate;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("address")
    private String address;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("phone")
    private String phone;
    
    @JacksonXmlElementWrapper(localName = "shopcart_details")
    @JacksonXmlProperty(localName = "order_detail")
    @JsonProperty("shopcartDetails")
    private ArrayList<OrderDetailDTO> shopcartDetails;
    
    @JsonProperty("paymentDate")
    @JacksonXmlProperty(localName = "payment_date")
    private String paymentDate;

    @JsonProperty("physicalData")
    @JacksonXmlProperty(localName = "physical_data")

    // @JsonAlias permite mapear otro nombre alternativo durante la deserialización.
    @JsonAlias("physicalData")
    private PhysicalDataDTO packageInfo;
    
    @JsonProperty("deliveryDate")
    @JacksonXmlProperty(localName = "delivery_date")
    private String deliveryDate;
    
    @JsonProperty("finishDate")
    @JacksonXmlProperty(localName = "finish_date")
    private String finishDate;

    protected OrderDTO() {

    }

    public OrderDTO(String ref, int idClient, String startDate, String description, String address, String name, String phone, ArrayList<OrderDetailDTO> shopcartDetails, String paymentDate, PhysicalDataDTO packageInfo, String deliveryDate, String finishDate
    ) {
        this.ref = ref;
        this.idClient = idClient;
        this.startDate = startDate;
        this.description = description;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.shopcartDetails = shopcartDetails;
        this.paymentDate = paymentDate;
        this.packageInfo = packageInfo;
        this.deliveryDate = deliveryDate;
        this.finishDate = finishDate;    
    }

    public int getIdClient() {
        return idClient;
    }

    public String getRef() {
        return ref;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<OrderDetailDTO> getShopcartDetails() {
        return shopcartDetails;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public PhysicalDataDTO getPhysicalData() {
        return packageInfo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    @Override
    public String toString() {
        return "OrderDTO [" +
            "ref=" + ref +
            ", idClient=" + idClient +
            ", startDate=" + startDate +
            ", description=" + description +
            ", address=" + address +
            ", name=" + name +
            ", phone=" + phone +
            ", shopcartDetails=" + shopcartDetails +
            ", paymentDate=" + paymentDate +
            ", packageInfo=" + packageInfo +
            ", deliveryDate=" + deliveryDate +
            ", finishDate=" + finishDate +
            "]";
    }
}