package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.incrementoInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;

public class agua extends recurso {
    private int incrementoTV;

    public agua () {}
    public agua (int id, DataModel model) {
        super (id, model);
        incrementoTV = model.getIncrementoTurnosAgua();
    }
    public agua (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
        incrementoTV = model.getIncrementoTurnosAgua();
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
    public Class<agua> getTipo () {
        return agua.class;
    }
}
