package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import excepciones.arrayTamañoInvalidoException;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.configuracionDataModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public abstract class individuo<T extends individuo<T>> {
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

    public individuo( int id, int posicionX, int posicionY, int generacion, int tiempoDeVida, float probReproduccion, float probClonacion) {
        this.id = id;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.generacion = generacion;
        TiempoDeVidaProperty.set(tiempoDeVida);
        this.probReproduccion = probReproduccion;
        this.probClonacion = probClonacion;
        this.probMuerte = 1-probReproduccion;
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

    public void setPosicion (int[] posicion) {
        try {
            if (posicion.length != 2) throw new arrayTamañoInvalidoException();
            posicionX = posicion[0];
            posicionY = posicion[1];
        } catch (arrayTamañoInvalidoException e) {
            log.error("Se ha intentado establecer la posición de un individuo con un array que no contiene 2 elementos");
        }
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

    public abstract Class<T> getTipo () ;

    public String setTipo (String tipo) {return null;}

    public void reproducirse (individuo pareja) {

    }
    public void clonarse () {}

    public void añadir(configuracionDataModel model, casillaTablero casillaActual) {
        try {
            model.getIndividuos().add(this);
            casillaActual.getIndividuos().add(this);
            this.setPosicion(casillaActual.getPosicion());

            Constructor<? extends individuo> constructor = getClass().getConstructor(
                    int.class, int.class, int.class, int.class, int.class, float.class, float.class);
            model.getHistorialIndividuos().add(constructor.newInstance(
                    id, posicionX, posicionY, generacion, TiempoDeVidaProperty.get(), probReproduccion, probClonacion));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("No se ha podido crear una nueva instancia de individuo para el historial de individuos");
        }
    }
    public void morir(configuracionDataModel model, casillaTablero casillaActual) {
        casillaActual.getIndividuos().del(this);
        model.getIndividuos().del(this);
    }

    public void actualizarTV (configuracionDataModel model, casillaTablero casillaActual) {
        TiempoDeVidaProperty.set(TiempoDeVidaProperty.get()-1);
        if (TiempoDeVidaProperty.get() == 0) morir(model, casillaActual);
        log.info("Tiempo de vida actualizado");
    }

    public abstract void mover(configuracionDataModel model, tablero tablero);

    protected void moverAleatorio(tablero tablero) {
        log.info("Inicio de movimiento aleatorio");
        Random r = new Random();
        int movimiento = r.nextInt(1,8);

        switch (movimiento) {
            case 1:
                cambiarPosicion(getPosicionX() + 1, getPosicionY(), tablero);
                break;
            case 2:
                cambiarPosicion(getPosicionX() + 1, getPosicionY() - 1, tablero);
                break;
            case 3:
                cambiarPosicion(getPosicionX(), getPosicionY() - 1, tablero);
                break;
            case 4:
                cambiarPosicion(getPosicionX() - 1, getPosicionY() - 1, tablero);
                break;
            case 5:
                cambiarPosicion(getPosicionX() - 1, getPosicionY(), tablero);
                break;
            case 6:
                cambiarPosicion(getPosicionX() - 1, getPosicionY() + 1, tablero);
                break;
            case 7:
                cambiarPosicion(getPosicionX(), getPosicionY() + 1, tablero);
                break;
            case 8:
                cambiarPosicion(getPosicionX() + 1, getPosicionY() + 1, tablero);
                break;
            default:
                log.error("Se ha intentado hacer un movimiento aleatorio inválido (numero generado < 1 o > 8)");
        }
        log.info("Fin del movimiento aleatorio");
    }

    protected void cambiarPosicion (int nuevaPosicionX, int nuevaPosicionY, tablero tablero) {
        casillaTablero casillaActual = tablero.getCasilla(getPosicion());
        casillaActual.getIndividuos().del(this);
        casillaActual.resetVisual();

        setPosicionX(nuevaPosicionX);
        setPosicionY(nuevaPosicionY);

        casillaTablero nuevaCasilla = tablero.getCasilla(nuevaPosicionX, nuevaPosicionY);
        nuevaCasilla.getIndividuos().add(this);
        nuevaCasilla.resetVisual();
    }

    public void mejorar (recurso recurso) {}
}
