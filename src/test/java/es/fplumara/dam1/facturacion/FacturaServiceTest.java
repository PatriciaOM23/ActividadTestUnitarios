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
    private FacturaService facturaService;
    @Mock
    private Calculadora calculadora;




    @BeforeEach
    public void setUp() {
        facturaService = new FacturaService(calculadora);
    }
    @Test
    @DisplayName("Que totalConIva(100) devuelve 121 ")
    public void comprobarTotalConIva(){
    //DEVUELVE 121 -- COMPORTAMIENTO
        when(calculadora.sumar(100, 21)).thenReturn(121);
        int resultadoEsperado = facturaService.totalConIva(100);
        assertEquals(121,resultadoEsperado);
        verify(calculadora, times(1)).sumar(100, 21);

      // DEVUELVE 21
         when(calculadora.sumar(0,21)).thenReturn(21);
          int base2 = facturaService.totalConIva(0);
         assertEquals(21,calculadora.sumar(0,21));
         verify(calculadora, times(2)).sumar(0, 21);




        //verifyNoInteractions(calculadora);
    }


}
