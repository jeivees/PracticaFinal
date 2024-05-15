package gui.mvc.javafx.practicafinal.listaEnlazada;

public class ElementoLE<TipoDato> {
    private TipoDato data;
    private ElementoLE<TipoDato> siguiente;


    protected void insertarmeEn(ElementoLE<TipoDato> el) {
        this.siguiente = el.siguiente;
        el.siguiente = this;
    }

    public ElementoLE<TipoDato> getSiguiente() {
        return this.siguiente;
    }

    protected void setSiguiente(ElementoLE<TipoDato> el) {
        this.siguiente = el;
    }

    public TipoDato getData() {
        return this.data;
    }
    public void setData(TipoDato newData) {
        this.data = newData;
    }
}
