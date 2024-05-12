package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;

public class Camino<T> {
    ListaDE<Nodo<T>> camino;
    double peso;

    public Camino(ListaDE<Nodo<T>> camino, double peso) {
        this.camino = camino;
        this.peso = peso;
    }

    public ListaDE<Nodo<T>> getCamino() {
        return camino;
    }

    public double getCoste() {
        return peso;
    }

    @Override
    public String toString() {
        StringBuffer salida = new StringBuffer();
        salida.append("Volcado del camino desde '" + getCamino().getPrimero().getData().getDato() + "' hasta '" + getCamino().getUltimo().getData().getDato() + "':       \n");
        salida.append("Lista de datos en vértices: [");
        for (int i=0; i != this.getCamino().getNumeroElementos(); i++) {
            salida.append(this.getCamino().getElemento(i).getData().getDato());
        }
        salida.append("] - Coste: " + this.getCoste() + "\n");

        return salida.toString();
    }
}
