package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.*;
import gui.mvc.javafx.practicafinal.configuracionDataModel;
import gui.mvc.javafx.practicafinal.menuPrincipalController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class individuoAvanzado extends individuo<individuoAvanzado> {
    private static final Logger log = LogManager.getLogger(menuPrincipalController.class);
    public individuoAvanzado(int I, int G, int TV, float PR, float PC) {
        super(I, G, TV, PR, PC);
    }
    public individuoAvanzado(int I, int PX, int PY, int G, int TV, float PR, float PC) {
        super(I, PX, PY, G, TV, PR, PC);
    }
    @Override
    public Class<individuoAvanzado> getTipo () {
        return individuoAvanzado.class;
    }

    @Override
    public void mover (configuracionDataModel model, tablero tablero) {
        if (model.getRecursos().isVacia()) {
            this.moverAleatorio(tablero);
        } else {
            Grafo<casillaTablero> grafoTablero = getGrafoTablero(model, tablero);

            Camino<casillaTablero> caminoMinimo = new Camino<>(new ListaDE<>(), Integer.MAX_VALUE);
            for (int i = 0; i != model.getRecursos().getNumeroElementos(); i++) {
                Nodo<casillaTablero> nodoCasillaActual = grafoTablero.getNodo(tablero.getCasilla(getPosicion()));
                Nodo<casillaTablero> nodoRecursoObjetivo = grafoTablero.getNodo(tablero.getCasilla(model.getRecursos().getElemento(i).getData().getPosicion()));
                Camino<casillaTablero> caminoARecurso = grafoTablero.getCaminoMinimo(nodoCasillaActual, nodoRecursoObjetivo);

                if (caminoARecurso.getCoste() <= caminoMinimo.getCoste()) {
                    caminoMinimo = caminoARecurso;
                }
            }

            casillaTablero casillaDestino = caminoMinimo.getCamino().getElemento(1).getData().getDato();
            cambiarPosicion(casillaDestino.getPosicionX(), casillaDestino.getPosicionY(), tablero);
        }
    }

    private Grafo<casillaTablero> getGrafoTablero (configuracionDataModel model, tablero tablero) {
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
                    if (peso < Integer.MAX_VALUE - retrasoMontaña) {
                        peso += retrasoMontaña;
                    }
                    break;
                case "pozo":
                    peso = Integer.MAX_VALUE;
                    break;
                default:
                    log.error("Se ha intentado añadir peso por un recurso que no supone un obstáculo");
            }
        }
        return peso;
    }
}
