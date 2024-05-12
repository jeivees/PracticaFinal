package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;

public class Nodo<T> {
    private T dato;
    private String etiqueta;
    private ListaDE<Arco<T>> arcosLlegada = new ListaDE<>();

    private ListaDE<Arco<T>> arcosSalida = new ListaDE<>();

    public Nodo(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public ListaDE<Arco<T>> getArcosLlegada() {
        return arcosLlegada;
    }

    public void setArcosLlegada(ListaDE<Arco<T>> arcosLlegada) {
        this.arcosLlegada = arcosLlegada;
    }

    public ListaDE<Arco<T>> getArcosSalida() {
        return arcosSalida;
    }

    public void setArcosSalida(ListaDE<Arco<T>> arcosSalida) {
        this.arcosSalida = arcosSalida;
    }
}
