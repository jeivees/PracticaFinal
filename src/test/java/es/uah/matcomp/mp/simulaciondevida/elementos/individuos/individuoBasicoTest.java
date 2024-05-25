package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class individuoBasicoTest {

    @Test
    void getTipo() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getTipo());
        assertEquals(i.getClass(), i.getTipo());
    }
}