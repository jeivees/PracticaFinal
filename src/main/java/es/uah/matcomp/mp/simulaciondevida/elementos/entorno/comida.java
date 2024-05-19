package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.incrementoInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class comida extends recurso {
    private static final Logger log = LogManager.getLogger();
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
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual, int turnoActual) {
        individuo.getAcciones().add(STR."Acción: comida recibir efecto, turno: \{turnoActual}");
        log.debug(STR."Efecto de comida aplicado a \{individuo.getId()}");
        individuo.setTiempoDeVida(individuo.getTiempoDeVida() + incrementoTV, turnoActual);
    }

    @Override
    public Class<comida> getTipo () {
        return comida.class;
    }
}
