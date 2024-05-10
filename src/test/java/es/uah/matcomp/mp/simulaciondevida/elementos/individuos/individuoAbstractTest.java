package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import excepciones.probabilidadInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class individuoAbstractTest {

    @Test
    void getPosicionX() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        i.setPosicionX(2);
        assertDoesNotThrow(()->i.getPosicionX());
        assertEquals(2, i.getPosicionX(), "El parámetro no es correcto");
    }

    @Test
    void setPosicionX() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setPosicionX(3));
        assertEquals(3, i.getPosicionX(), "El parámetro no es correcto");
    }

    @Test
    void getPosicionY() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        i.setPosicionY(2);
        assertDoesNotThrow(()->i.getPosicionY());
        assertEquals(2, i.getPosicionY(), "El parámetro no es correcto");
    }

    @Test
    void setPosicionY() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setPosicionY(3));
        assertEquals(3, i.getPosicionY(), "El parámetro no es correcto");
    }

    @Test
    void getPosicion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        i.setPosicionX(4);
        i.setPosicionY(5);
        int [] a= new int[2];
        a[0] = 4;
        a[1] = 5;
        assertDoesNotThrow(()->i.getPosicion());
        assertArrayEquals(i.getPosicion(), a, "La posición no es correcta");
    }

    @Test
    void getId() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.getId());
        assertEquals(2, i.getId(), "El parámetro no es correcto");
    }

    @Test
    void setId() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setId(3));
        assertEquals(3, i.getId(), "El parámetro no es correcto");
    }

    @Test
    void getGeneracion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.getGeneracion());
        assertEquals(2, i.getGeneracion(), "El parámetro no es correcto");
    }

    @Test
    void setGeneracion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setGeneracion(3));
        assertEquals(3, i.getGeneracion(), "El parámetro no es correcto");
    }

    @Test
    void getTiempoDeVida() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.getTiempoDeVida());
        assertEquals(2, i.getTiempoDeVida(), "El parámetro no es correcto");
    }

    @Test
    void setTiempoDeVida() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setTiempoDeVida(3));
        assertEquals(3, i.getTiempoDeVida(), "El parámetro no es correcto");
    }

    @Test
    void getProbReproduccion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.getProbReproduccion());
        assertEquals(2, i.getProbReproduccion(), "El parámetro no es correcto");
        assertEquals(98, i.getProbMuerte(), "El parámetro no es correcto");
    }

    @Test
    void setProbReproduccion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setProbReproduccion(3));
        assertEquals(3, i.getProbReproduccion(), "El parámetro no es correcto");
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbReproduccion(-1));
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbReproduccion(101));
    }

    @Test
    void getProbClonacion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.getProbClonacion());
        assertEquals(2, i.getProbClonacion(), "El parámetro no es correcto");
    }

    @Test
    void setProbClonacion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setProbClonacion(3));
        assertEquals(3, i.getProbClonacion(), "El parámetro no es correcto");
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbClonacion(-1));
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbClonacion(101));
    }

    @Test
    void getProbMuerte() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.setProbReproduccion(20));
        assertEquals(80, i.getProbMuerte(), "El parámetro no es correcto");
    }

    @Test
    void getTipo() {
    }

    @Test
    void setTipo() {
    }

    @Test
    void reproducirse() {
    }

    @Test
    void clonarse() {
    }

    @Test
    void morir() {
    }

    @Test
    void actualizarTV() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        assertDoesNotThrow(()->i.actualizarTV());
        assertEquals(1, i.getTiempoDeVida(), "El parámetro no es correcto");
    }

    @Test
    void mover() {
    }

    @Test
    void moverAleatorio() {
        individuoBasico i = new individuoBasico(2,2,2,2,2);
        i.setPosicionX(2);
        i.setPosicionY(2);
        assertDoesNotThrow(()->i.moverAleatorio());
        assertTrue(0<i.getPosicionX() && 4>i.getPosicionX() && 0<i.getPosicionY() && 4>i.getPosicionY());
        assertTrue((i.getPosicionX()==1 || i.getPosicionX()==3)!=(i.getPosicionY()==1 || i.getPosicionY()==3));
    }

    @Test
    void mejorar() {
    }
}