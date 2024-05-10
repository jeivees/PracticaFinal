package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

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
        m.setIncrementoTV(-2);
        assertDoesNotThrow(()->m.aplicarMejora(i));
        assertEquals(1, i.getTiempoDeVida(), "El incremento no es correcto");
    }
}