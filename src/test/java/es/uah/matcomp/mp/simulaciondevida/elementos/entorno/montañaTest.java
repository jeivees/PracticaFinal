package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.DataModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class montañaTest {
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

}
