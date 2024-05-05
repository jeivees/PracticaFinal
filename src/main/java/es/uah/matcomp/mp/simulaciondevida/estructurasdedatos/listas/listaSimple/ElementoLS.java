package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple;

public class ElementoLS<T> {

    private T data;

    public ElementoLS () {}

    public ElementoLS (T d) {
        this.data = d;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T d) {
        this.data = d;
    }
}
