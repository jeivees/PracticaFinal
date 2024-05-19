package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.montaña;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.pozo;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.*;
import excepciones.recursosNoConsumidosException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class individuoAvanzado extends individuo {
    private static final Logger log = LogManager.getLogger();
    public individuoAvanzado () {
        super();
    }
    public individuoAvanzado(int I, int G, int TV, float PR, float PC, int T) {
        super(I, G, TV, PR, PC, T);
    }
    public individuoAvanzado(int I, int PX, int PY, int G, int TV, float PR, float PC, int T) {
        super(I, PX, PY, G, TV, PR, PC, T);
    }
    public individuoAvanzado(individuo individuo) {
        super(individuo);
    }
    @Override
    public Class<individuoAvanzado> getTipo () {
        return individuoAvanzado.class;
    }

    @Override
    public void mover (DataModel model, tablero tablero) throws recursosNoConsumidosException{
        ListaDE<recurso> recursosDeseables = new ListaDE<>();
        for (int k = 0; k != model.getRecursos().getNumeroElementos(); k++) {
            recurso recursoAComprobar = model.getRecursos().getElemento(k).getData();
            if (recursoAComprobar.getTipo() != montaña.class && recursoAComprobar.getTipo() != pozo.class && recursoAComprobar.getPosicion() != getPosicion()) {
                recursosDeseables.add(recursoAComprobar);
            }
        }

        if (recursosDeseables.isVacia()) {
            this.moverAleatorio(tablero, model.getTurno());
        } else {
            Grafo<casillaTablero> grafoTablero = getGrafoTablero(model, tablero);

            Camino<casillaTablero> caminoMinimo = new Camino<>(new ListaDE<>(), Integer.MAX_VALUE);
            for (int i = 0; i != recursosDeseables.getNumeroElementos(); i++) {
                Nodo<casillaTablero> nodoCasillaActual = grafoTablero.getNodo(tablero.getCasilla(getPosicion()));
                Nodo<casillaTablero> nodoRecursoObjetivo = grafoTablero.getNodo(tablero.getCasilla(recursosDeseables.getElemento(i).getData().getPosicion()));
                Camino<casillaTablero> caminoARecurso = grafoTablero.getCaminoMinimo(nodoCasillaActual, nodoRecursoObjetivo);

                if (caminoARecurso == null) { // si es nulo hay recursos que no se han consumido correctamente
                    throw new recursosNoConsumidosException(this);
                }

                if (caminoARecurso.getCoste() <= caminoMinimo.getCoste()) {
                    caminoMinimo = caminoARecurso;
                }
            }

            casillaTablero casillaDestino = caminoMinimo.getCamino().getElemento(1).getData().getDato();
            cambiarPosicion(casillaDestino.getPosicionX(), casillaDestino.getPosicionY(), tablero);

            this.getAcciones().add(STR."Acción: moverse (\{casillaDestino.getPosicionX()}, \{casillaDestino.getPosicionY()}), turno: \{model.getTurno()}");
            log.debug(STR."El individuo se ha movido a \{casillaDestino.getPosicionX()}, \{casillaDestino.getPosicionY()}");
        }
    }

    private Grafo<casillaTablero> getGrafoTablero (DataModel model, tablero tablero) {
        Grafo<casillaTablero> grafoTablero = new Grafo<>(false);
        int retrasoMontaña = model.getIncrementoTurnosMontaña();

        for (int i = 0; i != tablero.getNumeroCasillasN(); i++) {
            for (int j = 0; j != tablero.getNumeroCasillasM(); j++) {
                casillaTablero casillaActual = tablero.getCasilla(i, j);

                grafoTablero.addNodo(casillaActual);

                if (i > 0) {
                    casillaTablero verticeOpuesto = tablero.getCasilla(i - 1, j);
                    int pesoArco = calcularPesoArco(casillaActual, verticeOpuesto, retrasoMontaña);

                    grafoTablero.addArco(pesoArco, casillaActual, verticeOpuesto);
                }
                if (j > 0) {
                    casillaTablero verticeOpuesto = tablero.getCasilla(i, j - 1);
                    int pesoArco = calcularPesoArco(casillaActual, verticeOpuesto, retrasoMontaña);

                    grafoTablero.addArco(pesoArco, casillaActual, verticeOpuesto);
                }
                if (i > 0 && j > 0) {
                    casillaTablero verticeOpuesto = tablero.getCasilla(i - 1, j - 1);
                    int pesoArco = calcularPesoArco(casillaActual, verticeOpuesto, retrasoMontaña);

                    grafoTablero.addArco(pesoArco, casillaActual, verticeOpuesto);
                }
                if (i > 0 && j < tablero.getNumeroCasillasM() - 1) {
                    casillaTablero verticeOpuesto = tablero.getCasilla(i - 1, j + 1);
                    int pesoArco = calcularPesoArco(casillaActual, verticeOpuesto, retrasoMontaña);

                    grafoTablero.addArco(pesoArco, casillaActual, verticeOpuesto);
                }
            }
        }
        return grafoTablero;
    }

    private int calcularPesoArco (casillaTablero vertice1, casillaTablero vertice2, int retrasoMontaña) {
        int peso = 1;
        peso = añadirPesoObstaculos(peso, vertice1, retrasoMontaña);
        peso = añadirPesoObstaculos(peso, vertice2, retrasoMontaña);
        return peso;
    }

    private int añadirPesoObstaculos (int peso, casillaTablero vertice, int retrasoMontaña) {
        for (int k = 0; k != vertice.getRecursos().getNumeroElementos(); k++) {
            switch (vertice.getRecursos().getElemento(k).getData().getTipo().getSimpleName()) {
                case "montaña":
                    if (peso < Integer.MAX_VALUE / 2) {
                        peso += retrasoMontaña;
                    }
                    break;
                case "pozo":
                    peso = Integer.MAX_VALUE / 2;
                    break;
                default:
                    log.trace("El recurso supone un peso de 1");
            }
        }
        return peso;
    }
}
