package com.example.softlearning.core.entity.sharedkernel.appservices.serializers;

import java.util.TreeMap;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.book.dtos.SpanishBookDTO;
import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.client.dtos.SpanishClientDTO;
import com.example.softlearning.core.entity.order.dtos.OrderDTO;
import com.example.softlearning.core.entity.order.dtos.SpanishOrderDTO;

public class SerializersCatalog {
    private static TreeMap<Serializers, Serializer> catalog = new TreeMap<>();

    private static void loadCatalog() {
        // AL CREAR EL SERIALITZADOR PASSEM PER CONSTRUCTOR L'OBJECTE AMB QUE ES
        // REALIZARÀ LA SERIALITZACIO

        // CLIENT
        catalog.put(Serializers.JSON_CLIENT, new JacksonSerializer<ClientDTO>());
        catalog.put(Serializers.JSON_SP_CLIENT, new JacksonSerializer<SpanishClientDTO>());
        catalog.put(Serializers.XML_CLIENT, new XmlJacksonSerializer<ClientDTO>());
        catalog.put(Serializers.XML_SP_CLIENT, new XmlJacksonSerializer<SpanishClientDTO>());

        // BOOK
        catalog.put(Serializers.JSON_BOOK, new JacksonSerializer<BookDTO>());
        catalog.put(Serializers.JSON_SP_BOOK, new JacksonSerializer<SpanishBookDTO>());
        catalog.put(Serializers.XML_BOOK, new XmlJacksonSerializer<BookDTO>());
        catalog.put(Serializers.XML_SP_BOOK, new XmlJacksonSerializer<SpanishBookDTO>());

        // ORDER
        catalog.put(Serializers.JSON_ORDER, new JacksonSerializer<OrderDTO>());
        catalog.put(Serializers.JSON_SP_ORDER, new JacksonSerializer<SpanishOrderDTO>());
        catalog.put(Serializers.XML_ORDER, new XmlJacksonSerializer<OrderDTO>());
        catalog.put(Serializers.XML_SP_ORDER, new XmlJacksonSerializer<SpanishOrderDTO>());
    }

    public static Serializer getInstance(Serializers type) {
        if (catalog.isEmpty()) {
            loadCatalog();
        }
        return catalog.get(type);
    }
}
