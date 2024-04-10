package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada;

public class ListaEnlazada {
    private ElementoLE primero;

    public ListaEnlazada() {
        this.primero = null;
    }

    public Boolean isVacia() {
        return this.primero == null;
    }

    public void vaciar() {
        this.primero = null;
    }

    protected int add(ElementoLE el) {
        int pos = 1;
        if (isVacia()) {
            this.primero = el;
            return 0;
        } else {
            ElementoLE actual = this.primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
                pos += 1;
            }
            el.insertarmeEn(actual);
            return pos;
        }
    }

    public void add(String s) {
        ElementoLE e = new ElementoLE();
        e.setData(s);
        add(e);
    }

    public void add(Object o) {
        ElementoLE e = new ElementoLE();
        if (o instanceof ElementoLE) {
            e = (ElementoLE) o;
        } else {
            e.setData(o);
        }
        add(e);
    }

    public void insert(Object o, int posicion) {
        ElementoLE objeto = new ElementoLE();
        objeto.setData(o);
        if (posicion == 0){
            objeto.setSiguiente(this.primero);
            this.primero = objeto;
        } else {
            if (getElemento(posicion - 1) == null){
                System.out.println("No existen tantos elementos en la lista");
            } else {
            objeto.insertarmeEn(getElemento(posicion - 1));
            }
        }
    }

    public void insert(String s, int posicion) {
    ElementoLE objeto = new ElementoLE();
        objeto.setData(s);
        if (posicion == 0){
        objeto.setSiguiente(this.primero);
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
            return this.getNumeroElementos();
        } else {
            ElementoLE actual = this.primero;
            for (int i = 0; i != posicion - 1; i++) {
                actual = actual.getSiguiente();
                if (actual.getSiguiente().getSiguiente() == null) {
                    actual.setSiguiente(null);
                    return this.getNumeroElementos();
                }
            }
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            return this.getNumeroElementos();
        }
    }

    public int getNumeroElementos() {
        if (isVacia()) {
            return 0;
        } else {
            int elms = 0;
            ElementoLE actual = this.primero;
            while (actual != null) {
                elms += 1;
                actual = actual.getSiguiente();
            }
            return elms;
        }
    }

    public int getPosicion(ElementoLE el) {
        int pos = 0;
        ElementoLE actual = this.primero;
        while (actual != el) {
            if (actual == null){
                System.out.println("El elemento no pertenece a la lista");
                return -1;
            }
            actual = actual.getSiguiente();
            pos += 1;
        }
        return pos;
    }

    public ElementoLE getPrimero() {
        if (isVacia()){
            return null;
        } else {
            return this.primero;
        }
    }

    public ElementoLE getUltimo() {
        if (isVacia()){
            return null;
        } else {
            ElementoLE actual = this.primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            return actual;
        }
    }

    public ElementoLE getSiguiente(ElementoLE el) {
        return el.getSiguiente();
    }

    public ElementoLE getElemento(int posicion) {
        if (isVacia()){
            System.out.println("La lista esta vacia, no contiene elementos");
            return null;
        } else {
            ElementoLE actual = this.primero;
            for (int i = 0; i != posicion; i++) {
                if (actual.getSiguiente() == null)  {
                    return null;
                }
                actual = actual.getSiguiente();
            }
            return actual;
        }
    }
}
