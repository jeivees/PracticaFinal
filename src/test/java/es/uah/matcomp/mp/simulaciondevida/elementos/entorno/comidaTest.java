package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.DataModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.*;

class comidaTest {

    @Test
    void getIncrementoTV() {
        comida c = new comida();
        c.setIncrementoTV(10);
        assertDoesNotThrow(()->c.getIncrementoTV());
        assertEquals(10, c.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void setIncrementoTV() {
        comida c = new comida();
        assertDoesNotThrow(()->c.setIncrementoTV(10));
        assertEquals(10, c.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void aplicarMejora() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        comida c = new comida();
        c.setIncrementoTV(10);
        DataModel model = new DataModel(
                10, 50, 10, 50,25,
                5,15,20,20,20,
                10,10,10,3,5,
                7, 25, 10, 10, 10, 0);
        tablero tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero(), model);

        casillaTablero casilla = new casillaTablero(i.getPosicionX(), i.getPosicionY(), model, tablero);
        assertDoesNotThrow(()->c.aplicarMejora(i, casilla, 1));
        assertEquals(12, i.getTiempoDeVida(), "El incremento no es correcto");
    }
}