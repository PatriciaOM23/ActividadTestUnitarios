package es.fplumara.dam1.operaciones;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OperacionesTest {
    @ParameterizedTest
    @CsvSource ({
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
     void comprobarNotas(double nota, String esperado){
        String resultado = Operaciones.calificacion(nota);
        assertEquals(esperado,resultado);
    }

    @ParameterizedTest
    @CsvSource ({
            "-0.01",
            "10.01"
    })

    @DisplayName("Comprueba si lanza la excepción al poner una nota que no entra en el rango 0-10")
    void comprobarNotaNoValida(double notaNoValida){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Operaciones.calificacion(notaNoValida);
        });
    }
}
