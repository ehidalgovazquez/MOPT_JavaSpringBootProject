package com.example.softlearning.functionals.serializers;

import java.util.ArrayList;

import com.example.softlearning.core.entity.order.dtos.SpanishOrderDTO;
import com.example.softlearning.core.entity.order.dtos.SpanishOrderDetailDTO;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;
import com.example.softlearning.core.entity.sharedkernel.model.physicals.dtos.SpanishPhysicalDataDTO;

public class SerializersCatalogSpanishOrder {
        public static void main(String[] args) {
                SpanishOrderDTO spanishOrder = new SpanishOrderDTO(
                        "ORD12345",
                        1,
                        "2024-06-01",
                        "Order description",
                        "123 Main St, City, Country",
                        "John Doe",
                        "+1234567890",
                        new ArrayList<SpanishOrderDetailDTO>() {{
                                add(new SpanishOrderDetailDTO("PROD1", 2, 19.99, 0.0));
                                add(new SpanishOrderDetailDTO("PROD2", 1, 9.99, 1.0));
                        }},
                        "2024-06-02",
                        new SpanishPhysicalDataDTO(10.0, 5.0, 3.0, 2.0, true),
                        "2024-06-03",
                        "2024-06-04"
                );

                try {
                        // JSON
                        Serializer<SpanishOrderDTO> jsonSerializer = SerializersCatalog.getInstance(Serializers.JSON_SP_ORDER);
                        String jsonSpanishOrder = jsonSerializer.serialize(spanishOrder);
                        System.out.println("JSON Order:");
                        System.out.println(jsonSpanishOrder);
                        SpanishOrderDTO OrderFromJson = jsonSerializer.deserialize(jsonSpanishOrder, SpanishOrderDTO.class);
                        System.out.println(OrderFromJson);

                        // XML
                        Serializer<SpanishOrderDTO> xmlSerializer = SerializersCatalog.getInstance(Serializers.XML_SP_ORDER);
                        String xmlSpanishOrder = xmlSerializer.serialize(spanishOrder);
                        System.out.println("\nXML Order:");
                        System.out.println(xmlSpanishOrder);
                        SpanishOrderDTO OrderFromXml = xmlSerializer.deserialize(xmlSpanishOrder, SpanishOrderDTO.class);
                        System.out.println(OrderFromXml);
                } catch (ServiceException e) {
                        System.out.println("ERROR: " + e.getMessage());
                }
        }
}