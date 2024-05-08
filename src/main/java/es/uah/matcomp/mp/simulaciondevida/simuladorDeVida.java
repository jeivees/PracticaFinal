package es.uah.matcomp.mp.simulaciondevida;

import es.uah.matcomp.mp.simulaciondevida.control.bucleDeControl;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.configuracionDataModel;

public class simuladorDeVida {
    private tablero tablero;
    private bucleDeControl bucle;
    private configuracionDataModel model;
    public simuladorDeVida (configuracionDataModel model) {
        this.model = model;
        tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero());
        bucle = new bucleDeControl(tablero, model);
    }

    public void comenzar () {
        bucle.ejecutarBucle();
    }

    public simuladorDeVida cargarJuego () {
        return this;
    }
    public tablero getTablero() {
        return tablero;
    }

    public void setTablero(tablero tablero) {
        this.tablero = tablero;
    }

    public bucleDeControl getBucle() {
        return bucle;
    }

    public void setBucle(bucleDeControl bucle) {
        this.bucle = bucle;
    }

    public configuracionDataModel getModel() {
        return model;
    }

    public void setModel(configuracionDataModel model) {
        this.model = model;
    }
}
