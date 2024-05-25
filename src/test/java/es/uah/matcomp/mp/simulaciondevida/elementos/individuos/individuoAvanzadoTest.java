package es.uah.matcomp.mp.simulaciondevida.elementos.individuos; 

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class individuoAvanzadoTest {

    @Test
    void getTipo() {
        individuoAvanzado i = new individuoAvanzado(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getTipo());
        assertEquals("individuoAvanzado", i.getTipo());
    }
}