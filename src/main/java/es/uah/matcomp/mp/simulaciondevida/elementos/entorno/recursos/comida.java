package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import excepciones.incrementoInvalidoException;

public class comida extends recurso<comida> {
    private int incrementoTV;

    public comida () {}
    public comida (int id, int T) {
        super (id, T);
    }
    public comida (int id, int PX, int PY, int T) {
        super (id, PX, PY, T);
    }

    public int getIncrementoTV() {
        return incrementoTV;
    }

    public void setIncrementoTV(int incrementoTV) throws incrementoInvalidoException {
        if (incrementoTV < 0) throw new incrementoInvalidoException();
        this.incrementoTV = incrementoTV;
    }
    @Override
    public void aplicarMejora (individuo individuo) {
        individuo.setTiempoDeVida(individuo.getTiempoDeVida() + incrementoTV);
    }

    @Override
    public Class<comida> getTipo () {
        return comida.class;
    }
}
