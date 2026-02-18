package com.example.softlearning.functionals.serializers;

import com.example.softlearning.core.entity.client.dtos.SpanishClientDTO;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

public class SerializersCatalogSpanishClient {
        public static void main(String[] args) {
                SpanishClientDTO spanishClient = new SpanishClientDTO(
                        1,
                        "P12345",
                        "joan@mail.com",
                        "666777888",
                        "Carrer Major 10",
                        "Joan Garcia",
                        "2024-10-01"
                );

                try {
                        // JSON
                        Serializer<SpanishClientDTO> jsonSerializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
                        String jsonSpanishClient = jsonSerializer.serialize(spanishClient);
                        System.out.println("JSON Client:");
                        System.out.println(jsonSpanishClient);
                        SpanishClientDTO clientFromJson = jsonSerializer.deserialize(jsonSpanishClient, SpanishClientDTO.class);
                        System.out.println(clientFromJson);

                        // XML
                        Serializer<SpanishClientDTO> xmlSerializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
                        String xmlSpanishClient = xmlSerializer.serialize(spanishClient);
                        System.out.println("\nXML Client:");
                        System.out.println(xmlSpanishClient);
                        SpanishClientDTO clientFromXml = xmlSerializer.deserialize(xmlSpanishClient, SpanishClientDTO.class);
                        System.out.println(clientFromXml);
                } catch (ServiceException e) {
                        System.out.println("ERROR: " + e.getMessage());
                }
        }
        }
