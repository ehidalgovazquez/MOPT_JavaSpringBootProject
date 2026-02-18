package com.example.softlearning.functionals.serializers;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

public class SerializersCatalogClient {
        public static void main(String[] args) {
                ClientDTO client = new ClientDTO(
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
                        Serializer<ClientDTO> jsonSerializer = SerializersCatalog.getInstance(Serializers.JSON_CLIENT);
                        String jsonClient = jsonSerializer.serialize(client);
                        System.out.println("JSON Client:");
                        System.out.println(jsonClient);
                        ClientDTO clientFromJson = jsonSerializer.deserialize(jsonClient, ClientDTO.class);
                        System.out.println(clientFromJson);

                        // XML
                        Serializer<ClientDTO> xmlSerializer = SerializersCatalog.getInstance(Serializers.XML_CLIENT);
                        String xmlClient = xmlSerializer.serialize(client);
                        System.out.println("\nXML Client:");
                        System.out.println(xmlClient);
                        ClientDTO clientFromXml = xmlSerializer.deserialize(xmlClient, ClientDTO.class);
                        System.out.println(clientFromXml);
                } catch (ServiceException e) {
                        System.out.println("ERROR: " + e.getMessage());
                }
        }
        }
