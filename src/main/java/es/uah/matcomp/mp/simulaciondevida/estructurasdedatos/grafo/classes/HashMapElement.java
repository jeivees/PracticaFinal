package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes;

public class HashMapElement<I,T> {
    private I key;
    private T dato;

    public HashMapElement (I clave, T dato) {
        this.key = clave;
        this.dato = dato;
    }

    public I getKey() {
        return key;
    }

    public void setKey(I key) {
        this.key = key;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }
}
