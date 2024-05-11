package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListaEnlazada<T> {
    private static final Logger log = LogManager.getLogger();
    private ElementoLE<T> primero;
    public ListaEnlazada() {
        this.primero = null;
    }
    public Boolean isVacia() {
        return primero == null;
    }
    public void vaciar() {
        this.primero = null;
    }
    protected void add(ElementoLE<T> elem) {
        if (isVacia()) {
            this.primero = elem;
        }else{
            ElementoLE<T> actual = this.primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            elem.insertarmeEn(actual);
        }
    }
    public void add(T dato) {
        ElementoLE<T> el = new ElementoLE<>();
        el.setData(dato);
        add(el);
    }
    public ElementoLE<T> getElemento(int posicion) {
        if (isVacia()) {
            return null;
        }else{
            ElementoLE<T> first = this.primero;
            for (int i=0; i != posicion; i++) {
                first = first.getSiguiente();
            }
            return first;
        }
    }
    public void insert(T obj, int posicion) {
        ElementoLE<T> objeto = new ElementoLE<>();
        objeto.setData(obj);
        if (posicion == 0) {
            objeto.setSiguiente(primero);
            primero = objeto;
        } else {
            objeto.insertarmeEn(getElemento(posicion-1));
        }
    }
    public int getNumeroElementos() {
        if (isVacia()) {
            return 0;
        }else{
            int elem = 0;
            ElementoLE<T> first = primero;
            while (first != null) {
                elem += 1;
                first = first.getSiguiente();
            }
            return elem;
        }
    }
    public int del(int pos) {
        if (pos == 0) {
            primero = primero.getSiguiente();
            return this.getNumeroElementos();
        }else{
            ElementoLE<T> first = this.primero;
            for (int i=0; i != pos; i++) {
                if (first.getSiguiente().getSiguiente() == null) {
                    first.setSiguiente(null);
                    return this.getNumeroElementos();
                }
                first = first.getSiguiente();
            }
            first.setSiguiente(first.getSiguiente().getSiguiente());
            return this.getNumeroElementos();
        }
    }
    
    public void del (T el) {
        Integer indiceAEliminar = null;
        for (int i=0; i != getNumeroElementos(); i++) {
            if (getElemento(i).getData() == el) {
                indiceAEliminar = i;
            }
        }
        if (indiceAEliminar == null) {
            log.warn("Se ha tratado de eliminar un elemento que no pertenece a la lista");
        } else {
            del(indiceAEliminar);
        }
    }

    public Integer getPosicion(ElementoLE<T> el) {
        int pos = 0;
        ElementoLE<T> first = this.primero;
        for (int i = 0; (pos < getNumeroElementos()) && (first.getData() != el.getData()); i++) {
            first = first.getSiguiente();
            pos += 1;
        }
        if ((pos>=getNumeroElementos())&&(el.getData()!= getUltimo().getData())) {
            return null;
        }
        return pos;
    }
    public Integer getPosicion(T el) {
        if (!this.isVacia()) {
            int pos = 0;
            ElementoLE<T> first = this.primero;
            for (int i = 0; (pos < getNumeroElementos()) && (first.getData() != el); i++) {
                first = first.getSiguiente();
                pos += 1;
            }
            if ((pos >= getNumeroElementos())&&(el != this.getUltimo().getData())) {
                return null;
            }
            return pos;
        }
        return null;
    }
    public ElementoLE<T> getPrimero() {
        if (isVacia()) {
            return null;
        }else{
            return this.primero;
        }
    }
    public ElementoLE<T> getUltimo() {
        if (this.isVacia()) {
            return null;
        }else{
            ElementoLE<T> first = this.primero;
            while (first.getSiguiente() != null) {
                first = first.getSiguiente();
            }
            return first;
        }
    }

    public ListaEnlazada<T> reverse(ListaEnlazada<T> lista) {
        ListaEnlazada<T> listaInvertida = new ListaEnlazada<>();
        if (lista.getNumeroElementos() <= 1) {
            return lista;
        }
        reverseRecursivo(lista, listaInvertida, 0);
        return listaInvertida;
    }

    private void reverseRecursivo(ListaEnlazada<T> lista, ListaEnlazada<T> listaInvertida, int index) {
        if (index == lista.getNumeroElementos() ) {
            return;
        }
        reverseRecursivo(lista, listaInvertida, index + 1);
        listaInvertida.add(lista.getElemento(index));
    }

    public ElementoLE<T> getSiguiente(ElementoLE<T> el) {
        return el.getSiguiente();
    }
    public String toString() {
        return "[" + toStrings(this.primero) + "]";
    }
    private String toStrings(ElementoLE<T> n) {
        String ret = "";
        if (n == null) {
            ret = "";
        } else if (n != this.getUltimo()) {
            ret = n.getData() + ", " + toStrings(n.getSiguiente());
        } else if (n == this.getUltimo()) {
            ret = n.getData() + "";
        }
        return ret;
    }
}
