package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import gui.mvc.javafx.practicafinal.configuracionDataModel;
import gui.mvc.javafx.practicafinal.configuracionDataModelProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class recursoTest {

    @Test
    void getPosicionX() {
        agua a = new agua ();
        a.setPosicionX(2);
        assertDoesNotThrow(()->a.getPosicionX());
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
        assertDoesNotThrow(()->a.getPosicionY());
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
        assertDoesNotThrow(()->a.getPosicion());
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
        assertDoesNotThrow(()->a.getTiempoDeAparicion());
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
        configuracionDataModel model = new configuracionDataModel(5, 50, 30,
                10,20,20,20,10,10,10,
                1,3,2,15,10,
                10, 10, 10, 10, 0);
        casillaTablero casilla = new casillaTablero(0,0, model);
        agua a = new agua ();
        a.setTiempoDeAparicion(3);
        assertDoesNotThrow(()->a.actualizarTA(model, casilla));
        assertEquals(2, a.getTiempoDeAparicion(), "Tiempo incorrecto");
    }

    @Test
    void aplicarMejora() {
    }
}