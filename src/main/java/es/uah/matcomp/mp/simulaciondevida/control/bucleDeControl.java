package es.uah.matcomp.mp.simulaciondevida.control;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import gui.mvc.javafx.practicafinal.configuracionDataModel;
import gui.mvc.javafx.practicafinal.menuPrincipalController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
public class bucleDeControl {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);
    private tablero tablero;
    private ListaEnlazada<individuo> individuos;
    private ListaEnlazada<recurso> recursos;
    private configuracionDataModel model;

    public bucleDeControl(tablero tablero, configuracionDataModel model) {
        this.tablero = tablero;
        this.individuos = model.getIndividuos();
        this.recursos = model.getRecursos();
        this.model = model;
    }

    public void ejecutarBucle() {
        try {
                model.setTurno(model.getTurno() + 1);
                actualizarTVIndividuos();
                actualizarTARecursos();
                moverIndividuos();
                evaluarMejoras();
                evaluarReproduccion();
                evaluarClonacion();
                evaluarDesaparicionIndividuos();
                evaluarAparicionDeRecursos();
                log.debug("Ha pasado el turno " + model.getTurno());
        } catch (Exception e) {
            log.error("El bucle ha sido interrumpido mientras esperaba");
        }
    }

    private void actualizarTVIndividuos() {
        if (individuos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuos.getElemento(i).getData().actualizarTV(model, tablero.getCasilla(individuos.getElemento(i).getData().getPosicion()));
            }
        }
    }

    private void actualizarTARecursos() {
        if (recursos != null) {
            for (int i = 0; i != recursos.getNumeroElementos(); i++) {
                recursos.getElemento(i).getData().actualizarTA(model, tablero.getCasilla(recursos.getElemento(i).getData().getPosicion()));
            }
        }
    }

    private void moverIndividuos() {
        if (individuos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuos.getElemento(i).getData().mover();
            }
        }
    }

    private void evaluarMejoras() {
        if (individuos != null && recursos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicionX(), individuoActual.getPosicionY());
                if (!casillaActual.getRecursos().isVacia()) {
                    for (int j = 0; j != casillaActual.getRecursos().getNumeroElementos(); j++) {
                        casillaActual.getRecursos().getElemento(j).getData().aplicarMejora(individuoActual);
                    }
                }
            }
        }
    }

    private void evaluarReproduccion() {
        if (individuos != null) {
            for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
                for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                    ListaEnlazada<individuo> individuos = tablero.getCasilla(i, j).getIndividuos();
                    if (!individuos.isVacia()) {
                        for (int k = 1; k < individuos.getNumeroElementos(); k += 2) {
                            individuo individuoActual = individuos.getElemento(k - 1).getData();
                            individuo pareja = individuos.getElemento(k).getData();
                            Random r = new Random();
                            int p = r.nextInt(1, 100);
                            if (p <= individuoActual.getProbReproduccion()) {
                                individuoActual.reproducirse(pareja);
                            } else {
                                individuoActual.morir(model, tablero.getCasilla(individuoActual.getPosicion()));
                                pareja.morir(model, tablero.getCasilla(pareja.getPosicion()));
                            }
                        }
                    }
                }
            }
        }
    }

    private void evaluarClonacion() {
        if (individuos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                Random r = new Random();
                int p = r.nextInt(1, 100);
                if (p <= individuoActual.getProbClonacion()) {
                    individuoActual.clonarse();
                }
            }
        }
    }

    private void evaluarDesaparicionIndividuos() {
        if (individuos != null) {
            for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
                for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                    casillaTablero casillaActual = tablero.getCasilla(i, j);
                    int k = 1;
                    while (casillaActual.getIndividuos().getNumeroElementos() > model.getIndividuosMaximosPorCelda()) {
                        for (int l = 0; l != casillaActual.getIndividuos().getNumeroElementos(); l++) {
                            individuo individuoActual = casillaActual.getIndividuos().getElemento(l).getData();
                            if (casillaActual.getIndividuos().getNumeroElementos() > model.getIndividuosMaximosPorCelda() &&
                                    individuoActual.getTiempoDeVida() == k) {
                                individuoActual.morir(model, tablero.getCasilla(individuoActual.getPosicion()));
                            }
                        }
                        k += 1;
                    }
                }
            }
        }
    }

    private void evaluarAparicionDeRecursos() {
        if (recursos != null) {
            for (int i = 0; i != model.getFilasTablero(); i++) {
                for (int j = 0; j != model.getColumnasTablero(); j++) {
                    casillaTablero casillaMejorable = tablero.getCasilla(i, j);
                    if (casillaMejorable.getRecursos().getNumeroElementos() < 3) {
                        int[] posicion = {i, j};
                        if (casillaMejorable.getRecursos().getNumeroElementos() < model.getRecursosMaximosPorCelda()) {
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
            }
        }
    }
}
