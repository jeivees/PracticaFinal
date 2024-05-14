package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import excepciones.incrementoInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;

public class montaña extends recurso<montaña> {
    private int incrementoTV;

    public montaña () {}
    public montaña (int id, DataModel model) {
        super (id, model);
        incrementoTV = model.getIncrementoTurnosMontaña();
    }
    public montaña (int id, int PX, int PY, DataModel model) {
        super (id, PX, PY, model);
        incrementoTV = model.getIncrementoTurnosMontaña();
    }

    public int getIncrementoTV() {
        return incrementoTV;
    }

    public void setIncrementoTV(int incrementoTV) throws incrementoInvalidoException{
        if (incrementoTV > 0) throw new incrementoInvalidoException();
        this.incrementoTV = incrementoTV;
    }

    @Override
    public void aplicarMejora (individuo individuo, casillaTablero casillaActual) {
        individuo.setTiempoDeVida(individuo.getTiempoDeVida() - incrementoTV);
        if (individuo.getTiempoDeVida() <= 0) {
            casillaActual.delIndividuo(individuo);
        }
    }

    @Override
    public Class<montaña> getTipo () {
        return montaña.class;
    }
}
