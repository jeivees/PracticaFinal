package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import gui.mvc.javafx.practicafinal.configuracionDataModel;

public class pozo extends recurso<pozo> {
    public pozo () {}
    public pozo (int id, int T) {
        super (id, T);
    }
    public pozo (int id, int PX, int PY, int T) {
        super (id, PX, PY, T);
    }

    public void aplicarMejora (individuo individuo, configuracionDataModel model, casillaTablero casillaActual) {
        individuo.morir(model, casillaActual);
    }

    @Override
    public Class<pozo> getTipo () {
        return pozo.class;
    }
}
