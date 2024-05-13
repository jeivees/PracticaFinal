package es.uah.matcomp.mp.simulaciondevida;

import es.uah.matcomp.mp.simulaciondevida.control.bucleDeControl;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.DataModel;

public class simuladorDeVida {
    private tablero tablero;
    private bucleDeControl bucle;
    private DataModel model;

    public simuladorDeVida (DataModel model) {
        this.model = model;
        tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero(), model);
        bucle = new bucleDeControl(tablero, model);
    }
    public simuladorDeVida (DataModel model, tablero tablero) {
        this.model = model;
        bucle = new bucleDeControl(tablero, model);
    }

    public void comenzar (boolean unTurno) {
        if (unTurno) {
            bucle.setUnTurno(true);
        } else {
            bucle.setUnTurno(false);
        }
        Thread threadBucle = new Thread(bucle);
        threadBucle.start();
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

    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel model) {
        this.model = model;
    }
}
