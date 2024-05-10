package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import excepciones.probabilidadInvalidaException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Random;

public abstract class individuo {
    private int posicionX;
    private int posicionY;
    private int id;
    private int generacion;
    private IntegerProperty TiempoDeVidaProperty = new SimpleIntegerProperty();
    private float probReproduccion;
    private float probClonacion;
    private float probMuerte;

    private static final Logger log = LogManager.getLogger("es.uah");

    public individuo(int I, int G, int T, float PR, float PC) {
        this.id = I;
        this.generacion = G;
        this.TiempoDeVidaProperty.set(T);
        if (PR < 0 || PR > 100 || PC < 0 || PC > 100) throw new probabilidadInvalidaException();
        this.probReproduccion = PR;
        this.probClonacion = PC;
        this.probMuerte = 1-PR;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public int[] getPosicion () {
        int[] posicion = new int[2];
        posicion[0] = posicionX;
        posicion[1] = posicionY;
        return posicion;
    }

    public void setPosicion (int[] pos) {
        posicionX = pos[0];
        posicionY = pos[1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        log.info("Id modificado");
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
        log.info("Generación modificada");
    }

    public IntegerProperty getTiempoDeVidaProperty() {
        return TiempoDeVidaProperty;
    }

    public int getTiempoDeVida() {
        return TiempoDeVidaProperty.get();
    }

    public void setTiempoDeVida(int tiempoDeVida) {
        TiempoDeVidaProperty.set(tiempoDeVida);
        log.info("Tiempo de vida modificado");
    }

    public float getProbReproduccion() {
        return probReproduccion;
    }

    public void setProbReproduccion(float probReproduccion) {
        if (probReproduccion < 0 || probReproduccion > 100) throw new probabilidadInvalidaException();
        this.probReproduccion = probReproduccion;
        this.probMuerte = 100 - probReproduccion;
        log.info("Probabilidad de reproducción modificada");
    }

    public float getProbClonacion() {
        return probClonacion;
    }

    public void setProbClonacion(float probClonacion) {
        if (probClonacion < 0 || probClonacion > 100) throw new probabilidadInvalidaException();
        this.probClonacion = probClonacion;
        log.info("Probabilidad de clonación modificada");
    }

    public float getProbMuerte() {
        return probMuerte;
    }

    public String getTipo () {return null;}

    public String setTipo (String tipo) {return null;}

    public void reproducirse (individuo pareja) {

    }
    public void clonarse () {}
    public void morir () {

    }
    public void actualizarTV () {
        TiempoDeVidaProperty.set(TiempoDeVidaProperty.get()-1);
        if (TiempoDeVidaProperty.get() == 0) morir();
        log.info("Tiempo de vida actualizado");
    }

    public void mover() {}

    public void moverAleatorio() {
        log.info("Inicio del movimiento aleatorio");
        Random r = new Random();
        int movimiento = r.nextInt(4);
        if (movimiento == 1) {
            this.setPosicionX(this.getPosicionX() - 1);
        } else if (movimiento == 2) {
            this.setPosicionX(this.getPosicionX() + 1);
        } else if (movimiento == 3) {
            this.setPosicionY(this.getPosicionY() - 1);
        } else {
            this.setPosicionY(this.getPosicionY() + 1);
        } log.info("Fin del movimiento aleatorio");
    }

    public void mejorar (recurso recurso) {}


}
