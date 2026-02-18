package com.example.softlearning.core.entity.order.model;

public enum OrderStatus {
    CREATED,        // Solo se ha creado el order, pendiente de confirmar
    CANCELLED,      // Order cancelada
    CONFIRMED,      // Carrito validado y pagado, cuando hay una fecha de pago establecida
    FORTHCOMMING,   // Cuando se ponen las dimensiones, se da por hecho que se ha preparado el paquete y está almacenado pendiente de envío
    DELIVERED,      // En este momento el pedido esta entregado al transportista con destino al cliente, no se debe de poder cancelar. Cuando se informa de la fecha de entrega 
    FINISHED        // Envio entregado al cliente
}