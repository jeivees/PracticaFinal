package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

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
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        comida c = new comida();
        c.setIncrementoTV(10);
        assertDoesNotThrow(()->c.aplicarMejora(i));
        assertEquals(12, i.getTiempoDeVida(), "El incremento no es correcto");
    }
}