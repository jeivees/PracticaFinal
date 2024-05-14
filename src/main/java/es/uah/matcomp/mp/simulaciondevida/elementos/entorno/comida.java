package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import com.google.gson.annotations.Expose;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.incrementoInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;

public class comida extends recurso<comida> {
    @Expose
    private final String nombreClase = "comida";
    private int incrementoTV;

    public comida () {}
    public comida (int id, DataModel model) {
        super (id, model);
        incrementoTV = model.getIncrementoTurnosComida();
    }
    public comida (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
        incrementoTV = model.getIncrementoTurnosComida();
    }

    public int getIncrementoTV() {
        return incrementoTV;
    }

    public void setIncrementoTV(int incrementoTV) throws incrementoInvalidoException {
        if (incrementoTV < 0) throw new incrementoInvalidoException();
        this.incrementoTV = incrementoTV;
    }
    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual) {
        individuo.setTiempoDeVida(individuo.getTiempoDeVida() + incrementoTV);
    }

    @Override
    public Class<comida> getTipo () {
        return comida.class;
    }
}
