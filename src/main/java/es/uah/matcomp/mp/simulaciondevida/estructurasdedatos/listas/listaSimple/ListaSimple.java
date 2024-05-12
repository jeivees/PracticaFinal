package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple;

public class ListaSimple<T> {
    private ElementoLS<T>[] datos;
    private int maximo;

    public ListaSimple(int m) {
        this.maximo = m;
        this.datos = new ElementoLS[m];
    }

    public Boolean isVacia() {
        return this.datos[0] == null;
    }

    public void vaciar() {
        this.datos = new ElementoLS[maximo];
    }

    protected int add(ElementoLS<T> el) {
        int pos = 0;
        for (int i = 0; this.datos[i] != null; i++) {
            pos += 1;
        }
        this.datos[pos] = el;
        return pos;
    }

    public void add(T o) {
        ElementoLS<T> e = new ElementoLS<>();
        e.setData(o);
        add(e);
    }

    public void insert(T o, int posicion) {
        ElementoLS<T> e = new ElementoLS<>();
        e.setData(o);
        ElementoLS<T> actual = this.datos[posicion];
        for (int i = posicion; this.datos[i] != null; i++) {
            ElementoLS<T> siguiente = this.datos[i + 1];
            this.datos[i + 1] = actual;
            actual = siguiente;
        }
        this.datos[posicion] = e;
    }

    public int del(int posicion) {
        this.datos[posicion] = null;
        int ultimo = 0;
        for (int i = posicion + 1; this.datos[i] != null; i++) {
            this.datos[i - 1] = this.datos[i];
            ultimo += 1;
        }
        return ultimo + posicion - 2;
    }

    public int getNumeroElementos() {
        int elms = 0;
        for (int i = 0; this.datos[i] != null; i++) {
            elms += 1;
            if (i + 1 == maximo) return elms;
        }
        return elms;
    }

    public int getPosicion(ElementoLS<T> el) {
        int pos = 0;
        for (int i = 0; this.datos[i].getData() != el.getData(); i++) {
            pos += 1;
        }
        return pos;
    }

    public ElementoLS<T> getPrimero() {
        return this.datos[0];
    }

    public ElementoLS<T> getUltimo() {
        int pos = getNumeroElementos();
        return this.datos[pos - 1];
    }

    protected ElementoLS<T> getSiguiente(ElementoLS<T> el) {
        int pos = getPosicion(el);
        return this.datos[pos + 1];
    }

    public ElementoLS<T> getElemento(int posicion) {
        return this.datos[posicion];
    }

    public void setElemento(int posicion, T elemento) {
        ElementoLS<T> e = new ElementoLS<>(elemento);
        datos[posicion] = e;
    }
}
