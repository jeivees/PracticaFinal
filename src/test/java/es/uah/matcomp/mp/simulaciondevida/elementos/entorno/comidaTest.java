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

}