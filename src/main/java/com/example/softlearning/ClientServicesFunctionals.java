package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.softlearning.core.entity.client.appservices.ClientServicesImpl;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@SpringBootApplication
public class ClientServicesFunctionals {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ClientServicesFunctionals.class, args);
        ClientServicesImpl clientServices = context.getBean(ClientServicesImpl.class);

        System.out.println("\n\n===============================");
        System.out.println("  F U N C T I O N A L   T E S T S  ( JSON )");
        System.out.println("===============================\n");

        String vAddFromJson = """
        {
            "idPerson": "PERSON01",
            "email": "client1@example.com",
            "phone": "+34123456789",
            "address": "Calle Falsa 123",
            "namePerson": "Juan Perez",
            "idClient": 1001,
            "registrationDate": "2022-01-10"
        }
        """;

        String iAddFromJson = """
        {
            "idPerson": "",
            "email": "bademail",
            "phone": "",
            "address": "",
            "namePerson": "",
            "idClient": 1002,
            "registrationDate": "2022-01-10"
        }
        """;

        String vUpdateOneFromJson = """
        {
            "idPerson": "PERSON01",
            "email": "client1_updated@example.com",
            "phone": "+34123456780",
            "address": "Calle Verdadera 321",
            "namePerson": "Juan Perez Updated",
            "idClient": 1001,
            "registrationDate": "2022-01-10"
        }
        """;

        String iUpdateOneFromJson = """
        {
            "idPerson": "PERSON01",
            "email": "client1_updated@example.com",
            "phone": "+34123456780",
            "address": "Calle Verdadera 321",
            "namePerson": "Juan Perez Updated",
            "idClient": 1002,
            "registrationDate": "2022-01-10"
        }
        """;

        System.out.println("\n--- Adding client from JSON (valid) ---");
        try {
            clientServices.addFromJson(vAddFromJson);
            System.out.println("Client added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1001 to JSON (valid) ---");
        try {
            System.out.println(clientServices.getByIdToJson(1001));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding client from JSON (invalid) ---");
        try {
            clientServices.addFromJson(iAddFromJson);
            System.out.println("Client added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (expected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 999 to JSON (invalid) ---");
        try {
            System.out.println(clientServices.getByIdToJson(999));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding client from JSON already exists (invalid) ---");
        try {
            clientServices.addFromJson(vAddFromJson);
            System.out.println("Client added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating client from JSON (valid) ---");
        try {
            clientServices.updateOneFromJson(vUpdateOneFromJson);
            System.out.println("Client updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1001 to JSON after update (invalid) ---");
        try {
            System.out.println(clientServices.getByIdToJson(1001));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating inexistent client from JSON (invalid) ---");
        try {
            clientServices.updateOneFromJson(iUpdateOneFromJson);
            System.out.println("Client updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (expected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1002 to JSON after update (invalid) ---");
        try {
            System.out.println(clientServices.getByIdToJson(1002));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n\n===============================");
        System.out.println("  F U N C T I O N A L   T E S T S  ( XML )");
        System.out.println("===============================\n");

        String vAddFromXml = """
        <client>
            <idPerson>PERSON02</idPerson>
            <email>client1@example.com</email>
            <phone>+34123456789</phone>
            <address>Calle Falsa 123</address>
            <namePerson>Juan Perez</namePerson>
            <idClient>1003</idClient>
            <registrationDate>2022-01-10</registrationDate>
        </client>
        """;

        String iAddFromXml = """
        <client>
            <idPerson></idPerson>
            <email>bademail</email>
            <phone></phone>
            <address></address>
            <namePerson></namePerson>
            <idClient>1004</idClient>
            <registrationDate>2022-01-10</registrationDate>
        </client>
        """;

        String vUpdateOneFromXml = """
        <client>
            <idPerson>PERSON02</idPerson>
            <email>client1_updated@example.com</email>
            <phone>+34123456780</phone>
            <address>Calle Verdadera 321</address>
            <namePerson>Juan Perez Updated</namePerson>
            <idClient>1003</idClient>
            <registrationDate>2022-01-10</registrationDate>
        </client>
        """;

        String iUpdateOneFromXml = """
        <client>
            <idPerson>PERSON02</idPerson>
            <email>client1_updated@example.com</email>
            <phone>+34123456780</phone>
            <address>Calle Verdadera 321</address>
            <namePerson>Juan Perez Updated</namePerson>
            <idClient>1004</idClient>
            <registrationDate>2022-01-10</registrationDate>
        </client>
        """;

        System.out.println("\n--- Adding client from XML (valid) ---");
        try {
            clientServices.addFromXml(vAddFromXml);
            System.out.println("Client added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1003 to XML (valid) ---");
        try {
            System.out.println(clientServices.getByIdToXml(1003));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding client from XML (invalid) ---");
        try {
            clientServices.addFromXml(iAddFromXml);
            System.out.println("Client added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (expected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1004 to XML (invalid) ---");
        try {
            System.out.println(clientServices.getByIdToXml(1004));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding client from XML already exists (invalid) ---");
        try {
            clientServices.addFromXml(vAddFromXml);
            System.out.println("Client added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating client from XML (valid) ---");
        try {
            clientServices.updateOneFromXml(vUpdateOneFromXml);
            System.out.println("Client updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1003 to XML after update (invalid) ---");
        try {
            System.out.println(clientServices.getByIdToXml(1003));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating inexistent client from XML (invalid) ---");
        try {
            clientServices.updateOneFromXml(iUpdateOneFromXml);
            System.out.println("Client updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (expected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting client 1004 to XML after update (invalid) ---");
        try {
            System.out.println(clientServices.getByIdToXml(1004));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }
    }
}
