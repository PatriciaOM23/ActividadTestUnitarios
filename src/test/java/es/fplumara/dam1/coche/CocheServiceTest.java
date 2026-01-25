package es.fplumara.dam1.coche;

import es.fplumara.dam1.operaciones.Operaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CocheServiceTest {
    @ExtendWith(MockitoExtension.class)
    private CocheService cocheService;
   @Mock
    private CocheRepository cocheRepository;
    @BeforeEach
    void setUp(){
        cocheService = new CocheService(cocheRepository);
    }
    @ParameterizedTest
    @ValueSource(strings = {"1234ABC", "0000ZZZ", "9876QWE"})
    @DisplayName("Comprobar matrículas válidas")
    void matriculasValidasNoNegativa(String matricula){
        assertTrue(cocheService.validaMatricula(matricula));

    }

    @ParameterizedTest
    @CsvSource (
            {
                    "123ABC",
                    "12345ABC",
                    "1234AB",
                    "1234A1C",
                    "1234-ABC",
                    "1234 ABC",
                    "1234abc"
            }
    )
    @DisplayName("Comprobar matrículas NO VÁLIDAS")
    void matriculasInvalidasComprobarFalse(String matriculas){
        assertFalse(cocheService.validaMatricula(matriculas));
    }

    @Test
    @DisplayName("Comprobar CocheService usa bien CocheRepository y válida la matrícula con Mockito")
    void comprarCocheGuardaMatricula(){
        Coche coche = new Coche();
        coche.setMatricula("1234BDC");
        cocheService.comprarCoche(coche);
        verify(cocheRepository,times(1)).save(coche);
        assertDoesNotThrow(() -> cocheService.comprarCoche(coche));

        }


        @Test
    @DisplayName("comprarCoche lanza excepción si la matrícula es inválida")
    void comprarCocheLanzaExcepcion(){
        Coche coche = new Coche();
        coche.setMatricula("123-VD");
        assertThrows(IllegalArgumentException.class, () -> {
            cocheService.comprarCoche(coche);
        });
            verifyNoMoreInteractions(cocheService);
    }

    @Test
    @DisplayName("buscarCoche devuelve lo que devuelve el repositorio")
    void buscarCocheDevuelveInfoRepositorio (){
        String matricula = "1234ABC";
        Coche coche = new Coche();
        coche.setMatricula(matricula);
        when(cocheRepository.findByMatricula(matricula)) .thenReturn(coche);
        Coche resultado = cocheService.buscarCoche(matricula);
        assertEquals(coche, resultado);
        verify(cocheRepository,times(1)).findByMatricula(matricula);
        verifyNoMoreInteractions(cocheRepository);
    }


    @Test
    @DisplayName("buscarCoche lanza excepción si la matrícula es inválida")
    void buscarCocheInvalidoLanzaExcepcion(){
        String matricula = "123-2132";
        assertThrows(IllegalArgumentException.class, () -> {
            cocheService.buscarCoche("123-241"); });
        verifyNoInteractions(cocheRepository);
    }
    }
