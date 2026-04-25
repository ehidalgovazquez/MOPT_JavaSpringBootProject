package com.example.softlearning.core.entity.sharedkernel.model.physicals.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

class PhysicalDataTest {
    @Test
    @DisplayName("Debe crear la instancia correctamente con parámetros numéricos válidos")
    void getInstance_ValidNumericParameters_ReturnsInstance() throws BuildException {
        PhysicalData data = PhysicalData.getInstance(10.5, 20.0, 30.0, 40.0, true);
        
        assertNotNull(data);
        assertEquals(10.5, data.getWeight());
        assertEquals(20.0, data.getWidth());
        assertEquals(30.0, data.getHeight());
        assertEquals(40.0, data.getDepth());
        assertTrue(data.getIsFragile());
    }

    @Test
    @DisplayName("Debe lanzar BuildException con mensaje detallado si los valores numéricos son negativos")
    void getInstance_InvalidNumericParameters_ThrowsBuildException() {
        BuildException ex1 = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(-5.0, 10.0, 10.0, 10.0, false)
        );
        assertEquals("Bad Weight", ex1.getMessage().trim());

        BuildException ex2 = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(10.0, -1.0, -2.0, -3.0, false)
        );
        assertEquals("Bad Width; Bad Height; Bad Depth", ex2.getMessage().trim());

        BuildException ex3 = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(-1.0, -1.0, -1.0, -1.0, true)
        );
        assertEquals("Bad Width; Bad Height; Bad Depth; Bad Weight", ex3.getMessage().trim());
    }

    @Test
    @DisplayName("Debe crear la instancia correctamente a partir de un String formateado")
    void getInstance_ValidString_ReturnsInstance() throws BuildException {
        String input = "width: 15.5 ; height: 20.0 ; depth: 5.0 ; weight: 2.5 ; isFragile: true";
        PhysicalData data = PhysicalData.getInstance(input);

        assertNotNull(data);
        assertEquals(15.5, data.getWidth());
        assertEquals(20.0, data.getHeight());
        assertEquals(5.0, data.getDepth());
        assertEquals(2.5, data.getWeight());
        assertTrue(data.getIsFragile());
    }

    @Test
    @DisplayName("Debe ignorar espacios en blanco y secciones vacías en el String")
    void getInstance_StringWithExtraSpacesAndEmptySections_ReturnsInstance() throws BuildException {
        String input = "  width:10;height:10;depth:10;;; weight:5; isFragile:false  ";
        PhysicalData data = PhysicalData.getInstance(input);
        
        assertNotNull(data);
        assertEquals(10.0, data.getWidth());
    }

    @Test
    @DisplayName("Debe lanzar BuildException si falta alguna clave obligatoria en el String")
    void getInstance_StringMissingKeys_ThrowsBuildException() {
        String input = "width: 15.5; height: 20.0; depth: 5.0; weight: 2.5";
        
        BuildException ex = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(input)
        );
        assertTrue(ex.getMessage().contains("Faltan claves:"));
        assertTrue(ex.getMessage().contains("isFragile"));
    }

    @Test
    @DisplayName("Debe lanzar BuildException si el String contiene una clave desconocida")
    void getInstance_StringUnknownKey_ThrowsBuildException() {
        String input = "width: 10; height: 10; depth: 10; weight: 10; isFragile: true; color: rojo";
        
        BuildException ex = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(input)
        );
        assertEquals("Clave desconocida: color", ex.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar BuildException si el formato del String no tiene ':'")
    void getInstance_StringInvalidFormat_ThrowsBuildException() {
        String input = "width=10; height:10; depth:10; weight:10; isFragile:true";
        
        BuildException ex = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(input)
        );
        assertTrue(ex.getMessage().contains("Formato inválido, falta ':'"));
    }

    @Test
    @DisplayName("Debe lanzar BuildException si los valores dentro del String correcto son negativos")
    void getInstance_StringValidFormatButNegativeValues_ThrowsBuildException() {
        String input = "width: -10; height: 10; depth: 10; weight: 10; isFragile: true";
        
        BuildException ex = assertThrows(BuildException.class, () -> 
            PhysicalData.getInstance(input)
        );
        assertEquals("Bad Width", ex.getMessage().trim());
    }

    @Test
    @DisplayName("Los getters de formateo de texto devuelven la estructura correcta")
    void gettersFormat_ReturnsCorrectStringFormat() throws BuildException {
        PhysicalData data = PhysicalData.getInstance(5.0, 1.0, 2.0, 3.0, true);

        String expectedDimensions = "width:1.0,height:2.0,depth:3.0";
        assertEquals(expectedDimensions, data.getDimensions());

        String expectedPhysicalData = "weight:5.0;height:2.0;width:1.0;depth:3.0;isFragile:true";
        assertEquals(expectedPhysicalData, data.getPhysicalData());
    }

    @Test
    @DisplayName("Setters individuales devuelven 0 si es válido y -1 si es inválido")
    void setters_ReturnCorrectStatus() {
        PhysicalData data = new PhysicalData();
        
        assertEquals(0, data.setWidth(10.0));
        assertEquals(-1, data.setWidth(-5.0));
        assertEquals(10.0, data.getWidth());
        
        assertEquals(0, data.setWeight(20.0));
        assertEquals(-1, data.setWeight(-1.0));
        assertEquals(20.0, data.getWeight());
    }
}