package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class aguaTest {

    @Test
    void getIncrementoTV() {
        agua a = new agua();
        a.setIncrementoTV(2);
        assertDoesNotThrow(()->a.getIncrementoTV());
        assertEquals(2, a.getIncrementoTV(), "El incremento no es correcto");
    }

    @Test
    void setIncrementoTV() {
        agua a = new agua();
        assertDoesNotThrow(()->a.setIncrementoTV(2));
        assertEquals(2, a.getIncrementoTV(), "El incremento no es correcto");
    }
/**
    @Test
    void aplicarMejora() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        agua a = new agua();
        a.setIncrementoTV(2);
        assertDoesNotThrow(()->a.aplicarMejora(i));
        assertEquals(4, i.getTiempoDeVida(), "El incremento no es correcto");
    }**/
}