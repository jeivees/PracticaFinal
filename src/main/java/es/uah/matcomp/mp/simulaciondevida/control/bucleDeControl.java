package es.uah.matcomp.mp.simulaciondevida.control;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import gui.mvc.javafx.practicafinal.configuracionDataModel;

import java.util.Random;

public class bucleDeControl {
    private tablero tablero;
    private ListaDE<individuoAbstract> individuos;
    private ListaDE<recursoAbstract> recursos;
    private int individuosMaximosPorCelda;
    private int recursosMaximosPorCelda;

    private configuracionDataModel model;

    public bucleDeControl(tablero tablero, ListaDE<individuoAbstract> individuos, ListaDE<recursoAbstract> recursos, configuracionDataModel model) {
        this.tablero = tablero;
        this.individuos = individuos;
        this.recursos = recursos;
        this.model = model;
        this.individuosMaximosPorCelda = model.getIndividuosMaximosPorCelda();
        this.recursosMaximosPorCelda = model.getRecursosMaximosPorCelda();
    }

    public void ejecutarTurno() {
        actualizarTVIndividuos();
        actualizarTARecursos();
        moverIndividuos();
        evaluarMejoras();
        evaluarReproduccion();
        evaluarClonacion();
        evaluarDesaparicionIndividuos();
        evaluarAparicionDeRecursos();
    }

    private void actualizarTVIndividuos() {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuos.getElemento(i).getData().actualizarTV();
        }
    }

    private void actualizarTARecursos() {
        for (int i = 0; i != recursos.getNumeroElementos(); i++) {
            recursos.getElemento(i).getData().actualizarTA();
        }
    }

    private void moverIndividuos() {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuos.getElemento(i).getData().mover();
        }
    }

    private void evaluarMejoras() {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuoAbstract individuoActual = individuos.getElemento(i).getData();
            casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicionX(), individuoActual.getPosicionY());
            if (!casillaActual.getRecursos().isVacia()) {
                for (int j = 0; j != casillaActual.getRecursos().getNumeroElementos(); j++) {
                    casillaActual.getRecursos().getElemento(j).getData().aplicarMejora(individuoActual);
                }
            }
        }
    }

    private void evaluarReproduccion() {
        for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
            for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                ListaDE<individuoAbstract> individuos = tablero.getCasilla(i, j).getIndividuos();
                if (!individuos.isVacia()) {
                    for (int k = 1; k < individuos.getNumeroElementos(); k += 2) {
                        individuoAbstract individuoActual = individuos.getElemento(k - 1).getData();
                        individuoAbstract pareja = individuos.getElemento(k).getData();
                        Random r = new Random();
                        int p = r.nextInt(1, 100);
                        if (p <= individuoActual.getProbReproduccion()) {
                            individuoActual.reproducirse(pareja);
                        } else {
                            individuoActual.morir();
                            pareja.morir();
                        }
                    }
                }
            }
        }
    }

    private void evaluarClonacion() {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            individuoAbstract individuoActual = individuos.getElemento(i).getData();
            Random r = new Random();
            int p = r.nextInt(1, 100);
            if (p <= individuoActual.getProbClonacion()) {
                individuoActual.clonarse();
            }
        }
    }

    private void evaluarDesaparicionIndividuos() {
        for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
            for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                casillaTablero casillaActual = tablero.getCasilla(i, j);
                int k = 1;
                while (casillaActual.getIndividuos().getNumeroElementos() > individuosMaximosPorCelda) {
                    for (int l = 0; l != casillaActual.getIndividuos().getNumeroElementos(); l++) {
                        individuoAbstract individuoActual = casillaActual.getIndividuos().getElemento(l).getData();
                        if (casillaActual.getIndividuos().getNumeroElementos() > individuosMaximosPorCelda &&
                                individuoActual.getTiempoDeVida() == k) {
                            individuoActual.morir();
                        }
                    }
                    k += 1;
                }
            }
        }
    }

    private void evaluarAparicionDeRecursos() {
        //para una sola casilla de momento
        int[] posicion = {1,1};
        casillaTablero casillaMejorable = new casillaTablero();
        if (casillaMejorable.getRecursos().getNumeroElementos() < recursosMaximosPorCelda) {
            Random r = new Random();
            int p = r.nextInt(1, 100);
            if (p <= model.getProbAparAgua()) {
                agua agua = new agua();
                agua.setPosicion(posicion);
            } else if (p <= model.getProbAparAgua() + model.getProbAparComida()) {
                comida comida = new comida();
                comida.setPosicion(posicion);
            } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña()) {
                montaña montaña = new montaña();
                montaña.setPosicion(posicion);
            } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() + model.getProbAparBiblioteca()) {
                biblioteca biblioteca = new biblioteca();
                biblioteca.setPosicion(posicion);
            } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() + model.getProbAparBiblioteca() + model.getProbAparTesoro()) {
                tesoro tesoro = new tesoro();
                tesoro.setPosicion(posicion);
            } else {
                pozo pozo = new pozo();
                pozo.setPosicion(posicion);
            }
        }
    }
}
