package com.example.softlearning.core.entity.book.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.book.model.Book;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class BookMapperTest {

    @Test
    @DisplayName("Mapea un objeto Book válido a BookDTO correctamente")
    void bookToDTO_ValidBook_ReturnsBookDTO() throws BuildException {
        Book book = Book.getInstance(
            1, "Clean Code", 45.0, true, "Robert C. Martin", 
            "2008-08-01", "2024-01-01", 1.0, 15.0, 20.0, 3.0, false
        );

        BookDTO dto = BookMapper.BookToDTO(book);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Clean Code", dto.getName());
        assertEquals(45.0, dto.getPrice());
        assertTrue(dto.isAvailable());
        assertEquals("Robert C. Martin", dto.getAuthor());
        assertEquals("2008-08-01", dto.getPublicationDate());
        assertEquals(1.0, dto.getWeight());
        assertEquals(15.0, dto.getWidth());
        assertFalse(dto.getIsFragile());
    }

    @Test
    @DisplayName("Mapea un BookDTO válido a objeto Book correctamente")
    void dtoToBook_ValidDTO_ReturnsBook() throws BuildException {
        BookDTO dto = new BookDTO(
            2, "The Pragmatic Programmer", 50.0, true, "Andrew Hunt", 
            "1999-10-30", "2024-01-01", 0.8, 14.0, 21.0, 4.0, true
        );

        Book book = BookMapper.DTOToBook(dto);

        assertNotNull(book);
        assertEquals(2, book.getId());
        assertEquals("The Pragmatic Programmer", book.getName());
        assertEquals(0.8, book.getWeight());
        assertTrue(book.getIsFragile());
    }

    @Test
    @DisplayName("Lanza NullPointerException al intentar mapear un Book nulo a DTO")
    void bookToDTO_NullBook_ThrowsException() {
        assertThrows(NullPointerException.class, () -> BookMapper.BookToDTO(null));
    }

    @Test
    @DisplayName("Lanza NullPointerException al intentar mapear un DTO nulo a Book")
    void dtoToBook_NullDTO_ThrowsException() {
        assertThrows(NullPointerException.class, () -> BookMapper.DTOToBook(null));
    }

    @Test
    @DisplayName("Lanza BuildException si el DTO contiene datos que violan las reglas de negocio")
    void dtoToBook_InvalidDTOData_ThrowsBuildException() {
        BookDTO invalidDto = new BookDTO(
            -1, "A", -10.0, true, "B", 
            "fecha-invalida", "2024-01-01", -1.0, -1.0, -1.0, -1.0, false
        );

        BuildException ex = assertThrows(BuildException.class, () -> BookMapper.DTOToBook(invalidDto));
        
        String msg = ex.getMessage();
        assertTrue(msg.contains("Bad Id"));
        assertTrue(msg.contains("Bad Name"));
        assertTrue(msg.contains("Bad Price"));
        assertTrue(msg.contains("Bad Author"));
        assertTrue(msg.contains("Bad Publication Date"));
        assertTrue(msg.contains("Bad PhysicalData"));
    }

    @Test
    @DisplayName("Mapea correctamente los valores booleanos de disponibilidad y fragilidad")
    void mapping_BooleanValues_CorrectlyTransferred() throws BuildException {
        Book book = Book.getInstance(1, "Test", 10.0, false, "Author", "2000-01-01", "2000-01-01", 1, 1, 1, 1, true);
        
        BookDTO dto = BookMapper.BookToDTO(book);
        assertFalse(dto.isAvailable());
        assertTrue(dto.getIsFragile());

        Book mappedBack = BookMapper.DTOToBook(dto);
        assertFalse(mappedBack.isAvailable());
        assertTrue(mappedBack.getIsFragile());
    }
}