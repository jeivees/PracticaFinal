package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class pozo extends recurso {
    private static final Logger log = LogManager.getLogger();
    public pozo () {}
    public pozo (int id,  DataModel model) {
        super (id, model);
    }
    public pozo (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
    }

    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual, int turnoActual) {
        individuo.getAcciones().add(STR."Acción: biblioteca recibir efecto, turno: \{turnoActual}");
        log.debug(STR."Efecto de biblioteca aplicado a \{individuo.getId()}");
        casillaActual.delIndividuo(individuo);
    }

    @Override
    public Class<pozo> getTipo () {
        return pozo.class;
    }
}
