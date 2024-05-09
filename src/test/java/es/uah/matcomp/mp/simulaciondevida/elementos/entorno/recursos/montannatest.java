package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.*;

class montannaTest {

    @Test
    void getIncrementoTV() {
        montanna m = new montanna();
        m.setIncrementoTV(-2);
        assertDoesNotThrow(()->m.getIncrementoTV());
        assertEquals(-2,m.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void setIncrementoTV() {
        montanna m = new montanna();
        assertDoesNotThrow(()->m.setIncrementoTV(-2));
        assertEquals(-2,m.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void aplicarMejora() {
        individuoBasico i = new individuoBasico(2,2,3,2,2);
        montanna m = new montanna();
        m.setIncrementoTV(-2);
        assertDoesNotThrow(()->m.aplicarMejora(i));
        assertEquals(1, i.getTiempoDeVida(), "El incremento no es correcto");
    }
}