package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;

public class Cola<T> {
    private ListaDE<T> elementos = new ListaDE<>();

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
}
