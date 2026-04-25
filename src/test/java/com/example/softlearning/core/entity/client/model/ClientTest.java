package com.example.softlearning.core.entity.client.model;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.stakeholders.Person;
import com.example.softlearning.core.entity.sharedkernel.model.stakeholders.Stakeholder;

class ClientTest {

    @Test
    @DisplayName("Crea la instancia correctamente con todos los parámetros válidos")
    void getInstance_ValidParameters_ReturnsClient() throws BuildException {
        Client client = Client.getInstance(
                "45123456A", 
                "joan.garcia@example.com", 
                "600123456", 
                "Carrer Major 12", 
                "Joan Garcia", 
                1500, 
                "2023-05-15"
        );

        assertNotNull(client);
        assertEquals("45123456A", client.getIdPerson());
        assertEquals("joan.garcia@example.com", client.getEmail());
        assertEquals("600123456", client.getPhone());
        assertEquals("Carrer Major 12", client.getAddress());
        assertEquals("Joan Garcia", client.getNamePerson());
        assertEquals(1500, client.getIdClient());
        assertEquals("2023-05-15", client.getRegistrationDate());
    }

    @Test
    @DisplayName("Falla acumulando todos los errores posibles simultáneamente")
    void getInstance_AllInvalidParameters_ThrowsAccumulatedException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance(
                "A", 
                "bad@co", 
                "123", 
                "Short", 
                "J", 
                500, 
                "bad-date"
            )
        );
        
        String msg = ex.getMessage();
        assertTrue(msg.contains("Bad idPerson;"));
        assertTrue(msg.contains("Bad Email;"));
        assertTrue(msg.contains("Bad Phone;"));
        assertTrue(msg.contains("Bad Address;"));
        assertTrue(msg.contains("Bad NamePerson;"));
        assertTrue(msg.contains("Bad idClient;"));
        assertTrue(msg.contains("Bad Date:"));
    }

    @Test
    @DisplayName("Falla únicamente por idPerson inválido")
    void getInstance_InvalidIdPerson_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("1", "valid.email@example.com", "123456789", "Valid Address", "Valid Name", 1500, "2023-05-15")
        );
        assertEquals("Bad idPerson;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Falla únicamente por Email inválido")
    void getInstance_InvalidEmail_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("12A", "short@e.co", "123456789", "Valid Address", "Valid Name", 1500, "2023-05-15")
        );
        assertEquals("Bad Email;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Falla únicamente por Phone inválido")
    void getInstance_InvalidPhone_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("12A", "valid.email@example.com", "12345678", "Valid Address", "Valid Name", 1500, "2023-05-15")
        );
        assertEquals("Bad Phone;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Falla únicamente por Address inválido")
    void getInstance_InvalidAddress_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("12A", "valid.email@example.com", "123456789", "123456789", "Valid Name", 1500, "2023-05-15")
        );
        assertEquals("Bad Address;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Falla únicamente por NamePerson inválido")
    void getInstance_InvalidNamePerson_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("12A", "valid.email@example.com", "123456789", "Valid Address", "A", 1500, "2023-05-15")
        );
        assertEquals("Bad NamePerson;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Falla únicamente por idClient menor al límite establecido")
    void getInstance_InvalidIdClient_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("12A", "valid.email@example.com", "123456789", "Valid Address", "Valid Name", 999, "2023-05-15")
        );
        assertEquals("Bad idClient;", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Falla únicamente por formato de fecha incorrecto")
    void getInstance_InvalidDate_ThrowsException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Client.getInstance("12A", "valid.email@example.com", "123456789", "Valid Address", "Valid Name", 1500, "15/05/2023")
        );
        assertTrue(ex.getMessage().contains("Bad Date:"));
    }

    @Test
    @DisplayName("Valida el comportamiento de los setters heredados de Person")
    void personSetters_ValidationAndAssignments() {
        Client client = new Client();

        assertEquals(0, client.setIdPerson("AB"));
        assertEquals("AB", client.getIdPerson());
        assertEquals(-1, client.setIdPerson("A"));

        assertEquals(0, client.setEmail("12345678@e.co")); 
        assertEquals("12345678@e.co", client.getEmail());
        assertEquals(-1, client.setEmail("123456@e.co"));

        assertEquals(0, client.setPhone("123456789"));
        assertEquals("123456789", client.getPhone());
        assertEquals(-1, client.setPhone("12345678"));

        assertEquals(0, client.setAddress("1234567890"));
        assertEquals("1234567890", client.getAddress());
        assertEquals(-1, client.setAddress("123456789"));

        assertEquals(0, client.setNamePerson("Jo"));
        assertEquals("Jo", client.getNamePerson());
        assertEquals(-1, client.setNamePerson("J"));
    }

    @Test
    @DisplayName("Valida el comportamiento de los setters propios de Client")
    void clientSetters_ValidationAndAssignments() {
        Client client = new Client();

        assertTrue(client.setidClient(1000));
        assertEquals(1000, client.getIdClient());
        assertFalse(client.setidClient(999));

        assertDoesNotThrow(() -> client.setRegistrationDate("2024-01-01"));
        assertEquals("2024-01-01", client.getRegistrationDate());
        assertThrows(DateTimeException.class, () -> client.setRegistrationDate("01-01-2024"));
    }

    @Test
    @DisplayName("Calcula correctamente los años de antigüedad (yearsOld)")
    void yearsOld_CalculatesDifferenceRespectToCurrentYear() throws BuildException {
        int currentYear = LocalDate.now().getYear();
        int registrationYear = currentYear - 10; 
        
        Client client = Client.getInstance(
            "12A", "valid.email@example.com", "123456789", "Valid Address", "Valid Name", 1500, registrationYear + "-01-01"
        );

        assertEquals(10, client.yearsOld());
    }

    @Test
    @DisplayName("Verifica que los métodos toString y de formato de texto generen la salida esperada")
    void stringFormatMethods_ReturnExpectedStrings() throws BuildException {
        Client client = Client.getInstance(
            "123A", "test@test.com123", "987654321", "Test Address 123", "Test Name", 1500, "2024-01-01"
        );

        String expectedPersonData = "\nIdPerson = 123A\nEmail = test@test.com123\nPhone = 987654321";
        assertEquals(expectedPersonData, client.getPersonData());

        String expectedContactData = expectedPersonData + "\nidClient = 1500";
        assertEquals(expectedContactData, client.getContactData());

        String expectedClientToString = "\nClient: \n getIdPerson = 123A\n idClient = 1500\n Email = test@test.com123\n RegistrationDate = 2024-01-01\n Phone = 987654321\n Address = Test Address 123\n NamePerson = Test Name\n";
        assertEquals(expectedClientToString, client.toString());

        Person anonymousPerson = new Person() {};
        anonymousPerson.setIdPerson(client.getIdPerson());
        anonymousPerson.setEmail(client.getEmail());
        anonymousPerson.setPhone(client.getPhone());
        anonymousPerson.setAddress(client.getAddress());
        anonymousPerson.setNamePerson(client.getNamePerson());

        String expectedPersonToString = "\nPerson: \n IdPerson = 123A\n Email = test@test.com123\n Phone = 987654321\n Address = Test Address 123\n NamePerson = Test Name\n";
        assertEquals(expectedPersonToString, anonymousPerson.toString());
    }

    @Test
    @DisplayName("Comprueba que la implementación de Stakeholder retorna los valores correctos")
    void stakeholderInterface_ExposesCorrectMapping() throws BuildException {
        Stakeholder stakeholder = Client.getInstance(
            "123A", "test@test.com123", "987654321", "Test Address 123", "Test Name", 2050, "2024-01-01"
        );

        assertEquals("2050", stakeholder.ident());
        assertEquals("Test Name", stakeholder.name());
        
        String expectedContactData = "\nIdPerson = 123A\nEmail = test@test.com123\nPhone = 987654321\nidClient = 2050";
        assertEquals(expectedContactData, stakeholder.contactData());
    }
}