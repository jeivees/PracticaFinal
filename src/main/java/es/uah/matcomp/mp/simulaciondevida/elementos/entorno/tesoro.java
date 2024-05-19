package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class tesoro extends recurso {
    private static final Logger log = LogManager.getLogger();
    private float incrementoProbReproduccion;
    private static final Logger logger = LogManager.getLogger();

    public tesoro () {}
    public tesoro (int id, DataModel model) {
        super (id, model);
        incrementoProbReproduccion = model.getIncrementoProbRepro();
    }
    public tesoro (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
        incrementoProbReproduccion = model.getIncrementoProbRepro();
    }

    public float getIncrementoProbReproduccion() {
        return incrementoProbReproduccion;
    }

    public void setIncrementoProbReproduccion(float incrementoProbReproduccion) throws probabilidadInvalidaException{
        if (incrementoProbReproduccion < 0 || incrementoProbReproduccion > 100) throw new probabilidadInvalidaException();
        this.incrementoProbReproduccion = incrementoProbReproduccion;
        logger.debug("Incremento de probabilidad de reproducción modificado");
    }

    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual, int turnoActual) {
        individuo.getAcciones().add(STR."Acción: tesoro recibir efecto, turno: \{turnoActual}");
        log.debug(STR."Efecto de tesoro aplicado a \{individuo.getId()}");
        if (individuo.getProbReproduccion() + incrementoProbReproduccion > 100) {
            individuo.setProbReproduccion(100, turnoActual);
        } else {
            individuo.setProbReproduccion(individuo.getProbReproduccion() + incrementoProbReproduccion, turnoActual);
        }
    }

    @Override
    public Class<tesoro> getTipo () {
        return tesoro.class;
    }
}
