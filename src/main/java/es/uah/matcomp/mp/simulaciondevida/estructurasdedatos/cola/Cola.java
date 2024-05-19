package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;

public class Cola<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();

    public T poll () {
        T primero = elementos.getPrimero().getData();
        elementos.del(0);
        return primero;
    }

    public void add (T e) {
        elementos.add(e);
    }

    public T peek () {
        return elementos.getPrimero().getData();
    }

    public Boolean isVacia () {
        return elementos.isVacia();
    }

    public int getNumeroElementos () {
        return elementos.getNumeroElementos();
    }

    protected ListaEnlazada<T> getElementos () {
        return elementos;
    }

    protected void setElementos (ListaEnlazada<T> elementos) {
        this.elementos = elementos;
    }
}
