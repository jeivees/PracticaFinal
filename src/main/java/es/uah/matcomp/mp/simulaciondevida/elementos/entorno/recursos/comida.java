package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import excepciones.incrementoInvalidoException;

public class comida extends recurso {
    private int incrementoTV;

    public comida () {}
    public comida (int id, int T) {
        super (id, T);
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
}
