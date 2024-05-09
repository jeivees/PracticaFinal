package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.agua;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.comida;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recursoAbstract;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class individuoAvanzadoTest {

    @Test
    void getTipo() {
        individuoAvanzado i = new individuoAvanzado(2,2,2,2,2);
        assertDoesNotThrow(()->i.getTipo());
        assertEquals("individuoAvanzado", i.getTipo());
    }

   /** @Test
    void mover() {
        individuoNormal i = new individuoNormal(2,2,2,2,2);
        i.setPosicionX(2);
        i.setPosicionY(2);
        ListaDE<recursoAbstract> list = new ListaDE<>();
        assertDoesNotThrow(()->i.mover(list));
        assertTrue(0<i.getPosicionX() && 4>i.getPosicionX() && 0<i.getPosicionY() && 4>i.getPosicionY());
        assertTrue((i.getPosicionX()==1 || i.getPosicionX()==3)!=(i.getPosicionY()==1 || i.getPosicionY()==3));
        agua a = new agua();
        a.setPosicionX(10);
        a.setPosicionY(9);
        list.add(a);
        i.setPosicionX(2);
        i.setPosicionY(2);
        assertDoesNotThrow(()->i.mover(list));
        assertEquals(3, i.getPosicionX());
        i.mover(list);
        assertEquals(3, i.getPosicionX());
        assertEquals(3, i.getPosicionY());
        i.mover(list);
        assertEquals(4, i.getPosicionX());
        comida c = new comida();
        c.setPosicionX(0);
        c.setPosicionY(0);
        list.add(c);
        i.mover(list);
        assertEquals(3, i.getPosicionX());
        i.mover(list);
        assertEquals(2, i.getPosicionX());
    }**/
}