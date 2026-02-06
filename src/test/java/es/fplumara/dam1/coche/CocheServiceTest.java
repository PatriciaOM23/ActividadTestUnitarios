package es.fplumara.dam1.coche;

import es.fplumara.dam1.operaciones.Operaciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
public class CocheServiceTest {
        @Mock
        CocheRepository cocheRepository;
        @InjectMocks
        CocheService cocheService;

    @ParameterizedTest
        @ValueSource (strings = {"1234ABC", "0000ZZZ", "9876QWE"})
        @DisplayName("Matriculas devuelve true")
        void comprobarMatriculasTrue(String matricula){
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
    @DisplayName("MATRICULAS INVALIDAS DA FALSE")
        void comprobarMatriculasInvalidas(String matriculas){
        assertFalse(cocheService.validaMatricula(matriculas));
    }

    @Test
        void comprarCocheGuardaCoche(){
        //podemos generar un constructor
        //Coche coche = new Coche("1234ABC","Audi");
        Coche coche = new Coche();
        coche.setMatricula("1234ABC");
        coche.setMarca("Audi");
        assertDoesNotThrow(() -> cocheService.comprarCoche(coche));
        verify(cocheRepository,times(1)).save(coche);

        }
        @Test
        @DisplayName("comprarCoche lanza excepción si la matrícula es inválida")
        void comprarCocheLanzaExcepcion(){
            Coche coche = new Coche();
            coche.setMatricula("123-VD");
            assertThrows(IllegalArgumentException.class, () -> {
                cocheService.comprarCoche(coche);
            });
            verifyNoMoreInteractions(cocheRepository);
        }

        @Test
        @DisplayName("buscarCoche devuelve lo que devuelve el repositorio")
        void buscarCocheDevuelveInfoRepositorio (){

            Coche coche = new Coche();
            coche.setMatricula("1234ABC");
            when(cocheRepository.findByMatricula(coche.getMatricula())) .thenReturn(coche);
            Coche resultado = cocheService.buscarCoche(coche.getMatricula());
            assertEquals(coche, resultado);
            verify(cocheRepository,times(1)).findByMatricula(coche.getMatricula());
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
