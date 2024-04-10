package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import excepciones.incrementoInvalidoException;

public class agua extends recursoAbstract{
    private int incrementoTV;

    public int getIncrementoTV() {
        return incrementoTV;
    }

    public void setIncrementoTV(int incrementoTV) throws incrementoInvalidoException {
        if (incrementoTV < 0) throw new incrementoInvalidoException();
        this.incrementoTV = incrementoTV;
    }
    @Override
    public void aplicarMejora (individuoAbstract individuo) {
        individuo.setTiempoDeVida(individuo.getTiempoDeVida() + incrementoTV);
    }
}
