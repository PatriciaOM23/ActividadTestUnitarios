package es.fplumara.dam1.operaciones;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OperacionesTest {
    @ParameterizedTest
    @CsvSource({
            "0.0, 'INSUFICIENTE'",
            "4.99, 'INSUFICIENTE'",
            "5.0, 'APROBADO'",
            "6.99, 'APROBAD0'",
            "7.0, 'NOTABLE'",
            "8.99, 'NOTABLE'",
            "9.0, 'SOBRESALIENTE'",
            "10.0, 'SOBRESALIENTE'"
    })
    @DisplayName("Comprueba si da el texto correcto al recibir una nota dentro de su rango")
    void comprobarNotas(double nota, String esperado) {
        String resultado = Operaciones.calificacion(nota);
        assertEquals(esperado, resultado);
    }

    @ParameterizedTest
    @CsvSource({
            "-0.01",
            "10.01"
    })

    @DisplayName("Comprueba si lanza la excepción al poner una nota que no entra en el rango 0-10")
    void comprobarNotaNoValida(double notaNoValida) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Operaciones.calificacion(notaNoValida);
        });
    }


    @ParameterizedTest
    @MethodSource("medias")
    @DisplayName("Comprobar que hace la media")
     void comprobarMedia(double esperado,double... notas){
        double resultado = Operaciones.media(notas);
        assertEquals(esperado, resultado,0.0001);
    }

    static Stream <Arguments> medias(){
       return Stream.of(
               Arguments.of(3.5, new double[]{3.0,4.0}),
               Arguments.of(7.5, new double[] {8.0,7.0}),
               Arguments.of(10, new double[]{10}),
               Arguments.of(7.8, new double[]{7,8,8,8.2})
       );
    }

    @Test
    @DisplayName("Comprobar el método media")
    void testMediasConAssertAll() {
        assertAll(
                () -> assertEquals(6.0, Operaciones.media(5, 7)),
                () -> assertEquals(10.0, Operaciones.media(10)),
                () -> assertEquals(0.0, Operaciones.media(0, 0, 0))
        );
    }

    @Test
    @DisplayName("Comprobar IllegalArgumentException cuando llaman sin notas o se pasa null")
    void comprobarDatosNulos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Operaciones.media(null);
        });
    }
}
