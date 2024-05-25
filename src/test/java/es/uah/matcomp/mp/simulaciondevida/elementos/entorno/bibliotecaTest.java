package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.*;

class bibliotecaTest {

    @Test
    void getIncrementoProbClonacion() {
        biblioteca b = new biblioteca();
        b.setIncrementoProbClonacion(3);
        assertDoesNotThrow(()->b.getIncrementoProbClonacion());
        assertEquals(3, b.getIncrementoProbClonacion(), "El incremento no es correcto");
    }

    @Test
    void setIncrementoProbClonacion() {
        biblioteca b = new biblioteca();
        assertDoesNotThrow(()->b.setIncrementoProbClonacion(3));
        assertEquals(3, b.getIncrementoProbClonacion(), "El incremento no es correcto");
        assertThrows(probabilidadInvalidaException.class, ()->b.setIncrementoProbClonacion(-1));
        assertThrows(probabilidadInvalidaException.class, ()->b.setIncrementoProbClonacion(101));
    }

}