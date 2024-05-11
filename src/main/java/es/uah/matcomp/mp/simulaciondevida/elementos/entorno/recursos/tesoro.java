package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import excepciones.probabilidadInvalidaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class tesoro extends recursoAbstract{
    private float incrementoProbReproduccion;
    private static final Logger logger = LogManager.getLogger("es.uah");

    public float getIncrementoProbReproduccion() {
        return incrementoProbReproduccion;
    }

    public void setIncrementoProbReproduccion(float incrementoProbReproduccion) throws probabilidadInvalidaException{
        if (incrementoProbReproduccion < 0 || incrementoProbReproduccion > 100) throw new probabilidadInvalidaException();
        this.incrementoProbReproduccion = incrementoProbReproduccion;
        logger.info("Incremento de probabilidad de reproducción modificado");
    }

    @Override
    public void aplicarMejora (individuoAbstract individuo) {
        if (individuo.getProbReproduccion() + incrementoProbReproduccion > 100) {
            individuo.setProbReproduccion(100);
        } else {
            individuo.setProbReproduccion(individuo.getProbReproduccion() + incrementoProbReproduccion);
        }
        logger.info("Mejora aplicada");
    }
}
