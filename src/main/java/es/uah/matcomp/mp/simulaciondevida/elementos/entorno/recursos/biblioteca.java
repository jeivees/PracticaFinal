package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import excepciones.probabilidadInvalidaException;

public class biblioteca extends recurso<biblioteca> {
    private float incrementoProbClonacion;

    public biblioteca () {}
    public biblioteca (int id, int T) {
        super (id, T);
    }
    public biblioteca (int id, int PX, int PY, int T) {
        super (id, PX, PY, T);
    }

    public float getIncrementoProbClonacion() {
        return incrementoProbClonacion;
    }

    public void setIncrementoProbClonacion(float incrementoProbClonacion) throws probabilidadInvalidaException {
        if (incrementoProbClonacion < 0 || incrementoProbClonacion > 100) throw new probabilidadInvalidaException();
        this.incrementoProbClonacion = incrementoProbClonacion;
    }

    @Override
    public void aplicarMejora (individuo individuo) {
        if (individuo.getProbClonacion() + incrementoProbClonacion > 100) {
            individuo.setProbClonacion(100);
        } else {
            individuo.setProbClonacion(individuo.getProbClonacion() + incrementoProbClonacion);
        }
        if (individuo.getTipo().getSimpleName().equals("individuoBasico")) {
            individuo.setTipo("individuoNormal");
        } else if (individuo.getTipo().getSimpleName().equals("individuoNormal")) {
            individuo.setTipo("individuoAvanzado");
        }
    }

    @Override
    public Class<biblioteca> getTipo () {
        return biblioteca.class;
    }
}
