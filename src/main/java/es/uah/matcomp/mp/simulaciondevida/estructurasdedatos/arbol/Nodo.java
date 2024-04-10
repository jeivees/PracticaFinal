package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arbol;
public class Nodo<T> {
    private T dato;
    private Nodo<T> derecha;
    private Nodo<T> izquierda;

    public Nodo (){}

    public Nodo (T dato){
        this.dato = dato;
        this.derecha=null;
        this.izquierda=null;
    }
    protected T getDato(){
        return dato;
    }

    protected void setDato(T dato) {
        this.dato = dato;
    }

    protected Nodo<T> getDerecha() {
        return derecha;
    }

    protected void setDerecha(Nodo<T> derecha) {
        this.derecha = derecha;
    }

    protected Nodo<T> getIzquierda() {
        return izquierda;
    }

    protected void setIzquierda(Nodo<T> izquierda) {
        this.izquierda = izquierda;
    }
}
