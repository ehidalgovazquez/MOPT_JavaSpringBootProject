package com.example.softlearning.core.entity.order.dtos;

import java.util.ArrayList;

import com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos.SpanishPhysicalDataDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SpanishOrderDTO {

    @JsonProperty("referencia")
    private String ref;

    @JsonProperty("idCliente")
    @JacksonXmlProperty(localName = "id_cliente")
    private int idClient;

    @JsonProperty("fechaInicio")
    @JacksonXmlProperty(localName = "fecha_inicio")
    private String startDate;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("direccion")
    private String address;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("telefono")
    private String phone;

    @JsonProperty("detallesCarrito")
    @JacksonXmlElementWrapper(localName = "detalles_carrito")
    @JacksonXmlProperty(localName = "detalle")
    private ArrayList<SpanishOrderDetailDTO> shopcartDetails;

    @JsonProperty("fechaPago")
    @JacksonXmlProperty(localName = "fecha_pago")
    private String paymentDate;

    @JsonProperty("informacionPaquete")
    @JacksonXmlProperty(localName = "informacion_paquete")
    @JsonAlias("physicalData")
    private SpanishPhysicalDataDTO packageInfo;

    @JsonProperty("fechaEntrega")
    @JacksonXmlProperty(localName = "fecha_entrega")
    private String deliveryDate;

    @JsonProperty("fechaFinal")
    @JacksonXmlProperty(localName = "fecha_final")
    private String finishDate;

    protected SpanishOrderDTO() {
        
    }

    public SpanishOrderDTO(
        String ref,
        int idClient,
        String startDate,
        String description,
        String address,
        String name,
        String phone,
        ArrayList<SpanishOrderDetailDTO> shopcartDetails,
        String paymentDate,
        SpanishPhysicalDataDTO packageInfo,
        String deliveryDate,
        String finishDate
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

    public ArrayList<SpanishOrderDetailDTO> getShopcartDetails() {
        return shopcartDetails;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public SpanishPhysicalDataDTO getPhysicalData() {
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
        return "SpanishOrderDTO [" +
            "ref=" + ref +
            ", idCliente=" + idClient +
            ", fechaInicio=" + startDate +
            ", descripcion=" + description +
            ", direccion=" + address +
            ", nombre=" + name +
            ", telefono=" + phone +
            ", detallesCarritoCompra=" + shopcartDetails +
            ", fechaPago=" + paymentDate +
            ", informacionPaquete=" + packageInfo +
            ", fechaEntrega=" + deliveryDate +
            ", fechaFinal=" + finishDate +
            "]";
    }
}