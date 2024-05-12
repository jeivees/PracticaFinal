package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAvanzado;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoNormal;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.configuracionDataModel;

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
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual) {
        if (individuo.getProbClonacion() + incrementoProbClonacion > 100) {
            individuo.setProbClonacion(100);
        } else {
            individuo.setProbClonacion(individuo.getProbClonacion() + incrementoProbClonacion);
        }
        if (individuo.getTipo() == individuoBasico.class) {
            casillaActual.addIndividuo(new individuoNormal(individuo), true);
            casillaActual.delIndividuo(individuo);
        } else if (individuo.getTipo() == individuoNormal.class) {
            casillaActual.addIndividuo(new individuoAvanzado(individuo), true);
            casillaActual.delIndividuo(individuo);
        }
    }

    @Override
    public Class<biblioteca> getTipo () {
        return biblioteca.class;
    }
}
