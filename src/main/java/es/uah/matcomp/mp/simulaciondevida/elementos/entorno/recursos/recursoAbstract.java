package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;

public abstract class recursoAbstract {
    private int tiempoDeAparicion;

    public recursoAbstract () {}

    public recursoAbstract (int T) {
        this.tiempoDeAparicion = T;
    }

    public void eliminar() {

    }
    public void actualizarTA () {
        tiempoDeAparicion -= 1;
        if (tiempoDeAparicion == 0) eliminar();
    }

    public void aplicarMejora (individuoAbstract individuo) {}
}
