package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class individuoBasicoTest {

    @Test
    void getTipo() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.getTipo());
        assertEquals("individuoBasico", i.getTipo());
    }

    @Test
    void mover() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        i.setPosicionX(2);
        i.setPosicionY(2);
        assertDoesNotThrow(()->i.mover());
        assertTrue(0<i.getPosicionX() && 4>i.getPosicionX() && 0<i.getPosicionY() && 4>i.getPosicionY());
        assertTrue((i.getPosicionX()==1 || i.getPosicionX()==3)!=(i.getPosicionY()==1 || i.getPosicionY()==3));
    }
}