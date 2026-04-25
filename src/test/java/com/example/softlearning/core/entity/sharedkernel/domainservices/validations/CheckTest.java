package com.example.softlearning.core.entity.sharedkernel.domainservices.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CheckTest {
    @Test
    @DisplayName("checkString debe retornar true para una cadena válida")
    void checkString_ValidString_ReturnsTrue() {
        assertTrue(Check.checkString("Hola"));
        assertTrue(Check.checkString(" Hola Mundo "));
    }
    
    @Test
    @DisplayName("checkString debe retornar false para nulos, vacíos o solo espacios")
    void checkString_InvalidStrings_ReturnsFalse() {
        assertFalse(Check.checkString(null), "Falla con null");
        assertFalse(Check.checkString(""), "Falla con cadena vacía");
        assertFalse(Check.checkString("   "), "Falla con cadena de solo espacios");
    }

    @Test
    @DisplayName("minStringChars debe retornar true si la longitud es mayor o igual al mínimo")
    void minStringChars_LengthMeetsMinimum_ReturnsTrue() {
        assertTrue(Check.minStringChars("Hola", 3.0));
        assertTrue(Check.minStringChars("Hola", 4.0));
    }

    @Test
    @DisplayName("minStringChars debe retornar false si la longitud es menor al mínimo o la cadena es inválida")
    void minStringChars_LengthBelowMinimum_ReturnsFalse() {
        assertFalse(Check.minStringChars("Hola", 5.0));
        assertFalse(Check.minStringChars("   ", 1.0));
    }

    @Test
    @DisplayName("maxStringChars debe retornar true si la longitud es menor o igual al máximo")
    void maxStringChars_LengthMeetsMaximum_ReturnsTrue() {
        assertTrue(Check.maxStringChars("Hola", 5.0));
        assertTrue(Check.maxStringChars("Hola", 4.0));
    }

    @Test
    @DisplayName("maxStringChars debe retornar false si la longitud es mayor al máximo o la cadena es inválida")
    void maxStringChars_LengthAboveMaximum_ReturnsFalse() {
        assertFalse(Check.maxStringChars("Hola Mundo", 5.0));
        assertFalse(Check.maxStringChars(null, 10.0));
    }

    @Test
    @DisplayName("isValidString debe retornar true si la longitud está dentro del rango")
    void isValidString_WithinRange_ReturnsTrue() {
        assertTrue(Check.isValidString("Hola", 2.0, 6.0));
        assertTrue(Check.isValidString("Hola", 4.0, 4.0));
    }

    @Test
    @DisplayName("isValidString debe retornar false si la longitud está fuera del rango")
    void isValidString_OutRange_ReturnsFalse() {
        assertFalse(Check.isValidString("Hola", 5.0, 10.0));
        assertFalse(Check.isValidString("Hola Mundo", 2.0, 5.0));
    }

    @Test
    @DisplayName("minValue debe validar correctamente si el valor es mayor o igual al mínimo")
    void minValue_ValidatesCorrectly() {
        assertTrue(Check.minValue(10.5, 5.0));
        assertTrue(Check.minValue(5.0, 5.0));
        assertFalse(Check.minValue(4.9, 5.0));
    }

    @Test
    @DisplayName("maxValue debe validar correctamente si el valor es menor o igual al máximo")
    void maxValue_ValidatesCorrectly() {
        assertTrue(Check.maxValue(5.0, 10.5));
        assertTrue(Check.maxValue(10.5, 10.5));
        assertFalse(Check.maxValue(10.6, 10.5));
    }

    @Test
    @DisplayName("isValidValue debe validar si un número está dentro del rango especificado")
    void isValidValue_ValidatesCorrectly() {
       
        assertTrue(Check.isValidValue(5.0, 1.0, 10.0));
        assertTrue(Check.isValidValue(1.0, 1.0, 10.0));
        assertTrue(Check.isValidValue(10.0, 1.0, 10.0));

        assertFalse(Check.isValidValue(0.9, 1.0, 10.0));
        assertFalse(Check.isValidValue(10.1, 1.0, 10.0));
    }
}
