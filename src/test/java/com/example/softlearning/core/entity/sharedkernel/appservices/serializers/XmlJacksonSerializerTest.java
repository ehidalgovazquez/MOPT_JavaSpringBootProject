package com.example.softlearning.core.entity.sharedkernel.appservices.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.book.model.Book;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class XmlJacksonSerializerTest {

    private XmlJacksonSerializer serializer;
    private Book validBook;

    @BeforeEach
    void setUp() throws BuildException {
        serializer = new XmlJacksonSerializer(); 
        validBook = Book.getInstance(
            101, 
            "El Señor de los Anillos", 
            29.99, 
            true, 
            "J.R.R. Tolkien", 
            "1954-07-29", 
            "2024-05-01", 
            1.5,
            15.0,
            22.0,
            5.0,
            false
        );
    }

    @Test
    @DisplayName("Debe serializar correctamente un objeto Book a formato XML")
    void serialize_ValidBook_ReturnsXmlString() throws Exception {
        String xmlResult = serializer.serialize(validBook);

        assertNotNull(xmlResult, "El resultado XML no debe ser nulo");
        assertTrue(xmlResult.contains("<id>101</id>"), "Debe contener la etiqueta id");
        assertTrue(xmlResult.contains("<name>El Señor de los Anillos</name>"), "Debe contener el nombre");
        assertTrue(xmlResult.contains("<price>29.99</price>"), "Debe contener el precio");
        assertTrue(xmlResult.contains("<available>true</available>"), "Debe contener la disponibilidad");
        assertTrue(xmlResult.contains("<author>J.R.R. Tolkien</author>"), "Debe contener el autor");
        assertTrue(xmlResult.contains("1954-07-29"), "Debe contener la fecha de publicación");
        assertTrue(xmlResult.contains("<dimensions>"), "Debe contener la apertura del objeto compuesto dimensions");
        assertTrue(xmlResult.contains("<weight>1.5</weight>"), "Debe serializar el peso anidado");
        assertTrue(xmlResult.contains("<width>15.0</width>"), "Debe serializar el ancho anidado");
    }

    @Test
    @DisplayName("Debe devolver exactamente la representación de null en XML (<null/>)")
    void serialize_NullObject_HandlesGracefully() throws Exception {
        String result = serializer.serialize(null);
        assertEquals("<null/>", result, "El XML devuelto para un nulo debe ser exactamente con <null/>");
    }

    @Test
    @DisplayName("Debe deserializar correctamente un XML válido a un objeto Book")
    void deserialize_ValidXml_ReturnsBookObject() throws Exception {
        String xmlInput = "<Book>"
                + "<id>202</id>"
                + "<name>1984</name>"
                + "<price>19.50</price>"
                + "<available>false</available>"
                + "<author>George Orwell</author>"
                + "<publicationDate>1949-06-08</publicationDate>"
                + "<availabilityDate>2024-01-10</availabilityDate>"
                + "<dimensions>"
                +   "<weight>0.8</weight>"
                +   "<width>13.5</width>"
                +   "<height>20.0</height>"
                +   "<depth>2.5</depth>"
                +   "<isFragile>false</isFragile>"
                + "</dimensions>"
                + "</Book>";

        Book resultBook = (Book) serializer.deserialize(xmlInput, Book.class);

        assertNotNull(resultBook);
        assertEquals(202, resultBook.getId());
        assertEquals("1984", resultBook.getName());
        assertEquals(19.50, resultBook.getPrice());
        assertFalse(resultBook.isAvailable());
        assertEquals("George Orwell", resultBook.getAuthor());
        assertEquals("1949-06-08", resultBook.getPublicationDate());
        assertEquals(0.8, resultBook.getWeight());
        assertEquals(13.5, resultBook.getWidth());
        assertEquals(20.0, resultBook.getHeight());
        assertFalse(resultBook.getIsFragile());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el XML está mal formado")
    void deserialize_MalformedXml_ThrowsException() {
        String malformedXml = "<Book><id>1</id><name>Cien años de soledad</Book>";

        assertThrows(Exception.class, () -> {
            serializer.deserialize(malformedXml, Book.class);
        }, "Debe fallar al procesar un XML con sintaxis inválida");
    }

    @Test
    @DisplayName("Debe ignorar valores inválidos si el setter devuelve -1 sin lanzar excepción")
    void deserialize_XmlWithInvalidDomainData_IgnoresInvalidValues() throws Exception {
        String xmlWithInvalidData = "<Book>"
                + "<id>-50</id>"
                + "<name>A</name>"
                + "<price>-10.0</price>"
                + "</Book>";

        Book resultBook = (Book) serializer.deserialize(xmlWithInvalidData, Book.class);

        assertNotNull(resultBook);
        assertEquals(0, resultBook.getId(), "El ID debió quedar en 0 por ser inválido");
        assertEquals(0.0, resultBook.getPrice(), "El precio debió quedar en 0.0 por ser inválido");
        assertNull(resultBook.getName(), "El nombre debió quedar null por no cumplir los requisitos");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el XML contiene una fecha mal formateada")
    void deserialize_XmlWithInvalidDate_ThrowsException() {
        String xmlWithBadDate = "<Book>"
                + "<id>10</id>"
                + "<publicationDate>31/12/2023</publicationDate>"
                + "</Book>";

        assertThrows(Exception.class, () -> {
            serializer.deserialize(xmlWithBadDate, Book.class);
        }, "Debe fallar porque Jackson atrapa la DateTimeException del setter");
    }
}