package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;

public class pozo extends recursoAbstract{
    @Override
    public void aplicarMejora (individuoAbstract individuo) {
        individuo.morir();
    }
}
