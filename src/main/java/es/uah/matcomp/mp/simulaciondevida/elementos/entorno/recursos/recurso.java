package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class recurso {
    private int id;
    private int posicionX;
    private int posicionY;
    private IntegerProperty TiempoDeAparicionProperty = new SimpleIntegerProperty();

    public recurso() {}

    public recurso(int id, int T) {
        this.id = id;
        TiempoDeAparicionProperty.set(T);
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
        posicionX = posicion[0];
        posicionY = posicion[1];
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
    public void eliminar() {

    }
    public void actualizarTA () {
        TiempoDeAparicionProperty.set(TiempoDeAparicionProperty.get()-1);
        if (TiempoDeAparicionProperty.get() == 0) eliminar();
    }

    public void aplicarMejora (individuo individuo) {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
