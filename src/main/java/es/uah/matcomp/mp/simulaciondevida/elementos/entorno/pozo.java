package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import com.google.gson.annotations.Expose;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import gui.mvc.javafx.practicafinal.DataModel;

public class pozo extends recurso<pozo> {
    @Expose
    private final String nombreClase = "pozo";
    public pozo () {}
    public pozo (int id,  DataModel model) {
        super (id, model);
    }
    public pozo (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
    }

    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual) {
        casillaActual.delIndividuo(individuo);
    }

    @Override
    public Class<pozo> getTipo () {
        return pozo.class;
    }
}
