package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;
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

}