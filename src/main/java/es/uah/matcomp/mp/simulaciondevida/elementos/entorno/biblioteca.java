package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAvanzado;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoNormal;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class biblioteca extends recurso {
    private static final Logger log = LogManager.getLogger();
    private float incrementoProbClonacion;

    public biblioteca () {}
    public biblioteca (int id, DataModel model) {
        super (id, model);
        incrementoProbClonacion = model.getIncrementoProbClon();
    }
    public biblioteca (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
        incrementoProbClonacion = model.getIncrementoProbClon();
    }

    public float getIncrementoProbClonacion() {
        return incrementoProbClonacion;
    }

    public void setIncrementoProbClonacion(float incrementoProbClonacion) throws probabilidadInvalidaException {
        if (incrementoProbClonacion < 0 || incrementoProbClonacion > 100) throw new probabilidadInvalidaException();
        this.incrementoProbClonacion = incrementoProbClonacion;
    }

    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual, int turnoActual) {
        individuo.getAcciones().add(STR."Acción: biblioteca recibir efecto, turno: \{turnoActual}");
        log.debug(STR."Efecto de biblioteca aplicado a \{individuo.getId()}");
        if (individuo.getProbClonacion() + incrementoProbClonacion > 100) {
            individuo.setProbClonacion(100, turnoActual);
        } else {
            individuo.setProbClonacion(individuo.getProbClonacion() + incrementoProbClonacion, turnoActual);
        }
        if (individuo.getTipo() == individuoBasico.class) {
            casillaActual.addIndividuo(new individuoNormal(individuo), true);
            casillaActual.delIndividuo(individuo);
            individuo.getAcciones().add(STR."Acción: evolucionar, turno: \{turnoActual}");
            log.debug(STR."Efecto de biblioteca aplicado a \{individuo.getId()}");
        } else if (individuo.getTipo() == individuoNormal.class) {
            casillaActual.addIndividuo(new individuoAvanzado(individuo), true);
            casillaActual.delIndividuo(individuo);
            individuo.getAcciones().add(STR."Acción: biblioteca recibir efecto, turno: \{turnoActual}");
            log.debug(STR."Efecto de biblioteca aplicado a \{individuo.getId()}");
        }
    }

    @Override
    public Class<biblioteca> getTipo () {
        return biblioteca.class;
    }
}
