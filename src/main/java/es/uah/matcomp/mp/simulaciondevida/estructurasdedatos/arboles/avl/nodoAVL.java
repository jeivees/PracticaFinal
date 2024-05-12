package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.avl;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.*;

public class nodoAVL<T> extends nodoBST<T> {
    private int altura;
    private nodoAVL<T> derecha;
    private nodoAVL<T> izquierda;

    public nodoAVL() {
        super();
        this.altura = 0;
    }
    public nodoAVL(T dato) {
        super(dato);
        this.altura = 0;
    }

    public int getAltura() {
        return altura;
    }

    protected void setAltura(int altura) {
        this.altura = altura;
    }

    @Override
    public nodoAVL<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(nodoAVL<T> derecha) {
        this.derecha = derecha;
    }
    @Override
    public nodoAVL<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(nodoAVL<T> izquierda) {
        this.izquierda = izquierda;
    }
}
