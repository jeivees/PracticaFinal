package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada;
public class ElementoLDE<T> {
    private T data;
    private ElementoLDE<T> siguiente;
    private ElementoLDE<T> anterior;

    protected void insertarmeEn(ElementoLDE<T> el) {
        this.siguiente = el.siguiente;
        this.anterior = el;
        el.siguiente = this;
        if (this.siguiente != null)
            this.siguiente.anterior = this;
    }

    protected ElementoLDE<T> getSiguiente() {
        return siguiente;
    }

    protected ElementoLDE<T> getAnterior() {
        return anterior;
    }

    protected void setSiguiente(ElementoLDE<T> el) {
        this.siguiente = el;
    }

    protected void setAnterior(ElementoLDE<T> el) {
        this.anterior = el;
    }


    public T getData() {
        return this.data;
    }

    public void setData(T d) {
        this.data = d;
    }
}
