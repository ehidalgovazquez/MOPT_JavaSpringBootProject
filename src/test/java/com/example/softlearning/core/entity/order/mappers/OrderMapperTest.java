package com.example.softlearning.core.entity.order.mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailJpaDTO;
import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
import com.example.softlearning.core.entity.order.model.Order;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class OrderMapperTest {

    @Test
    @DisplayName("Mapea de Order (Domain) a OrderDTO correctamente con carrito vacío")
    void orderToDTO_FromDomain_EmptyCart_ReturnsDTO() throws BuildException {
        // Instancio un objeto real
        Order order = Order.getInstance(
            "REF-100", 1, "2024-01-01 10:00:00", "Desc", "Carrer Major 12", "John Doe", "123456789", "CREATED"
        );

        OrderDTO dto = OrderMapper.OrderToDTO(order);

        assertNotNull(dto);
        assertEquals("REF-100", dto.getRef());
        assertEquals(1, dto.getIdClient());
        assertEquals("Carrer Major 12", dto.getAddress());
        assertEquals("John Doe", dto.getName());
        assertTrue(dto.getShopcartDetails() == null || dto.getShopcartDetails().isEmpty());
    }

    @Test
    @DisplayName("Lanza NullPointerException si el Order a mapear es nulo")
    void orderToDTO_FromDomain_NullOrder_ThrowsException() {
        assertThrows(NullPointerException.class, () -> OrderMapper.OrderToDTO((Order) null));
    }

    @Test
    @DisplayName("Mapea de OrderJpaDTO a OrderDTO usando stubs manuales")
    void orderToDTO_FromJPA_ValidData_ReturnsDTO() {
        // Creo un stub manual de OrderDetailJpaDTO
        OrderDetailJpaDTO detailStub = new OrderDetailJpaDTO() {
            @Override public int getBookId() { return 99; }
            @Override public int getAmount() { return 2; }
            @Override public double getPrice() { return 15.0; }
            @Override public double getDiscount() { return 0.1; }
            @Override public BookDTO getBook() { return null; } 
        };

        List<OrderDetailJpaDTO> detailsList = new ArrayList<>();
        detailsList.add(detailStub);

        // Creo un stub manual de OrderJpaDTO
        OrderJpaDTO jpaStub = new OrderJpaDTO() {
            @Override public String getRef() { return "REF-JPA"; }
            @Override public int getIdClient() { return 10; }
            @Override public ClientDTO getClient() { return null; }
            @Override public String getStartDate() { return "2024-01-01 10:00:00"; }
            @Override public String getDescription() { return "JPA Desc"; }
            @Override public String getAddress() { return "JPA Address"; }
            @Override public String getName() { return "JPA Name"; }
            @Override public String getPhone() { return "987654321"; }
            @Override public List<OrderDetailJpaDTO> getShopcartDetails() { return detailsList; }
            @Override public LocalDateTime getPaymentDate() { return LocalDateTime.of(2024, 1, 2, 10, 0, 0); }
            @Override public String getPhysicalData() { return null; }
            @Override public LocalDateTime getDeliveryDate() { return LocalDateTime.of(2024, 1, 3, 10, 0, 0); }
            @Override public LocalDateTime getFinishDate() { return LocalDateTime.of(2024, 1, 4, 10, 0, 0); }
            @Override public String getStatus() { return "CONFIRMED"; }
        };

        OrderDTO dto = OrderMapper.OrderToDTO(jpaStub);

        assertNotNull(dto);
        assertEquals("REF-JPA", dto.getRef());
        assertEquals(10, dto.getIdClient());
        assertEquals("2024-01-02 10:00:00", dto.getPaymentDate());
        assertEquals("2024-01-03 10:00:00", dto.getDeliveryDate());
        assertEquals("2024-01-04 10:00:00", dto.getFinishDate());
        assertNotNull(dto.getShopcartDetails());
        assertEquals(1, dto.getShopcartDetails().size());
        assertEquals(99, dto.getShopcartDetails().get(0).getBookId());
    }

    @Test
    @DisplayName("Mapea de OrderJpaDTO manejando fechas y listas nulas de forma segura")
    void orderToDTO_FromJPA_WithNulls_HandlesGracefully() {
        OrderJpaDTO jpaStubNulls = new OrderJpaDTO() {
            @Override public String getRef() { return "REF-NULLS"; }
            @Override public int getIdClient() { return 1; }
            @Override public ClientDTO getClient() { return null; }
            @Override public String getStartDate() { return "2024-01-01 10:00:00"; }
            @Override public String getDescription() { return ""; }
            @Override public String getAddress() { return ""; }
            @Override public String getName() { return ""; }
            @Override public String getPhone() { return ""; }
            @Override public List<OrderDetailJpaDTO> getShopcartDetails() { return null; }
            @Override public LocalDateTime getPaymentDate() { return null; }
            @Override public String getPhysicalData() { return null; }
            @Override public LocalDateTime getDeliveryDate() { return null; }
            @Override public LocalDateTime getFinishDate() { return null; }
            @Override public String getStatus() { return "CREATED"; }
        };

        OrderDTO dto = OrderMapper.OrderToDTO(jpaStubNulls);

        assertNotNull(dto);
        assertEquals("REF-NULLS", dto.getRef());
        assertNull(dto.getPaymentDate());
        assertNull(dto.getDeliveryDate());
        assertNull(dto.getFinishDate());
        assertTrue(dto.getShopcartDetails() == null || dto.getShopcartDetails().isEmpty());
    }

    @Test
    @DisplayName("Lanza NullPointerException si el OrderJpaDTO es nulo")
    void orderToDTO_FromJPA_Null_ThrowsException() {
        assertThrows(NullPointerException.class, () -> OrderMapper.OrderToDTO((OrderJpaDTO) null));
    }

    @Test
    @DisplayName("Mapea de OrderDTO a Order (Domain) reconstruyendo el string del carrito de compras")
    void dtoToOrder_ValidDTO_ReturnsDomainOrder() throws BuildException {
        // Preparo los datos reales del DTO
        List<OrderDetailDTO> cartDetails = new ArrayList<>();
        cartDetails.add(new OrderDetailDTO(1, 2, 25.5, 0.0));
        cartDetails.add(new OrderDetailDTO(2, 1, 10.0, 0.1));

        OrderDTO dto = new OrderDTO(
            "REF-DTO", 5, "2024-01-01 10:00:00", "Desc", "Carrer Major 12", "Maria Garcia", 
            "987654321", cartDetails, "2024-01-02 10:00:00", 
            "width:10.0; height:10.0; depth:10.0; weight:1.0; isFragile:false", 
            null, null, "FORTHCOMMING"
        );

        Order order = OrderMapper.DTOToOrder(dto);

        assertNotNull(order);
        assertEquals("REF-DTO", order.getRef());
        assertEquals(5, order.getIdClient());
        assertEquals("Carrer Major 12", order.getAddress());
        assertEquals("FORTHCOMMING", order.getStatus());
        
        // Verifica que el mapper reconstruyó el string de detalles correctamente
        String shopCartStr = order.getShopcartDetails();
        assertTrue(shopCartStr.contains("1, 2, 25.5, 0.0"));
        assertTrue(shopCartStr.contains("2, 1, 10.0, 0.1"));
    }

    @Test
    @DisplayName("Lanza BuildException si el OrderDTO tiene datos corruptos de negocio")
    void dtoToOrder_InvalidDTO_ThrowsBuildException() {
        OrderDTO invalidDto = new OrderDTO(
            "REF", -1, "fecha-incorrecta", "Desc", "A", "B", "123", 
            new ArrayList<>(), null, null, null, null, "CREATED"
        );

        BuildException ex = assertThrows(BuildException.class, () -> OrderMapper.DTOToOrder(invalidDto));
        
        String msg = ex.getMessage();
        assertTrue(msg.contains("Bad date"));
        assertTrue(msg.contains("Bad idClient"));
        assertTrue(msg.contains("Bad Address"));
        assertTrue(msg.contains("Bad Name"));
        assertTrue(msg.contains("Bad PhoneContact"));
    }

    @Test
    @DisplayName("Lanza NullPointerException al intentar mapear un OrderDTO nulo a Order")
    void dtoToOrder_NullDTO_ThrowsException() {
        assertThrows(NullPointerException.class, () -> OrderMapper.DTOToOrder((OrderDTO) null));
    }
}