package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.*;
import javafx.application.Platform;


class individuoTest {
    @Test
    void getPosicionX() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        i.setPosicionX(2);
        assertDoesNotThrow(()->i.getPosicionX());
        assertEquals(2, i.getPosicionX(), "El parámetro no es correcto");
    }

    @Test
    void setPosicionX() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setPosicionX(3));
        assertEquals(3, i.getPosicionX(), "El parámetro no es correcto");
    }

    @Test
    void getPosicionY() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        i.setPosicionY(2);
        assertDoesNotThrow(()->i.getPosicionY());
        assertEquals(2, i.getPosicionY(), "El parámetro no es correcto");
    }

    @Test
    void setPosicionY() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setPosicionY(3));
        assertEquals(3, i.getPosicionY(), "El parámetro no es correcto");
    }

    @Test
    void getPosicion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        i.setPosicionX(4);
        i.setPosicionY(5);
        int [] a= new int[2];
        a[0] = 4;
        a[1] = 5;
        assertDoesNotThrow(()->i.getPosicion());
        assertArrayEquals(i.getPosicion(), a, "La posición no es correcta");
    }

    @Test
    void setPosicion() {
    }

    @Test
    void getId() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getId());
        assertEquals(2, i.getId(), "El parámetro no es correcto");
    }

    @Test
    void setId() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setId(3));
        assertEquals(3, i.getId(), "El parámetro no es correcto");
    }

    @Test
    void getGeneracion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getGeneracion());
        assertEquals(2, i.getGeneracion(), "El parámetro no es correcto");
    }

    @Test
    void setGeneracion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setGeneracion(3));
        assertEquals(3, i.getGeneracion(), "El parámetro no es correcto");
    }

    @Test
    void getTiempoDeVida() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getTiempoDeVida());
        assertEquals(2, i.getTiempoDeVida(), "El parámetro no es correcto");
    }

    @Test
    void setTiempoDeVida() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setTiempoDeVida(3, 1));
        assertEquals(3, i.getTiempoDeVida(), "El parámetro no es correcto");
    }

    @Test
    void getTiempoDeVidaProperty() {
    }

    @Test
    void getProbReproduccion() {
        individuoBasico i = new individuoBasico(2, 2, 2,2, 2, 1);
        assertDoesNotThrow(()->i.getProbReproduccion());
        assertEquals(2, i.getProbReproduccion(), "El parámetro no es correcto");
        assertEquals(98, i.getProbMuerte(), "El parámetro no es correcto");
    }
    @Test
    void setProbReproduccion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setProbReproduccion(3, 1));
        assertEquals(3, i.getProbReproduccion(), "El parámetro no es correcto");
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbReproduccion(-1, 1));
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbReproduccion(101, 1));
    }

    @Test
    void getProbClonacion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.getProbClonacion());
        assertEquals(2, i.getProbClonacion(), "El parámetro no es correcto");
    }

    @Test
    void setProbClonacion() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setProbClonacion(3, 1));
        assertEquals(3, i.getProbClonacion(), "El parámetro no es correcto");
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbClonacion(-1, 1));
        assertThrows(probabilidadInvalidaException.class, ()->i.setProbClonacion(101, 1));
    }

    @Test
    void getProbMuerte() {
        individuoBasico i = new individuoBasico(2,2,2,2,2, 1);
        assertDoesNotThrow(()->i.setProbReproduccion(20, 1));
        assertEquals(80, i.getProbMuerte(), "El parámetro no es correcto");
    }
    

    @Test
    void getGradoTipo() {
        individuoBasico i1= new individuoBasico();
        assertDoesNotThrow(()->i1.getGradoTipo());
        assertEquals(0, i1.getGradoTipo(), "El grado no es correcto");
        individuoNormal i2 = new individuoNormal();
        assertEquals(1, i2.getGradoTipo(), "El grado no es correcto");
        individuoAvanzado i3 = new individuoAvanzado();
        assertEquals(2, i3.getGradoTipo(), "El grado no es correcto");
    }
    @Test

    void reproducirse () {
        individuoBasico i = new individuoBasico();
        individuoBasico pareja = new individuoBasico();
        DataModel model = new DataModel(2,2,
                2,2,2,
                2,2,2,
                2,2,2,
                2,2,2,
                2,2,2,
                2,2, 2, 2);
        tablero tablero = new tablero(0,0, model);
        casillaTablero casilla = new casillaTablero(0,0, model, tablero);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(casilla);
        Scene scene = new Scene(root, 800, 600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ejemplo de casillaTablero");
        primaryStage.show();
    }

    @Test
    void clonarse() {
    }

    @Test
    void añadir() {
    }

    @Test
    void morir() {
    }

    @Test
    void isVivo() {
        individuoBasico i = new individuoBasico(1,1,1,1,1,1);
        assertDoesNotThrow(()->i.isVivo());
        assertEquals(true, i.isVivo());
        assertEquals(false, i.isVivo());
    }

    @Test
    void actualizarTV() {
    }

    @Test
    void mover() {
    }

    @Test
    void moverAleatorio() {
        individuoBasico i = new individuoBasico();
        DataModel model = new DataModel(2,2,2,
                2,2,2,
                2,2,2,
                2,2,2,2,2,
                2,2,2,2,
                2,2,2);
        tablero tablero = new tablero(2,2, model);
        assertDoesNotThrow(()->i.moverAleatorio(tablero,1));
    }

    @Test
    void cambiarPosicion() {

    }

    @Test
    void addAccion() {
    }

    @Test
    void getPadres() {
    }

    @Test
    void setPadres() {
    }

    @Test
    void testSetPadres() {
    }
}