package com.example.softlearning.functionals.serializers;

import java.util.ArrayList;

import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDetailDTO;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos.PhysicalDataDTO;

public class SerializersCatalogOrder {
        public static void main(String[] args) {
                OrderDTO order = new OrderDTO(
                        "ORD12345",
                        1,
                        "2024-06-01",
                        "Order description",
                        "123 Main St, City, Country",
                        "John Doe",
                        "+1234567890",
                        new ArrayList<OrderDetailDTO>() {{
                                add(new OrderDetailDTO(1, 2, 19.99, 0.0));
                                add(new OrderDetailDTO(2, 1, 9.99, 1.0));
                        }},
                        "2024-06-02",
                        new PhysicalDataDTO(1.5, 20.0, 15.0, 5.0),
                        "2024-06-03",
                        "2024-06-04"
                );

                try {
                        // JSON
                        Serializer<OrderDTO> jsonSerializer = SerializersCatalog.getInstance(Serializers.JSON_ORDER);
                        String jsonOrder = jsonSerializer.serialize(order);
                        System.out.println("JSON Order:");
                        System.out.println(jsonOrder);
                        OrderDTO OrderFromJson = jsonSerializer.deserialize(jsonOrder, OrderDTO.class);
                        System.out.println(OrderFromJson);

                        // XML
                        Serializer<OrderDTO> xmlSerializer = SerializersCatalog.getInstance(Serializers.XML_ORDER);
                        String xmlOrder = xmlSerializer.serialize(order);
                        System.out.println("\nXML Order:");
                        System.out.println(xmlOrder);
                        OrderDTO OrderFromXml = xmlSerializer.deserialize(xmlOrder, OrderDTO.class);
                        System.out.println(OrderFromXml);
                } catch (ServiceException e) {
                        System.out.println("ERROR: " + e.getMessage());
                }
        }
}