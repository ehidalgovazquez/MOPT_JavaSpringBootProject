package com.example.softlearning.functionals.serializers;

import com.example.softlearning.core.entity.book.dtos.SpanishBookDTO;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

public class SerializersCatalogSpanishBook {
        public static void main(String[] args) {
                SpanishBookDTO spanishBook = new SpanishBookDTO(
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
                        Serializer<SpanishBookDTO> jsonSerializer = SerializersCatalog.getInstance(Serializers.JSON_SP_BOOK);
                        String jsonSpanishBook = jsonSerializer.serialize(spanishBook);
                        System.out.println("JSON Book:");
                        System.out.println(jsonSpanishBook);
                        SpanishBookDTO bookFromJson = jsonSerializer.deserialize(jsonSpanishBook, SpanishBookDTO.class);
                        System.out.println(bookFromJson);

                        // XML
                        Serializer<SpanishBookDTO> xmlSerializer = SerializersCatalog.getInstance(Serializers.XML_SP_BOOK);
                        String xmlSpanishBook = xmlSerializer.serialize(spanishBook);
                        System.out.println("\nXML Book:");
                        System.out.println(xmlSpanishBook);
                        SpanishBookDTO bookFromXml = xmlSerializer.deserialize(xmlSpanishBook, SpanishBookDTO.class);
                        System.out.println(bookFromXml);
                } catch (ServiceException e) {
                        System.out.println("ERROR: " + e.getMessage());
                }
        }
}