package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import excepciones.probabilidadInvalidaException;

public class tesoro extends recurso {
    private float incrementoProbReproduccion;

    public tesoro () {}
    public tesoro (int id, int T) {
        super (id, T);
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
}
