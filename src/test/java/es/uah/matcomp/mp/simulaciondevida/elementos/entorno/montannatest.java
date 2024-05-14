package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.DataModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.*;

class montañaTest {

    @Test
    void getIncrementoTV() {
        montaña m = new montaña();
        m.setIncrementoTV(-2);
        assertDoesNotThrow(()->m.getIncrementoTV());
        assertEquals(-2,m.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void setIncrementoTV() {
        montaña m = new montaña();
        assertDoesNotThrow(()->m.setIncrementoTV(-2));
        assertEquals(-2,m.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void aplicarMejora() {
        individuoBasico i = new individuoBasico(2,2,3,2,2);
        montaña m = new montaña();
        m.setIncrementoTV(2);
        DataModel model = new DataModel(
                10, 50, 10, 50,25,
                5,15,20,20,20,
                10,10,10,3,5,
                7, 25, 10, 10, 10, 0);
        tablero tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero(), model);

        casillaTablero casilla = new casillaTablero(i.getPosicionX(), i.getPosicionY(), model, tablero);
        assertDoesNotThrow(()->m.aplicarMejora(i, casilla));
        assertEquals(1, i.getTiempoDeVida(), "El incremento no es correcto");
    }
}