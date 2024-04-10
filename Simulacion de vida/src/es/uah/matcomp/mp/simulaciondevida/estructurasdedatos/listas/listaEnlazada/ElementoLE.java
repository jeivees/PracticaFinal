package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada;

public class ElementoLE {
    private Object data;
    private ElementoLE siguiente;

    protected void insertarmeEn(ElementoLE el) {
        this.siguiente = el.siguiente;
        el.siguiente = this;
    }

    protected ElementoLE getSiguiente() {
        return siguiente;
    }

    protected void setSiguiente(ElementoLE el) {
        this.siguiente = el;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object d) {
        this.data = d;
    }
}
