package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import excepciones.probabilidadInvalidaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class biblioteca extends recursoAbstract{
    private float incrementoProbClonacion;
    private static final Logger logger = LogManager.getLogger("es.uah");

    public float getIncrementoProbClonacion() {
        return incrementoProbClonacion;
    }

    public void setIncrementoProbClonacion(float incrementoProbClonacion) throws probabilidadInvalidaException {
        if (incrementoProbClonacion < 0 || incrementoProbClonacion > 100) throw new probabilidadInvalidaException();
        this.incrementoProbClonacion = incrementoProbClonacion;
        logger.info("Incremento de probabilidad de clonación modificado");
    }

    @Override
    public void aplicarMejora (individuoAbstract individuo) {
        if (individuo.getProbClonacion() + incrementoProbClonacion > 100) {
            individuo.setProbClonacion(100);
        } else {
            individuo.setProbClonacion(individuo.getProbClonacion() + incrementoProbClonacion);
        }
        logger.info("Mejora aplicada");
        if (individuo.getTipo().equals("individuoBasico")) {
            individuo.setTipo("individuoNormal");
            logger.info("Mejora de tipo de individuo");
        } else if (individuo.getTipo().equals("individuoNormal")) {
            individuo.setTipo("individuoAvanzado");
            logger.info("Mejora de tipo de individuo");
        }
    }
}
