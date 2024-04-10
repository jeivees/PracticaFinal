package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple;

public class ElementoLS<T> {

    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T d) {
        this.data = d;
    }
}
