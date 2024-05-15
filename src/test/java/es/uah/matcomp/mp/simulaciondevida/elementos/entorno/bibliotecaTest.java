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

    @Test
    void aplicarMejora() {
        individuoBasico i = new individuoBasico(2,2,2,2,85);
        biblioteca b = new biblioteca();
        b.setIncrementoProbClonacion(10);
        DataModel model = new DataModel(
                10, 50, 10, 50,25,
                5,15,20,20,20,
                10,10,10,3,5,
                7, 25, 10, 10, 10, 0);
        tablero tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero(), model);

        casillaTablero casilla = new casillaTablero(i.getPosicionX(), i.getPosicionY(), model, tablero);

        assertDoesNotThrow(()->b.aplicarMejora(i, casilla));
        assertEquals(95, i.getProbClonacion(), "El incremento no es correcto");
        assertEquals("individuoBasico", i.getTipo(), "El tipo no es correcto");
        assertDoesNotThrow(()->b.aplicarMejora(i, casilla));
        assertEquals(100, i.getProbClonacion(), "El incremento no es correcto");
        assertEquals("individuoBasico", i.getTipo(), "EL tipo no es correcto");
    }
}