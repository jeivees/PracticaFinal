package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada;


public class ListaDE<T>{
    private ElementoLDE<T> primero;
    private ElementoLDE<T> ultimo;

    public ListaDE() {
        this.primero = null;
        this.ultimo = null;
    }

    public Boolean isVacia() {
        return this.primero == null;
    }

    public void vaciar() {
        this.primero = null;
        this.ultimo = null;
    }

    protected int add(ElementoLDE<T> el) {
        int pos = 1;
        if (isVacia()) {
            this.primero = el;
            this.ultimo = el;
            return 0;
        } else {
            ElementoLDE<T> actual = this.primero;
            while (actual != this.ultimo) {
                actual = actual.getSiguiente();
                pos += 1;
            }
            el.insertarmeEn(this.ultimo);
            this.ultimo = el;
            return pos;
        }
    }

    public void add(T o) {
        ElementoLDE<T> e = new ElementoLDE<>();
        e.setData(o);
        add(e);
    }

    public void insert(T o, int posicion) {
        ElementoLDE<T> objeto = new ElementoLDE<>();
        objeto.setData(o);
        if (posicion == 0){
            objeto.setSiguiente(this.primero);
            if (this.primero != null) {
                this.primero.setAnterior(objeto);
            } else {
                this.ultimo = objeto;
            }
            this.primero = objeto;
        } else {
            if (getElemento(posicion - 1) == null){
                System.out.println("No existen tantos elementos en la lista");
            } else {
                objeto.insertarmeEn(getElemento(posicion - 1));
            }
        }
    }


    public int del(int posicion) {
        if (posicion == 0) {
            this.primero = this.primero.getSiguiente();
            if (!isVacia()){
                this.primero.setAnterior(null);
            }
        } else {
            ElementoLDE<T> actual = this.primero;
            for (int i = 0; i != posicion ; i++) {
                actual = actual.getSiguiente();
                if (actual == this.ultimo) {
                    this.ultimo = actual.getAnterior();
                    return this.getNumeroElementos();
                }
            }
            actual.getSiguiente().setAnterior(actual.getAnterior());
            actual.getAnterior().setSiguiente(actual.getSiguiente());
        }
        return this.getNumeroElementos();
    }

    public int getNumeroElementos() {
        if (isVacia()) {
            return 0;
        } else {
            int elms = 1;
            ElementoLDE<T> actual = this.primero;
            while (actual != this.ultimo) {
                elms += 1;
                actual = actual.getSiguiente();
            }
            return elms;
        }
    }

    public int getPosicion (ElementoLDE<T> e){
        int pos = 0;
        ElementoLDE<T> actual = this.primero;
        while (actual != e) {
            if (actual == null){
                System.out.println("El elemento no pertenece a la lista");
                return -1;
            }
            actual = actual.getSiguiente();
            pos += 1;
        }
        return pos;
    }

    public ElementoLDE<T> getPrimero() {
        if (isVacia()){
            return null;
        } else {
            return this.primero;
        }
    }

    public ElementoLDE<T> getUltimo () {
        if (isVacia()){
            return null;
        } else {
            return this.ultimo;
        }
    }

    public ElementoLDE<T> getSiguiente (ElementoLDE<T> el){
        return el.getSiguiente();
    }

    public ElementoLDE<T> getElemento (int posicion){
        if (isVacia()){
            System.out.println("La lista esta vacia, no contiene elementos");
            return null;
        } else {
            ElementoLDE<T> actual = this.primero;
            for (int i = 0; i != posicion; i++) {
                actual = actual.getSiguiente();
            }
            return actual;
        }
    }

    public void setElemento (int posicion, T elemento) {
        ElementoLDE<T> elementoActual = this.primero;
        for (int i = 0; i != posicion; i++) {
            elementoActual = elementoActual.getSiguiente();
        }
        elementoActual.setData(elemento);
    }

    public String toString(){
        String salida = "";
        return "["+ toStringAux(this.primero,salida)+"]";
    }
    public String toStringAux(ElementoLDE<T> n, String salida){
        if((n != null)&&(n != this.ultimo)){
            salida= salida + n.getData() + ", "+toStringAux(n.getSiguiente(),salida);
        } else if (n== this.ultimo){
            salida = salida + n.getData();
        }
        return salida;
    }
}