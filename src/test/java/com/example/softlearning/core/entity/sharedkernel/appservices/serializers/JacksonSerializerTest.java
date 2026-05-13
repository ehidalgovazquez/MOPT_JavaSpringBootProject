package com.example.softlearning.core.entity.sharedkernel.appservices.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.client.model.Client;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class JacksonSerializerTest {

    private JacksonSerializer serializer;
    private Client validClient;

    @BeforeEach
    void setUp() throws BuildException {
        serializer = new JacksonSerializer();
        validClient = Client.getInstance(
            "12345A", 
            "juan.perez@example.com", 
            "123456789", 
            "Calle Falsa 123, Madrid", 
            "Juan Perez", 
            1500, 
            "2023-05-15"
        );
    }

    @Test
    @DisplayName("Debe serializar correctamente un objeto Client completo a JSON")
    void serialize_ValidClient_ReturnsJsonString() throws Exception {
        String jsonResult = serializer.serialize(validClient);

        assertNotNull(jsonResult, "El resultado JSON no debe ser nulo");
        
        assertTrue(jsonResult.contains("\"idPerson\""), "Debe contener idPerson");
        assertTrue(jsonResult.contains("\"12345A\""), "Debe contener el valor de idPerson");
        
        assertTrue(jsonResult.contains("\"email\""), "Debe contener email");
        assertTrue(jsonResult.contains("\"juan.perez@example.com\""), "Debe contener el valor del email");
        
        assertTrue(jsonResult.contains("\"namePerson\""), "Debe contener namePerson");
        assertTrue(jsonResult.contains("\"Juan Perez\""), "Debe contener el valor de namePerson");
        
        assertTrue(jsonResult.contains("\"idClient\""), "Debe contener idClient");
        assertTrue(jsonResult.contains("1500"), "Debe contener el valor de idClient");
        
        assertTrue(jsonResult.contains("registrationDate"), "Debe contener registrationDate");
    }

    @Test
    @DisplayName("Debe deserializar correctamente un JSON válido a un objeto Client")
    void deserialize_ValidJson_ReturnsClientObject() throws Exception {
        String jsonInput = "{"
                + "\"idPerson\": \"98765B\","
                + "\"email\": \"ana.gomez@example.com\","
                + "\"phone\": \"987654321\","
                + "\"address\": \"Avenida Central 45\","
                + "\"namePerson\": \"Ana Gomez\","
                + "\"idClient\": 2000,"
                + "\"registrationDate\": \"2024-01-10\""
                + "}";

        Client resultClient = (Client) serializer.deserialize(jsonInput, Client.class);

        assertNotNull(resultClient, "El cliente deserializado no puede ser nulo");
        assertEquals("98765B", resultClient.getIdPerson());
        assertEquals("ana.gomez@example.com", resultClient.getEmail());
        assertEquals("987654321", resultClient.getPhone());
        assertEquals("Avenida Central 45", resultClient.getAddress());
        assertEquals("Ana Gomez", resultClient.getNamePerson());
        
        assertEquals(2000, resultClient.getIdClient());
        assertEquals("2024-01-10", resultClient.getRegistrationDate()); 
    }

    @Test
    @DisplayName("Debe lanzar excepción si el JSON está mal formado (error sintáctico)")
    void deserialize_MalformedJson_ThrowsException() {
        String malformedJson = "{ \"idPerson\": \"12345A, \"email: broken";

        assertThrows(Exception.class, () -> {
            serializer.deserialize(malformedJson, Client.class);
        }, "Debe fallar al intentar leer un JSON roto");
    }

    @Test
    @DisplayName("Debe devolver el string 'null' si se intenta serializar un null")
    void serialize_NullObject_ReturnsNullString() throws Exception {
        // Jackson por defecto convierte un objeto nulo en el string "null"
        String result = serializer.serialize(null);
        assertEquals("null", result, "Debe devolver la representación en string de null");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el JSON de entrada para deserializar es null o vacío")
    void deserialize_NullOrEmptyString_ThrowsException() {
        assertThrows(Exception.class, () -> {
            serializer.deserialize(null, Client.class);
        }, "Debe fallar con input null");
        
        assertThrows(Exception.class, () -> {
            serializer.deserialize("", Client.class);
        }, "Debe fallar con input vacío");
    }

    @Test
    @DisplayName("Debe ignorar valores inválidos al deserializar si los setters no lanzan excepción")
    void deserialize_JsonWithInvalidDomainData_IgnoresInvalidValues() throws Exception {
        String jsonWithInvalidData = "{"
                + "\"namePerson\": \"A\","
                + "\"idClient\": 0,"
                + "\"registrationDate\": \"2024-01-10\"" 
                + "}";

        Client resultClient = (Client) serializer.deserialize(jsonWithInvalidData, Client.class);

        assertNotNull(resultClient);
        assertNull(resultClient.getNamePerson(), "El nombre no debió asignarse porque tiene menos de 2 caracteres");
        assertEquals(0, resultClient.getIdClient(), "El idClient no debió asignarse porque es menor de 1");
    }

    @Test
    @DisplayName("Debe lanzar excepción al deserializar un JSON con una fecha de formato inválido")
    void deserialize_JsonWithInvalidDate_ThrowsException() {
        String jsonWithBadDate = "{"
                + "\"idClient\": 1500,"
                + "\"registrationDate\": \"31-12-2024\"" 
                + "}";

        assertThrows(Exception.class, () -> {
            serializer.deserialize(jsonWithBadDate, Client.class);
        }, "Debe fallar porque el formato de la fecha no coincide con el DateTimeFormatter");
    }
}