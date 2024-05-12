package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;

public class HashMap<C,T> {
    private ListaDE<HashMapElement<C,T>> elementos = new ListaDE<>();
    public HashMap(){}
    public HashMap (ListaDE<HashMapElement<C,T>> list){
        list = elementos;
    }

    public void put (C clave, T elemento) {
        for (int i=0; i != elementos.getNumeroElementos(); i++){
            if (elementos.getElemento(i).getData().getKey() == clave) {
                elementos.getElemento(i).getData().setDato(elemento);
                return;
            }
        }
        this.elementos.add(new HashMapElement<>(clave, elemento));
    }

    public T get (C clave) {
        if (!elementos.isVacia()) {
            for (int i = 0; i < elementos.getNumeroElementos(); i++) {
                HashMapElement<C, T> elementoActual = elementos.getElemento(i).getData();
                if (elementoActual.getKey() == clave) return elementoActual.getDato();
            }
        }
        return null;
    }

    public void del (C clave) {
        for (int i = 0; i != elementos.getNumeroElementos(); i++) {
            HashMapElement<C, T> elementoActual = elementos.getElemento(i).getData();
            if (elementoActual.getKey() == clave) elementos.del(i);
        }
    }
    
    public ListaDE<C> KeySet () {
        ListaDE<C> listaClaves = new ListaDE<>();
        ElementoLDE<HashMapElement<C,T>> elementoActual = elementos.getPrimero();
        
        while (elementoActual != null) {
            listaClaves.add(elementoActual.getData().getKey());
            elementoActual = elementoActual.getSiguiente();
        }

        return listaClaves;
    }
}
