package com.example.softlearning.core.entity.order.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderDetailTest {

    @Test
    @DisplayName("Los setters con valores válidos devuelven 0 y asignan correctamente")
    void setters_ValidParameters_ReturnsZeroAndAssigns() {
        OrderDetail detail = new OrderDetail();

        assertEquals(0, detail.setRef("REF-001"));
        assertEquals("REF-001", detail.getRef());

        assertEquals(0, detail.setAmount(5));
        assertEquals(5, detail.getAmount());

        assertEquals(0, detail.setAmount(0));
        assertEquals(0, detail.getAmount());

        assertEquals(0, detail.setPrice(15.99));
        assertEquals(15.99, detail.getPrice());

        assertEquals(0, detail.setPrice(0.0));
        assertEquals(0.0, detail.getPrice());

        assertEquals(0, detail.setDiscount(0.15));
        assertEquals(0.15, detail.getDiscount());

        assertEquals(0, detail.setDiscount(0.0));
        assertEquals(0.0, detail.getDiscount());

        assertEquals(0, detail.setDiscount(1.0));
        assertEquals(1.0, detail.getDiscount());
    }

    @Test
    @DisplayName("Los setters con valores inválidos devuelven -1 y no asignan")
    void setters_InvalidParameters_ReturnsMinusOneAndDoesNotAssign() {
        OrderDetail detail = new OrderDetail();

        assertEquals(-1, detail.setAmount(-1));
        assertEquals(0, detail.getAmount());

        assertEquals(-1, detail.setPrice(-0.01));
        assertEquals(0.0, detail.getPrice());

        assertEquals(-1, detail.setDiscount(-0.1));
        assertEquals(0.0, detail.getDiscount());

        assertEquals(-1, detail.setDiscount(1.01));
        assertEquals(0.0, detail.getDiscount());
    }

    @Test
    @DisplayName("OrderDetailDataValidation devuelve string vacío si todos los datos son válidos")
    void orderDetailDataValidation_AllValid_ReturnsEmptyString() {
        OrderDetail detail = new OrderDetail();
        
        String validation = detail.OrderDetailDataValidation("REF-001", 2, 20.0, 0.1);
        
        assertEquals("", validation);
    }

    @Test
    @DisplayName("OrderDetailDataValidation concatena todos los errores si todo es inválido")
    void orderDetailDataValidation_AllInvalid_ConcatenatesErrors() {
        OrderDetail detail = new OrderDetail();
        
        String validation = detail.OrderDetailDataValidation("REF", -5, -10.0, 1.5);
        
        assertTrue(validation.contains("Bad amount;"));
        assertTrue(validation.contains("Bad price;"));
        assertTrue(validation.contains("Bad discount;"));
    }

    @Test
    @DisplayName("getDetailCost calcula correctamente el coste sin descuento")
    void getDetailCost_NoDiscount_CalculatesCorrectly() {
        OrderDetail detail = new OrderDetail();
        detail.setAmount(3);
        detail.setPrice(10.0);
        detail.setDiscount(0.0); 

        assertEquals(30.0, detail.getDetailCost());
    }

    @Test
    @DisplayName("getDetailCost calcula correctamente el coste aplicando descuento parcial")
    void getDetailCost_WithDiscount_CalculatesCorrectly() {
        OrderDetail detail = new OrderDetail();
        detail.setAmount(2);
        detail.setPrice(50.0);
        detail.setDiscount(0.10); 

        assertEquals(90.0, detail.getDetailCost());
    }

    @Test
    @DisplayName("getDetailCost calcula correctamente el coste con descuento total (100%)")
    void getDetailCost_FullDiscount_CalculatesCorrectly() {
        OrderDetail detail = new OrderDetail();
        detail.setAmount(5);
        detail.setPrice(100.0);
        detail.setDiscount(1.0); 

        assertEquals(0.0, detail.getDetailCost());
    }
}