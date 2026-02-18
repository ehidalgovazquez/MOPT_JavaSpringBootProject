package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.softlearning.core.entity.book.appservices.BookServicesImpl;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@SpringBootApplication
public class BookServicesFunctionals {
    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(BookServicesFunctionals.class, args);
        BookServicesImpl bookServices = context.getBean(BookServicesImpl.class);

        System.out.println("\n\n===============================");
        System.out.println("  F U N C T I O N A L   T E S T S  ( JSON )");
        System.out.println("===============================\n");

        String vAddFromJson = """
        {
            "id": 141,
            "name": "Clean Architecture",
            "price": 39.90,
            "available": true,
            "author": "Robert Martin",
            "publicationDate": "2018-01-01",
            "availabilityDate": "2018-01-10",
            "weight": 0.75,
            "width": 15.0,
            "height": 23.0,
            "depth": 3.0,
            "isFragile": false
        }
        """;

        String iAddFromJson = """
        {
            "id": -142,
            "name": "Clean Architecture",
            "price": -5,
            "available": true,
            "author": "Robert Martin",
            "publicationDate": "2018-01-01",
            "availabilityDate": "10-01-2018",
            "weight": 0.75,
            "width": -2,
            "height": 23.0,
            "depth": 3.0,
            "isFragile": false
        }
        """;

        String vUpdateOneFromJson = """
        {
            "id": 141,
            "name": "Clean Architecture (Updated)",
            "price": 42.50,
            "available": true,
            "author": "Robert Martin",
            "publicationDate": "2018-01-01",
            "availabilityDate": "2018-01-15",
            "weight": 0.80,
            "width": 15.0,
            "height": 23.0,
            "depth": 3.2,
            "isFragile": false
        }
        """;
        
        String iUpdateOneFromJson = """
        {
            "id": 142,
            "name": "Clean Architecture (Updated)",
            "price": 42.50,
            "available": true,
            "author": "Robert Martin",
            "publicationDate": "2018-01-01",
            "availabilityDate": "2018-01-15",
            "weight": 0.80,
            "width": 15.0,
            "height": 23.0,
            "depth": 3.2,
            "isFragile": false
        }
        """;

        System.out.println("\n--- Adding book from JSON (valid) ---");
        try {
            bookServices.addFromJson(vAddFromJson);
            System.out.println("Book added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting book 141 to JSON (valid) ---");
        try {
            System.out.println(bookServices.getByIdToJson(141));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding book from JSON (invalid) ---");
        try {
            bookServices.addFromJson(iAddFromJson);
            System.out.println("Book added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting book -142 to JSON (invalid) ---");
        try {
            System.out.println(bookServices.getByIdToJson(-142));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding book from JSON already exists (invalid) ---");
        try {
            bookServices.addFromJson(vAddFromJson);
            System.out.println("Book added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating book from JSON (valid) ---");
        try {
            bookServices.updateOneFromJson(vUpdateOneFromJson);
            System.out.println("Book updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting book 141 to JSON after update (valid) ---");
        try {
            System.out.println(bookServices.getByIdToJson(141));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating inexistent book from JSON (invalid) ---");
        try {
            bookServices.updateOneFromJson(iUpdateOneFromJson);
            System.out.println("Book updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting book 142 to JSON after update (invalid) ---");
        try {
            System.out.println(bookServices.getByIdToJson(-142));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }

        String vAddFromXml = """
            <book>
                <id>141</id>
                <name>Clean Architecture</name>
                <price>39.90</price>
                <available>true</available>
                <author>Robert Martin</author>
                <publicationDate>2018-01-01</publicationDate>
                <availabilityDate>2018-01-10</availabilityDate>
                <weight>0.75</weight>
                <width>15.0</width>
                <height>23.0</height>
                <depth>3.0</depth>
                <isFragile>false</isFragile>
            </book>
        """;

        String iAddFromXml = """
            <book>
                <id>-142</id>
                <name>Clean Architecture</name>
                <price>-5</price>
                <available>true</available>
                <author>Robert Martin</author>
                <publicationDate>2018-01-01</publicationDate>
                <availabilityDate>10-01-2018</availabilityDate>
                <weight>0.75</weight>
                <width>-2</width>
                <height>23.0</height>
                <depth>3.0</depth>
                <isFragile>false</isFragile>
            </book>
        """;

        String vUpdateOneFromXml = """
            <book>
                <id>141</id>
                <name>Clean Architecture (Updated)</name>
                <price>42.50</price>
                <available>true</available>
                <author>Robert Martin</author>
                <publicationDate>2018-01-01</publicationDate>
                <availabilityDate>2018-01-15</availabilityDate>
                <weight>0.80</weight>
                <width>15.0</width>
                <height>23.0</height>
                <depth>3.2</depth>
                <isFragile>false</isFragile>
            </book>
        """;

        String iUpdateOneFromXml = """
            <book>
                <id>142</id>
                <name>Clean Architecture</name>
                <price>42.50</price>
                <available>true</available>
                <author>Robert Martin</author>
                <publicationDate>2018-01-01</publicationDate>
                <availabilityDate>2018-01-15</availabilityDate>
                <weight>0.80</weight>
                <width>15.0</width>
                <height>23.0</height>
                <depth>3.2</depth>
                <isFragile>false</isFragile>
            </book>
        """;

        System.out.println("\n\n===============================");
        System.out.println("  F U N C T I O N A L   T E S T S  ( XML )");
        System.out.println("===============================\n");

        System.out.println("\n--- Adding book from XML (valid) ---");
        try {
            bookServices.addFromXml(vAddFromXml);
            System.out.println("Book added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting book 141 to XML (valid) ---");
        try {
            System.out.println(bookServices.getByIdToXml(141));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Adding book from XML (invalid) ---");
        try {
            bookServices.addFromXml(iAddFromXml);
            System.out.println("Book added successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (expected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating book from XML (valid) ---");
        try {
            bookServices.updateOneFromXml(vUpdateOneFromXml);
            System.out.println("Book updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Getting book 141 to XML after update (valid) ---");
        try {
            System.out.println(bookServices.getByIdToXml(141));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected)");
            System.out.println("  → " + e.getMessage());
        }

        System.out.println("\n--- Updating inexistent book from XML (invalid) ---");
        try {
            bookServices.updateOneFromXml(iUpdateOneFromXml);
            System.out.println("Book updated successfully.");
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (expected)");
            System.out.println("  → " + e.getMessage());
        }


        System.out.println("\n--- Getting book 142 to XML after update (invalid) ---");
        try {
            System.out.println(bookServices.getByIdToXml(142));
        } catch (ServiceException e) {
            System.out.println("✘ FAIL (unexpected) - during setup");
            System.out.println("  → " + e.getMessage());
        }
    }
}