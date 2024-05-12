package es.uah.matcomp.mp.simulaciondevida.control;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import gui.mvc.javafx.practicafinal.configuracionDataModel;
import gui.mvc.javafx.practicafinal.menuPrincipalController;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
public class bucleDeControl implements Runnable{
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

    @Override
    public void run() {
        try {
            Platform.runLater(() -> {
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
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("El bucle ha sido interrumpido mientras esperaba");
        }
    }

    private void actualizarTVIndividuos() {
        if (individuos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casilla = tablero.getCasilla(individuoActual.getPosicion());
                individuoActual.actualizarTV(model, casilla);
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
                individuos.getElemento(i).getData().mover(model, tablero);
            }
        }
    }

    private void evaluarMejoras() {
        if (individuos != null && recursos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicion());
                if (!casillaActual.getRecursos().isVacia()) {
                    for (int j = 0; j != casillaActual.getRecursos().getNumeroElementos(); j++) {
                        recurso recursoActual = casillaActual.getRecursos().getElemento(j).getData();
                        recursoActual.aplicarMejora(individuoActual, casillaActual);
                        casillaActual.delRecurso(recursoActual);
                    }
                }
            }
        }
    }

    private void evaluarReproduccion() {
        if (individuos != null) {
            for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
                for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                    casillaTablero casillaActual = tablero.getCasilla(i, j);
                    ListaEnlazada<individuo> individuos = casillaActual.getIndividuos();
                    if (individuos.getNumeroElementos() >= 2 ) {
                        for (int k = 1; k < individuos.getNumeroElementos(); k += 2) {
                            individuo individuoActual = individuos.getElemento(k - 1).getData();
                            individuo pareja = individuos.getElemento(k).getData();

                            individuoActual.reproducirse(pareja, model, casillaActual);
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
                    individuoActual.clonarse(model, tablero.getCasilla(individuoActual.getPosicion()));
                }
            }
        }
    }

    private void evaluarDesaparicionIndividuos() {
        if (individuos != null) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicion());
                if (individuoActual.getTiempoDeVida() <= 0) {
                    casillaActual.delIndividuo(individuoActual);
                } else {
                    int k = 1;
                    while (casillaActual.getIndividuos().getNumeroElementos() > model.getIndividuosMaximosPorCelda()) {
                        for (int l = 0; l != casillaActual.getIndividuos().getNumeroElementos(); l++) {
                            individuo individuoSobrante = casillaActual.getIndividuos().getElemento(l).getData();
                            if (casillaActual.getIndividuos().getNumeroElementos() > model.getIndividuosMaximosPorCelda() &&
                                    individuoSobrante.getTiempoDeVida() == k) {
                                casillaActual.delIndividuo(individuoSobrante);
                            }
                        }
                        k += 1;
                    }
                }
            }
        }
    }

    private void evaluarAparicionDeRecursos() {
        for (int i = 0; i != model.getFilasTablero(); i++) {
            for (int j = 0; j != model.getColumnasTablero(); j++) {
                casillaTablero casillaMejorable = tablero.getCasilla(i, j);

                if (casillaMejorable.getRecursos().getNumeroElementos() < model.getRecursosMaximosPorCelda()) {
                    Random r = new Random();
                    int q = r.nextInt(1,100);

                    if (q <= model.getProbAparRecurso()) {
                        int idNuevoRecurso = model.getHistorialRecursos().getUltimo().getData().getId() + 1;

                        Random s = new Random();
                        int p = s.nextInt(1, 100);

                        if (p <= model.getProbAparAgua()) {
                           casillaMejorable.addRecurso(new agua(idNuevoRecurso, model.getTurnosInicialesRecurso()), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida()) {
                            casillaMejorable.addRecurso(new comida(idNuevoRecurso, model.getTurnosInicialesRecurso()), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña()) {
                            casillaMejorable.addRecurso(new montaña(idNuevoRecurso, model.getTurnosInicialesRecurso()), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() + model.getProbAparBiblioteca()) {
                            casillaMejorable.addRecurso(new biblioteca(idNuevoRecurso, model.getTurnosInicialesRecurso()), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() + model.getProbAparBiblioteca() + model.getProbAparTesoro()) {
                            casillaMejorable.addRecurso(new tesoro(idNuevoRecurso, model.getTurnosInicialesRecurso()), true);

                        } else {
                            casillaMejorable.addRecurso(new pozo(idNuevoRecurso, model.getTurnosInicialesRecurso()), true);
                        }
                    }
                }
            }
        }
    }
}
