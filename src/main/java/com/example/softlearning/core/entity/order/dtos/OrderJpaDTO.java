package com.example.softlearning.core.entity.order.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderJpaDTO {
    @Id
    private String ref;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", insertable = false, updatable = false)
    private ClientDTO client;
    
    @Column(name = "id_client")
    private int idClient;
    private String startDate;
    private String description;
    private String address;
    private String name;
    private String phone;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetailJpaDTO> shopcartDetails = new ArrayList<>();
    
    private LocalDateTime paymentDate;

    @Column(name = "physical_data", length = 500)
    private String physicalData;
    
    private LocalDateTime deliveryDate;
    private LocalDateTime finishDate;
    
    protected OrderJpaDTO() {}

    public OrderJpaDTO(String ref, int idClient, String startDate, String description, String address, String name, String phone, LocalDateTime paymentDate, String physicalData, LocalDateTime deliveryDate, LocalDateTime finishDate) {
        this.ref = ref;
        this.idClient = idClient;
        this.startDate = startDate;
        this.description = description;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.paymentDate = paymentDate;
        this.physicalData = physicalData;
        this.deliveryDate = deliveryDate;
        this.finishDate = finishDate;
    }

    public void addOrderDetail(OrderDetailJpaDTO detail) {
        this.shopcartDetails.add(detail);
        detail.setOrder(this);
    }

    public String getRef() { return ref; }
    public int getIdClient() { return idClient; }
    public ClientDTO getClient() { return client; }
    public void setClient(ClientDTO client) { this.client = client; }
    public String getStartDate() { return startDate; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public List<OrderDetailJpaDTO> getShopcartDetails() { return shopcartDetails; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPhysicalData() { return physicalData; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public LocalDateTime getFinishDate() { return finishDate; }
}