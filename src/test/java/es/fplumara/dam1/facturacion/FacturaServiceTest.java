package es.fplumara.dam1.facturacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FacturaServiceTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    private Calculadora calculadora;

    private FacturaService facturaService;

    @BeforeEach
    public void setUp() {
      facturaService = new FacturaService(calculadora);
    }

    @Test
    @DisplayName("Que totalConIva(100) devuelve 121 ")
    void comprobarTotalConIva(){
    //DEVUELVE 121 -- COMPORTAMIENTO
        when(calculadora.sumar(100, 21)).thenReturn(121);
        when(calculadora.sumar(0,21)).thenReturn(21);


        int resultadoEsperado = facturaService.totalConIva(100);
        int base2 = facturaService.totalConIva(0);
        assertEquals(121,resultadoEsperado);
        assertEquals(21,base2);


        verify(calculadora).sumar(100, 21);
        verify(calculadora).sumar(0, 21);

        verifyNoMoreInteractions(calculadora);
    }


}
