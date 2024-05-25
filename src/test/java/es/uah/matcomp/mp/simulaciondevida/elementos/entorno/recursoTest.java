package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.DataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class recursoTest {

    @Test
    void getPosicionX() {
        agua a = new agua ();
        a.setPosicionX(2);
        Assertions.assertDoesNotThrow(()->a.getPosicionX());
        assertEquals(2, a.getPosicionX(), "Posición incorrecta");
    }

    @Test
    void setPosicionX() {
        agua a = new agua ();
        assertDoesNotThrow(()->a.setPosicionX(2));
        assertEquals(2, a.getPosicionX(), "Posición incorrecta");
    }

    @Test
    void getPosicionY() {
        agua a = new agua ();
        a.setPosicionY(2);
        Assertions.assertDoesNotThrow(()->a.getPosicionY());
        assertEquals(2, a.getPosicionY(), "Posición incorrecta");
    }

    @Test
    void setPosicionY() {
        agua a = new agua ();
        assertDoesNotThrow(()->a.setPosicionY(2));
        assertEquals(2, a.getPosicionY(), "Posición incorrecta");
    }

    @Test
    void getPosicion() {
        agua a = new agua ();
        a.setPosicionY(5);
        a.setPosicionX(4);
        int [] array= new int[2];
        array[0] = 4;
        array[1] = 5;
        Assertions.assertDoesNotThrow(()->a.getPosicion());
        assertArrayEquals(array, a.getPosicion(), "La posición no es correcta");
    }

    @Test
    void setPosicion() {
        agua a = new agua ();
        assertDoesNotThrow(()->a.setPosicionY(5));
        assertDoesNotThrow(()->a.setPosicionX(4));
        int [] array= new int[2];
        array[0] = 4;
        array[1] = 5;
        assertArrayEquals(array, a.getPosicion(), "La posición no es correcta");
    }

    @Test
    void getTiempoDeAparicion() {
        agua a = new agua ();
        a.setTiempoDeAparicion(3);
        Assertions.assertDoesNotThrow(()->a.getTiempoDeAparicion());
        assertEquals(3, a.getTiempoDeAparicion(), "Tiempo incorrecto");
    }

    @Test
    void setTiempoDeAparicion() {
        agua a = new agua ();
        assertDoesNotThrow(()->a.setTiempoDeAparicion(3));
        assertEquals(3, a.getTiempoDeAparicion(), "Tiempo incorrecto");
    }

    @Test
    void eliminar() {
    }

    @Test
    void actualizarTA() {
        DataModel model = new DataModel(10, 50, 10, 50,25,
                5,15,20,20,20,
                10,10,10,3,5,
                7, 25, 10, 10, 10, 0);
        tablero tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero(), model);
    }

    @Test
    void aplicarMejora() {
    }
}