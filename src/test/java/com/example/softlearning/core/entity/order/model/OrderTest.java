package com.example.softlearning.core.entity.order.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.GeneralDateTimeException;

class OrderTest {

    @Test
    @DisplayName("Crea la instancia base correctamente en estado CREATED")
    void getInstance_BasicParams_ReturnsOrder() throws BuildException {
        Order order = Order.getInstance(
            "REF-100", 1, "2024-01-01 10:00:00", "Desc", "Carrer de Test 12", "Cliente Test", "123456789", "CREATED"
        );

        assertNotNull(order);
        assertEquals("CREATED", order.getStatus());
        assertEquals("REF-100", order.getRef());
        assertEquals(1, order.getIdClient());
        assertEquals("Carrer de Test 12", order.getAddress());
        assertEquals("Cliente Test", order.getName());
        assertEquals("123456789", order.getPhoneContact());
        assertEquals("2024-01-01 10:00:00", order.getStartDate());
    }

    @Test
    @DisplayName("Lanza BuildException acumulando errores de Operation y Order")
    void getInstance_InvalidParams_ThrowsAccumulatedException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Order.getInstance("", -1, "fecha-mala", "Desc", "Ab", "A", "123", "CREATED")
        );

        String msg = ex.getMessage();
        assertTrue(msg.contains("Bad ref"));
        assertTrue(msg.contains("Bad date"));
        assertTrue(msg.contains("Bad idClient"));
        assertTrue(msg.contains("Bad Address"));
        assertTrue(msg.contains("Bad Name"));
        assertTrue(msg.contains("Bad PhoneContact: 123"));
    }

    @Test
    @DisplayName("Crea la instancia avanzada ejecutando toda la máquina de estados secuencialmente")
    void getInstance_AdvancedParams_ReturnsFinishedOrder() throws BuildException {
        String shopCartStr = "ART1, 2, 10.5, 0.1; ART2, 1, 50.0, 0.0";
        String packageStr = "width:10.0; height:15.0; depth:20.0; weight:1.5; isFragile:true";
        
        Order order = Order.getInstance(
            "REF-200", 1, "2024-01-01 10:00:00", "Desc", "Carrer Test", "Cliente Test", "123456789",
            shopCartStr, "2024-01-02 10:00:00", packageStr, "2024-01-05 10:00:00", "2024-01-05 11:00:00", "IGNORED"
        );

        assertNotNull(order);
        assertEquals("FINISHED", order.getStatus());
        assertEquals("ART1, 2, 10.5, 0.1; ART2, 1, 50.0, 0.0", order.getShopcartDetails());
        assertEquals("2024-01-02 10:00:00", order.getPaymentDate());
        assertEquals("2024-01-05 10:00:00", order.getDeliveryDate());
        assertEquals("2024-01-05 11:00:00", order.getFinishDate());
        assertEquals(1.5, order.getWeight());
        assertTrue(order.getIsFragile());
    }

    @Test
    @DisplayName("setPhoneContact valida múltiples teléfonos y los devuelve formateados")
    void setPhoneContact_MultiplePhones_StoresProperly() throws BuildException {
        Order order = new Order();
        order.setPhoneContact("123456789; 987654321 ; 111222333");
        
        String result = order.getPhoneContact();
        assertTrue(result.contains("123456789"));
        assertTrue(result.contains("987654321"));
        assertTrue(result.contains("111222333"));
    }

    @Test
    @DisplayName("setPhoneContact lanza excepción con teléfonos inválidos o vacíos")
    void setPhoneContact_InvalidPhones_ThrowsException() {
        Order order = new Order();
        
        assertThrows(BuildException.class, () -> order.setPhoneContact(""));
        
        BuildException ex = assertThrows(BuildException.class, () -> order.setPhoneContact("123456789; 123"));
        assertTrue(ex.getMessage().contains("Bad PhoneContact: 123"));
    }

    @Test
    @DisplayName("setShopcartDetails parsea correctamente string válido y lo devuelve formateado")
    void setShopcartDetails_ValidString_ParsesAndFormats() throws BuildException {
        Order order = new Order();
        order.setShopcartDetails("REF-A, 5, 10.0, 0.1; REF-B, 1, 20.0, 0.0");
        
        String details = order.getShopcartDetails();
        assertTrue(details.contains("REF-A, 5, 10.0, 0.1"));
        assertTrue(details.contains("REF-B, 1, 20.0, 0.0"));
    }

    @Test
    @DisplayName("setShopcartDetails lanza excepción si falla la estructura o validación del DTO")
    void setShopcartDetails_InvalidString_ThrowsException() {
        Order order = new Order();
        
        BuildException ex1 = assertThrows(BuildException.class, () -> order.setShopcartDetails("REF-A, 5, 10.0"));
        assertTrue(ex1.getMessage().contains("se esperaban 4 campos"));

        BuildException ex2 = assertThrows(BuildException.class, () -> order.setShopcartDetails("REF-A, -5, 10.0, 0.1"));
        assertTrue(ex2.getMessage().contains("Bad amount"));
    }

    @Test
    @DisplayName("CRUD de ShopCart: Operaciones por referencia (REF)")
    void shopCart_CRUD_ByRef_WorksProperly() throws BuildException {
        Order order = new Order();
        
        order.createDetail("REF-A", 5, 10.0, 0.0);
        assertEquals("REF-A, 5, 10.0, 0.0", order.getShopcartDetails());

        OrderDetail read = order.readDetail("REF-A");
        assertNotNull(read);
        assertEquals(5, read.getAmount());
        assertNull(order.readDetail("REF-NO-EXISTE"));

        order.updateDetail("REF-A", 10);
        assertEquals(10, order.readDetail("REF-A").getAmount());

        assertThrows(BuildException.class, () -> order.updateDetail("REF-NO-EXISTE", 5));
        assertThrows(BuildException.class, () -> order.updateDetail("REF-A", -5));

        order.deleteDetail("REF-A");
        assertNull(order.readDetail("REF-A"));

        assertThrows(BuildException.class, () -> order.deleteDetail(""));
        assertThrows(BuildException.class, () -> order.deleteDetail("REF-NO-EXISTE"));
    }

    @Test
    @DisplayName("CRUD de ShopCart: Operaciones por posición en el array")
    void shopCart_CRUD_ByPos_WorksProperly() throws BuildException {
        Order order = new Order();
        order.createDetail("REF-A", 5, 10.0, 0.0);
        order.createDetail("REF-B", 1, 20.0, 0.0);

        order.updateDetail(1, 2);
        assertEquals(2, order.readDetail("REF-B").getAmount());

        assertThrows(BuildException.class, () -> order.updateDetail(5, 10));
        assertThrows(BuildException.class, () -> order.updateDetail(-1, 10));
        assertThrows(BuildException.class, () -> order.updateDetail(0, -5));

        order.deleteDetail(0);
        assertNull(order.readDetail("REF-A"));
        assertNotNull(order.readDetail("REF-B"));

        assertThrows(BuildException.class, () -> order.deleteDetail(5));
        assertThrows(BuildException.class, () -> order.deleteDetail(-1));
    }

    @Test
    @DisplayName("Máquina de estados: Falla si se intenta asignar datos fuera del orden lógico")
    void stateMachine_Violations_ThrowsExceptions() throws BuildException, GeneralDateTimeException {
        Order order = new Order();
        assertEquals("CREATED", order.getStatus());

        assertThrows(BuildException.class, () -> order.setPackageInfo("width:10.0;height:10.0;depth:10.0;weight:1.0;isFragile:false"));
        assertThrows(GeneralDateTimeException.class, () -> order.setDeliveryDate("2024-01-01 10:00:00"));
        assertThrows(GeneralDateTimeException.class, () -> order.setFinishDate("2024-01-01 10:00:00"));

        order.setPaymentDate("2024-01-01 10:00:00");
        assertEquals("CONFIRMED", order.getStatus());

        assertThrows(BuildException.class, () -> order.setAddress("Nueva Address"));
        assertThrows(BuildException.class, () -> order.setIdClient(2));
        assertThrows(BuildException.class, () -> order.setName("Nuevo Nombre"));
        assertThrows(BuildException.class, () -> order.setPhoneContact("987654321"));
        assertThrows(BuildException.class, () -> order.setShopcartDetails("REF,1,1,0"));
        assertThrows(BuildException.class, () -> order.createDetail("R1", 1, 1.0, 0.0));
        assertThrows(BuildException.class, () -> order.updateDetail(0, 5));
        assertThrows(BuildException.class, () -> order.updateDetail("R1", 5));
        assertThrows(BuildException.class, () -> order.deleteDetail(0));
        assertThrows(BuildException.class, () -> order.deleteDetail("R1"));
        assertThrows(GeneralDateTimeException.class, () -> order.setPaymentDate("2024-01-02 10:00:00"));

        order.setPackageInfo("width:10.0; height:15.0; depth:20.0; weight:1.5; isFragile:true");
        assertEquals("FORTHCOMMING", order.getStatus());
        assertThrows(BuildException.class, () -> order.setPackageInfo("width:10.0; height:15.0; depth:20.0; weight:1.5; isFragile:true"));

        order.setDeliveryDate("2024-01-03 10:00:00");
        assertEquals("DELIVERED", order.getStatus());
        assertThrows(GeneralDateTimeException.class, () -> order.setDeliveryDate("2024-01-04 10:00:00"));

        order.setFinishDate("2024-01-04 10:00:00");
        assertEquals("FINISHED", order.getStatus());
        assertThrows(GeneralDateTimeException.class, () -> order.setFinishDate("2024-01-05 10:00:00"));
    }

    @Test
    @DisplayName("Máquina de estados: Fechas nulas lanzan excepción controlada")
    void stateMachine_NullDates_ThrowsException() {
        Order order = new Order();
        
        assertThrows(GeneralDateTimeException.class, () -> order.setPaymentDate(null));
        
        assertDoesNotThrow(() -> order.setPaymentDate("2024-01-01 10:00:00"));
        assertDoesNotThrow(() -> order.setPackageInfo("width:10.0; height:15.0; depth:20.0; weight:1.5; isFragile:true"));
        
        assertThrows(GeneralDateTimeException.class, () -> order.setDeliveryDate(null));
    }

    @Test
    @DisplayName("La interfaz Storable devuelve nulls de forma segura si no hay PackageInfo")
    void storableInterface_WithoutPackage_ReturnsSafeNulls() {
        Order order = new Order();
        
        assertNull(order.getPhysicalData());
        assertThrows(NullPointerException.class, () -> order.getDimensions());
        assertThrows(NullPointerException.class, () -> order.getWeight());
        assertThrows(NullPointerException.class, () -> order.getWidth());
        assertThrows(NullPointerException.class, () -> order.getHeight());
        assertThrows(NullPointerException.class, () -> order.getDepth());
        assertThrows(NullPointerException.class, () -> order.getIsFragile());
        assertThrows(NullPointerException.class, () -> order.setDimensions(10, 10, 10));
    }

    @Test
    @DisplayName("La interfaz Storable delega correctamente si hay PackageInfo")
    void storableInterface_WithPackage_DelegatesCorrectly() throws BuildException, GeneralDateTimeException {
        Order order = new Order();
        order.setPaymentDate("2024-01-01 10:00:00");
        order.setPackageInfo("width:10.0; height:15.0; depth:20.0; weight:1.5; isFragile:true");

        assertEquals("weight:1.5;height:15.0;width:10.0;depth:20.0;isFragile:true", order.getPhysicalData());
        assertEquals("width:10.0,height:15.0,depth:20.0", order.getDimensions());
        assertEquals(1.5, order.getWeight());
        assertEquals(10.0, order.getWidth());
        assertEquals(15.0, order.getHeight());
        assertEquals(20.0, order.getDepth());
        assertTrue(order.getIsFragile());

        assertDoesNotThrow(() -> order.setDimensions(20.0, 20.0, 20.0));
        assertEquals(20.0, order.getWidth());
    }

    @Test
    @DisplayName("Estado Cancelado detiene las modificaciones de CREATED")
    void orderCancelled_BlocksModifications() throws BuildException {
        Order order = new Order();
        order.orderCancelled();
        
        assertEquals("CANCELLED", order.getStatus());
        assertThrows(BuildException.class, () -> order.setIdClient(1));
        assertThrows(BuildException.class, () -> order.setAddress("Calle Valid"));
        assertThrows(BuildException.class, () -> order.createDetail("R1", 1, 1.0, 0.0));
    }

    @Test
    @DisplayName("Los getters de fechas devuelven null si no han sido inicializadas")
    void getDates_WhenNull_ReturnsNull() {
        Order order = new Order();
        
        assertNull(order.getStartDate());
        assertNull(order.getFinishDate());
        assertNull(order.getPaymentDate());
        assertNull(order.getDeliveryDate());
    }
}