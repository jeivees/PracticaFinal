package es.uah.matcomp.mp.simulaciondevida.control;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import excepciones.recursosNoConsumidosException;
import gui.mvc.javafx.practicafinal.DataModel;
import gui.mvc.javafx.practicafinal.menuPrincipalController;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static java.lang.Thread.sleep;

public class bucleDeControl implements Runnable{
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);
    private final tablero tablero;
    private final ListaEnlazada<individuo> individuos;
    private final ListaEnlazada<recurso> recursos;
    private final DataModel model;
    private final IntegerProperty turnoProperty = new SimpleIntegerProperty();
    private boolean unTurno;

    public bucleDeControl(tablero tablero, DataModel model) {
        this.tablero = tablero;
        this.individuos = model.getIndividuos();
        this.recursos = model.getRecursos();
        this.model = model;
    }

    @Override
    public void run() {
        try {
            if (unTurno) {
                ejecutarBucle();
            } else {
                while (!model.isPausado()) {
                    ejecutarBucle();
                    sleep(1500);
                }
            }
        } catch (Exception e) {
            log.error("El bucle ha sido interrumpido mientras esperaba");
        }
    }

    private void ejecutarBucle() {
        Platform.runLater(() -> {
            turnoProperty.set(turnoProperty.get() + 1);
            model.setTurno(model.getTurno() + 1);
            actualizarTVIndividuos();
            actualizarTARecursos();
            moverIndividuos();
            evaluarMejoras();
            evaluarReproduccion();
            evaluarClonacion();
            evaluarDesaparicionIndividuos();
            evaluarAparicionDeRecursos();
            log.debug("Ha pasado el turno " + turnoProperty.get());
        });
    }

    private void actualizarTVIndividuos() {
        if (!individuos.isVacia()) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casilla = tablero.getCasilla(individuoActual.getPosicion());
                boolean muere = individuoActual.actualizarTV(casilla, getTurnoProperty().get());
                if (muere) {
                    log.info("El individuo " + individuoActual + " ha muerto");
                    i -= 1;
                }
            }
        }
    }

    private void actualizarTARecursos() {
        if (!recursos.isVacia()) {
            for (int i = 0; i != recursos.getNumeroElementos(); i++) {
                recurso recursoActual = recursos.getElemento(i).getData();
                boolean desaparece = recursoActual.actualizarTA(model, tablero.getCasilla(recursos.getElemento(i).getData().getPosicion()));
                if (desaparece) {
                    log.info("El recurso " + recursoActual + " ha desaparecido");
                    i -= 1;
                }
            }
        }
    }

    private void moverIndividuos() {
        try {
            if (!individuos.isVacia()) {
                for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                    individuos.getElemento(i).getData().mover(model, tablero);
                }
            }
        } catch (recursosNoConsumidosException e) {
            log.error("Se ha detectado un camino nulo, hay recursos que no se han consumido correctamente después de que un individuo llegase a la casilla");
        }
    }

    private void evaluarMejoras() {
        if (!individuos.isVacia() && !recursos.isVacia()) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicion());
                if (!casillaActual.getRecursos().isVacia()) {
                    int numeroRecursos = casillaActual.getRecursos().getNumeroElementos();
                    for (int j = 0; j != numeroRecursos; j++) {
                        recurso recursoActual = casillaActual.getRecursos().getPrimero().getData();
                        if (individuoActual.isVivo()) {
                            recursoActual.aplicarMejora(individuoActual, casillaActual, turnoProperty.get());
                            if (!individuoActual.isVivo()) i -= 1;
                        }
                        casillaActual.delRecurso(recursoActual);
                    }
                }
            }
        }
    }

    private void evaluarReproduccion() {
        if (!individuos.isVacia()) {
            for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
                for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                    casillaTablero casillaActual = tablero.getCasilla(i, j);
                    ListaEnlazada<individuo> individuos = casillaActual.getIndividuos();

                    int numeroIndividuos = individuos.getNumeroElementos();
                    if (numeroIndividuos >= 2 ) {
                        for (int k = 1 ; k < numeroIndividuos; k += 2) {
                            individuo individuoActual = individuos.getElemento(k - 1).getData();
                            individuo pareja = individuos.getElemento(k).getData();

                            boolean mueren1 = individuoActual.reproducirse(pareja, model, casillaActual, turnoProperty.get());
                            boolean mueren2 = pareja.reproducirse(individuoActual, model, casillaActual, turnoProperty.get());
                            if (mueren1 || mueren2) {
                                casillaActual.delIndividuo(individuoActual);
                                casillaActual.delIndividuo(pareja);
                            }
                        }
                    }
                }
            }
        }
    }

    private void evaluarClonacion() {
        if (!individuos.isVacia()) {
            int numeroIndividuos = individuos.getNumeroElementos();
            for (int i = 0; i != numeroIndividuos; i++) {
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
        if (!individuos.isVacia()) {
            for (int i = 0; i != individuos.getNumeroElementos(); i++) {
                individuo individuoActual = individuos.getElemento(i).getData();
                casillaTablero casillaActual = tablero.getCasilla(individuoActual.getPosicion());
                int k = 1;
                while (casillaActual.getIndividuos().getNumeroElementos() > model.getIndividuosMaximosPorCelda()) {
                    int numeroIndividuosCasilla = casillaActual.getIndividuos().getNumeroElementos();
                    for (int l = 0; l != numeroIndividuosCasilla; l++) {
                        individuo individuoSobrante = casillaActual.getIndividuos().getElemento(l).getData();
                        if (casillaActual.getIndividuos().getNumeroElementos() > model.getIndividuosMaximosPorCelda() &&
                                individuoSobrante.getTiempoDeVida() == k) {
                            casillaActual.delIndividuo(individuoSobrante);
                            l -= 1;
                        }
                    }
                    k += 1;
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
                        int idNuevoRecurso;
                        if (model.getHistorialRecursos().isVacia()) {
                            idNuevoRecurso = 1;
                        } else {
                            idNuevoRecurso = model.getHistorialRecursos().getUltimo().getData().getId() + 1;
                        }

                        Random s = new Random();
                        int p = s.nextInt(1, model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() +
                                model.getProbAparBiblioteca() + model.getProbAparTesoro() + model.getProbAparPozo());

                        if (p <= model.getProbAparAgua()) {
                           casillaMejorable.addRecurso(new agua(idNuevoRecurso, model), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida()) {
                            casillaMejorable.addRecurso(new comida(idNuevoRecurso, model), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña()) {
                            casillaMejorable.addRecurso(new montaña(idNuevoRecurso, model), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() + model.getProbAparBiblioteca()) {
                            casillaMejorable.addRecurso(new biblioteca(idNuevoRecurso, model), true);

                        } else if (p <= model.getProbAparAgua() + model.getProbAparComida() + model.getProbAparMontaña() + model.getProbAparBiblioteca() + model.getProbAparTesoro()) {
                            casillaMejorable.addRecurso(new tesoro(idNuevoRecurso, model), true);

                        } else {
                            casillaMejorable.addRecurso(new pozo(idNuevoRecurso, model), true);
                        }
                    }
                }
            }
        }
    }

    public void updateTurnoProperty () {
        setTurnoProperty(model.getTurno());
    }

    public boolean isUnTurno() {
        return unTurno;
    }

    public void setUnTurno(boolean unTurno) {
        this.unTurno = unTurno;
    }

    public IntegerProperty getTurnoProperty() {
        return turnoProperty;
    }

    public void setTurnoProperty(Integer turnoProperty) {
        this.turnoProperty.set(turnoProperty);
    }
}
