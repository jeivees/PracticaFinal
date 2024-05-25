package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.*;

class individuoNormalTest {

    @Test
    void getTipo() {
        individuoNormal i = new individuoNormal(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getTipo());
        assertEquals(i.getClass(), i.getTipo());
    }
}