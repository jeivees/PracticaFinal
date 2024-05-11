package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import excepciones.probabilidadInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class tesoroTest {

    @Test
    void getIncrementoProbReproduccion() {
        tesoro t = new tesoro();
        t.setIncrementoProbReproduccion(3);
        assertDoesNotThrow(()->t.getIncrementoProbReproduccion());
        assertEquals(3, t.getIncrementoProbReproduccion(), "El incremento no es correcto");
    }

    @Test
    void setIncrementoProbReproduccion() {
        tesoro t = new tesoro();
        assertDoesNotThrow(()->t.setIncrementoProbReproduccion(3));
        assertEquals(3, t.getIncrementoProbReproduccion(), "El incremento no es correcto");
        assertThrows(probabilidadInvalidaException.class, ()->t.setIncrementoProbReproduccion(-1));
        assertThrows(probabilidadInvalidaException.class, ()->t.setIncrementoProbReproduccion(101));
    }

    @Test
    void aplicarMejora() {
        individuoBasico i = new individuoBasico(2,2,2,85,2);
        tesoro t = new tesoro();
        t.setIncrementoProbReproduccion(10);
        assertDoesNotThrow(()->t.aplicarMejora(i));
        assertEquals(95, i.getProbReproduccion(), "El incremento no es correcto");
        assertEquals("individuoBasico", i.getTipo(), "El tipo no es correcto");
        assertDoesNotThrow(()->t.aplicarMejora(i));
        assertEquals(100, i.getProbReproduccion(), "El incremento no es correcto");
        assertEquals("individuoBasico", i.getTipo(), "EL tipo no es correcto");
    }
}