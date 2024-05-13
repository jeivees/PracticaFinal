package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;

public class tesoro extends recurso<tesoro> {
    private float incrementoProbReproduccion;

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
    }

    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual) {
        if (individuo.getProbReproduccion() + incrementoProbReproduccion > 100) {
            individuo.setProbReproduccion(100);
        } else {
            individuo.setProbReproduccion(individuo.getProbReproduccion() + incrementoProbReproduccion);
        }
    }

    @Override
    public Class<tesoro> getTipo () {
        return tesoro.class;
    }
}
