package com.example.softlearning.core.entity.client.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.client.model.Client;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class ClientMapperTest {

    @Test
    @DisplayName("Mapea un Client valido hacia un ClientDTO correctamente")
    void clientToDTO_ValidClient_ReturnsClientDTO() throws BuildException {
        Client client = Client.getInstance(
            "12345678A", "cliente@dominio.com", "123456789", "Calle Verdadera 123", "Juan Perez", 1000, "2024-01-01"
        );

        ClientDTO dto = ClientMapper.ClientToDTO(client);

        assertNotNull(dto);
        assertEquals(1000, dto.getIdClient());
        assertEquals("12345678A", dto.getIdPerson());
        assertEquals("cliente@dominio.com", dto.getEmail());
        assertEquals("123456789", dto.getPhone());
        assertEquals("Calle Verdadera 123", dto.getAddress());
        assertEquals("Juan Perez", dto.getNamePerson());
        assertEquals("2024-01-01", dto.getRegistrationDate());
    }

    @Test
    @DisplayName("Mapea un ClientDTO valido hacia un Client correctamente")
    void dtoToClient_ValidDTO_ReturnsClient() throws BuildException {
        ClientDTO dto = new ClientDTO(
            1500, "87654321B", "otro@dominio.com", "987654321", "Avenida Siempreviva 742", "Ana Garcia", "2023-12-31"
        );

        Client client = ClientMapper.DTOToClient(dto);

        assertNotNull(client);
        assertEquals(1500, client.getIdClient());
        assertEquals("87654321B", client.getIdPerson());
        assertEquals("otro@dominio.com", client.getEmail());
        assertEquals("987654321", client.getPhone());
        assertEquals("Avenida Siempreviva 742", client.getAddress());
        assertEquals("Ana Garcia", client.getNamePerson());
        assertEquals("2023-12-31", client.getRegistrationDate());
    }

    @Test
    @DisplayName("Lanza NullPointerException si se intenta mapear un Client null a DTO")
    void clientToDTO_NullClient_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ClientMapper.ClientToDTO(null));
    }

    @Test
    @DisplayName("Lanza NullPointerException si se intenta mapear un DTO null a Client")
    void dtoToClient_NullDTO_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ClientMapper.DTOToClient(null));
    }

    @Test
    @DisplayName("Lanza BuildException si el DTO contiene absolutamente todos los datos invalidos")
    void dtoToClient_AllInvalidData_ThrowsBuildException() {
        ClientDTO invalidDto = new ClientDTO(
            -5, "1", "mal@co", "123", "Corta", "J", "fecha-mala"
        );

        BuildException ex = assertThrows(BuildException.class, () -> ClientMapper.DTOToClient(invalidDto));
        
        String msg = ex.getMessage();
        assertTrue(msg.contains("Bad idPerson"));
        assertTrue(msg.contains("Bad Email"));
        assertTrue(msg.contains("Bad Phone"));
        assertTrue(msg.contains("Bad Address"));
        assertTrue(msg.contains("Bad NamePerson"));
        assertTrue(msg.contains("Bad idClient"));
        assertTrue(msg.contains("Bad Date"));
    }

    @Test
    @DisplayName("Lanza BuildException si el DTO tiene unicamente el idClient invalido")
    void dtoToClient_InvalidIdClient_ThrowsBuildException() {
        ClientDTO dto = new ClientDTO(
            -5, "12345678A", "cliente@dominio.com", "123456789", "Calle Verdadera 123", "Juan Perez", "2024-01-01"
        );

        BuildException ex = assertThrows(BuildException.class, () -> ClientMapper.DTOToClient(dto));
        assertEquals("Bad idClient;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Lanza BuildException si el DTO tiene unicamente la fecha mal formateada")
    void dtoToClient_InvalidDate_ThrowsBuildException() {
        ClientDTO dto = new ClientDTO(
            1500, "12345678A", "cliente@dominio.com", "123456789", "Calle Verdadera 123", "Juan Perez", "01-01-2024"
        );

        BuildException ex = assertThrows(BuildException.class, () -> ClientMapper.DTOToClient(dto));
        assertTrue(ex.getMessage().contains("Bad Date"));
    }
}