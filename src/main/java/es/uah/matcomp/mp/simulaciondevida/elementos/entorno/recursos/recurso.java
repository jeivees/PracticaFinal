package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.arrayTamañoInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class recurso <T extends recurso<T>>{
    private static final Logger log = LogManager.getLogger("es.uah");
    private int id;
    private int posicionX;
    private int posicionY;
    private IntegerProperty TiempoDeAparicionProperty = new SimpleIntegerProperty();

    public recurso() {}

    public recurso(int id, DataModel model) {
        this.id = id;
        TiempoDeAparicionProperty.set(model.getTurnosInicialesRecurso());
    }

    public recurso(int id, int posicionX, int posicionY, DataModel model) {
        this.id = id;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        TiempoDeAparicionProperty.set(model.getTurnosInicialesRecurso());
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

    public int getTiempoDeAparicion() {
        return TiempoDeAparicionProperty.get();
    }

    public IntegerProperty getTiempoDeAparicionProperty () {
        return TiempoDeAparicionProperty;
    }

    public void setTiempoDeAparicion(int tiempoDeAparicion) {
        TiempoDeAparicionProperty.set(tiempoDeAparicion);
    }

    public abstract Class<T> getTipo ();

    public void añadir(DataModel model, casillaTablero casillaActual) {
        try {
            model.getRecursos().add(this);
            casillaActual.getRecursos().add(this);
            this.setPosicion(casillaActual.getPosicion());

            Constructor<? extends recurso> constructor = getClass().getConstructor(
                    int.class, int.class, int.class, DataModel.class);
            model.getHistorialRecursos().add(constructor.newInstance(
                    id, posicionX, posicionY, model));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error("No se ha podido crear una nueva instancia de recurso para el historial de recursos");
        }
    }
    public void eliminar(DataModel model, casillaTablero casillaActual) {
        model.getRecursos().del(this);
        casillaActual.getRecursos().del(this);
    }

    public void actualizarTA (DataModel model, casillaTablero casillaActual) {
        TiempoDeAparicionProperty.set(TiempoDeAparicionProperty.get()-1);
        if (TiempoDeAparicionProperty.get() == 0) eliminar(model, casillaActual);
    }

    public abstract void aplicarMejora (individuo individuo, casillaTablero casillaActual);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
