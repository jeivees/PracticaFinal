package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada;

import com.google.gson.annotations.Expose;

public class ElementoLE<T> {
    @Expose
    private T data;
    @Expose
    private ElementoLE<T> siguiente;


    protected void insertarmeEn(ElementoLE<T> el) {
        this.siguiente = el.siguiente;
        el.siguiente = this;
    }

    public ElementoLE<T> getSiguiente() {
        return this.siguiente;
    }

    protected void setSiguiente(ElementoLE<T> el) {
        this.siguiente = el;
    }

    public T getData() {
        return this.data;
    }
    public void setData(T newData) {
        this.data = newData;
    }
}
