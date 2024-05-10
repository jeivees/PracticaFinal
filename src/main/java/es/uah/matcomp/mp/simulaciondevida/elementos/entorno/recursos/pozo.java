package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;

public class pozo extends recurso {
    public pozo () {}
    public pozo (int id, int T) {
        super (id, T);
    }

    @Override
    public void aplicarMejora (individuo individuo) {
        individuo.morir();
    }
}
