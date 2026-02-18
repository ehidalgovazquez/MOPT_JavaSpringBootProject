package com.example.softlearning.functionals.serializers;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

public class SerializersCatalogBook {
        public static void main(String[] args) {
                BookDTO book = new BookDTO(
                        101,
                        "Effective Java",
                        45.99,
                        true,
                        "Joshua Bloch",
                        "2018-01-06",
                        "2018-02-01",
                        1.2,
                        15.0,
                        2.5,
                        22.0,
                        false
                );

                try {
                        // JSON
                        Serializer<BookDTO> jsonSerializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
                        String jsonBook = jsonSerializer.serialize(book);
                        System.out.println("JSON Book:");
                        System.out.println(jsonBook);
                        BookDTO bookFromJson = jsonSerializer.deserialize(jsonBook, BookDTO.class);
                        System.out.println(bookFromJson);

                        // XML
                        Serializer<BookDTO> xmlSerializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
                        String xmlBook = xmlSerializer.serialize(book);
                        System.out.println("\nXML Book:");
                        System.out.println(xmlBook);
                        BookDTO bookFromXml = xmlSerializer.deserialize(xmlBook, BookDTO.class);
                        System.out.println(bookFromXml);
                } catch (ServiceException e) {
                        System.out.println("ERROR: " + e.getMessage());
                }
        }
}