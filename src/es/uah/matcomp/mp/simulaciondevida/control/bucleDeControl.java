package es.uah.matcomp.mp.simulaciondevida.control;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;

public class bucleDeControl {
    private tablero tablero;
    private ListaDE<individuoAbstract> individuos;
    private ListaDE<recursoAbstract> recursos;

    public bucleDeControl (tablero tablero, ListaDE<individuoAbstract> individuos, ListaDE<recursoAbstract> recursos) {
        this.tablero = tablero;
        this.individuos = individuos;
        this.recursos = recursos;
    }

    public void ejecutarTurno () {
        actualizarTVIndividuos();
        actualizarTARecursos();
        moverIndividuos();
        evaluarMejoras();
        evaluarReproduccion();
        evaluarClonacion();
        evaluarDesaparicion();
        evaluarAparicionDeRecursos();
    }

    public void actualizarTVIndividuos () {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuos.getElemento(i).getData().actualizarTV();
        }
    }

    public void actualizarTARecursos () {
        for (int i = 0; i != recursos.getNumeroElementos(); i++) {
            recursos.getElemento(i).getData().actualizarTA();
        }
    }

    public void moverIndividuos () {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuos.getElemento(i).getData().mover();
        }
    }

    public void evaluarMejoras () {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuoAbstract individuoActual = individuos.getElemento(i).getData();
            casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicion());
            if (!casillaActual.getRecursos().isVacia()) {
                for (int j = 0; j != casillaActual.getRecursos().getNumeroElementos(); j++) {
                    casillaActual.getRecursos().getElemento(j).getData().aplicarMejora(individuoActual);
                }
            }
        }
    }

    public void evaluarReproduccion () {

    }

    public void evaluarClonacion () {

    }

    public void evaluarDesaparicion () {

    }

    public void evaluarAparicionDeRecursos () {

    }
}
