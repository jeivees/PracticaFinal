package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst;

public class nodoBST<T> {
    private T dato;
    private nodoBST<T> derecha;
    private nodoBST<T> izquierda;

    public nodoBST(){}

    public nodoBST(T dato){
        this.dato = dato;
    }
    public T getDato(){
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public nodoBST<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(nodoBST<T> derecha) {
        this.derecha = derecha;
    }

    public nodoBST<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(nodoBST<T> izquierda) {
        this.izquierda = izquierda;
    }
}
