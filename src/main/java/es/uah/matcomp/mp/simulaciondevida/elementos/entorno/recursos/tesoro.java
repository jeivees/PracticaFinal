package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import excepciones.probabilidadInvalidaException;

public class tesoro extends recurso<tesoro> {
    private float incrementoProbReproduccion;

    public tesoro () {}
    public tesoro (int id, int T) {
        super (id, T);
    }
    public tesoro (int id, int PX, int PY, int T) {
        super (id, PX, PY, T);
    }

    public float getIncrementoProbReproduccion() {
        return incrementoProbReproduccion;
    }

    public void setIncrementoProbReproduccion(float incrementoProbReproduccion) throws probabilidadInvalidaException{
        if (incrementoProbReproduccion < 0 || incrementoProbReproduccion > 100) throw new probabilidadInvalidaException();
        this.incrementoProbReproduccion = incrementoProbReproduccion;
    }

    @Override
    public void aplicarMejora (individuo individuo) {
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
