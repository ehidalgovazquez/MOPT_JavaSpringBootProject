package com.example.softlearning.core.entity.book.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class BookTest {

    @Test
    @DisplayName("Crea la instancia correctamente con parámetros válidos (Camino Feliz)")
    void getInstance_AllValid_ReturnsBook() throws BuildException {
        Book book = Book.getInstance(
            101, "Cien años de soledad", 25.50, true, "Gabriel Garcia Marquez", 
            "1967-05-30", "2024-01-01", 0.85, 14.0, 21.0, 3.5, false
        );

        assertNotNull(book);
        assertEquals(101, book.getId());
        assertEquals("Cien años de soledad", book.getName());
        assertEquals(25.50, book.getPrice());
        assertTrue(book.isAvailable());
        assertEquals("Gabriel Garcia Marquez", book.getAuthor());
        assertEquals("1967-05-30", book.getPublicationDate());
        assertEquals(0.85, book.getWeight());
    }

    @Test
    @DisplayName("Lanza BuildException con todos los errores acumulados si todos los parámetros son inválidos")
    void getInstance_AllInvalid_ThrowsAccumulatedException() {
        BuildException ex = assertThrows(BuildException.class, () -> 
            Book.getInstance(
                -1, "A", -5.0, true, "B", 
                "30/05/1967", "01-01-2024", -1.0, -2.0, -3.0, -4.0, false
            )
        );
        
        String msg = ex.getMessage();
        assertTrue(msg.contains("Bad Id"));
        assertTrue(msg.contains("Bad Name"));
        assertTrue(msg.contains("Bad Price"));
        assertTrue(msg.contains("Bad Author"));
        assertTrue(msg.contains("Bad Publication Date"));
        assertTrue(msg.contains("Bad Availability Date"));
        assertTrue(msg.contains("Bad PhysicalData"));
        assertTrue(msg.contains("Bad Width"));
        assertTrue(msg.contains("Bad Height"));
        assertTrue(msg.contains("Bad Depth"));
        assertTrue(msg.contains("Bad Weight"));
    }

    @Test
    @DisplayName("Lanza excepción si las fechas no cumplen el formato yyyy-MM-dd")
    void getInstance_InvalidDateFormats_ThrowsException() {
        assertThrows(BuildException.class, () -> 
            Book.getInstance(1, "Valid Name", 10.0, true, "Valid Author", "1967-5-30", "2024-01-01", 1, 1, 1, 1, false)
        );
        
        assertThrows(BuildException.class, () -> 
            Book.getInstance(1, "Valid Name", 10.0, true, "Valid Author", "1967-05-30", "2024/01/01", 1, 1, 1, 1, false)
        );
    }

    @Test
    @DisplayName("Lanza excepción si el autor tiene menos de 2 caracteres")
    void setAuthor_InvalidLength_ReturnsMinusOne() {
        Book book = new Book();
        assertEquals(-1, book.setAuthor("A"));
        assertEquals(0, book.setAuthor("Author"));
    }

    @Test
    @DisplayName("setPhysicalData lanza BuildException si los datos físicos son negativos")
    void setPhysicalData_NegativeValues_ThrowsException() {
        Book book = new Book();
        BuildException ex = assertThrows(BuildException.class, () -> 
            book.setPhysicalData(-1.0, 10.0, 10.0, -5.0, true)
        );
        assertTrue(ex.getMessage().contains("Bad Weight"));
        assertTrue(ex.getMessage().contains("Bad Depth"));
    }

    @Test
    @DisplayName("La interfaz Storable (PhysicalData) devuelve los strings de dimensiones correctamente")
    void storableInterface_ReturnsCorrectStrings() throws BuildException {
        Book book = Book.getInstance(1, "Test", 10.0, true, "Author", "2024-01-01", "2024-01-01", 1.0, 10.5, 20.0, 5.0, true);
        
        assertEquals("width:10.5,height:20.0,depth:5.0", book.getDimensions());
        String physicalDataString = book.getPhysicalData();
        assertTrue(physicalDataString.contains("weight:1.0"));
        assertTrue(physicalDataString.contains("isFragile:true"));
    }

    @Test
    @DisplayName("setDimensions valida y actualiza las dimensiones u lanza excepción si alguna es incorrecta")
    void setDimensions_Validation() throws BuildException {
        Book book = Book.getInstance(1, "Test", 10.0, true, "Author", "2024-01-01", "2024-01-01", 1.0, 10.0, 10.0, 10.0, false);
        
        assertDoesNotThrow(() -> book.setDimensions(15.0, 25.0, 35.0));
        assertEquals(15.0, book.getWidth());
        
        BuildException ex = assertThrows(BuildException.class, () -> book.setDimensions(-1, 20, -1));
        assertTrue(ex.getMessage().contains("Bad Width"));
        assertTrue(ex.getMessage().contains("Bad Depth"));
    }

    @Test
    @DisplayName("getBookData devuelve la concatenación completa de Product + Book")
    void getBookData_ReturnsFullString() throws BuildException {
        Book book = Book.getInstance(1, "Libro", 10.0, true, "Escritor", "2000-01-01", "2000-01-01", 1, 10, 10, 10, false);
        
        String data = book.getBookData();
        assertTrue(data.contains("ID = 1"));
        assertTrue(data.contains("Author = Escritor"));
        assertTrue(data.contains("Publication Date = 2000-01-01"));
        assertTrue(data.contains("Dimensions = width:10.0,height:10.0,depth:10.0"));
    }

    @Test
    @DisplayName("Maneja correctamente setters manuales de Product")
    void productSetters_Validation() {
        Book book = new Book();
        assertEquals(0, book.setId(0));
        assertEquals(-1, book.setId(-1));
        
        assertEquals(0, book.setName("Valid"));
        assertEquals(-1, book.setName("V"));
        
        assertEquals(0, book.setPrice(0.0));
        assertEquals(-1, book.setPrice(-0.01));
        
        assertEquals(0, book.setAvailable(true));
    }
}