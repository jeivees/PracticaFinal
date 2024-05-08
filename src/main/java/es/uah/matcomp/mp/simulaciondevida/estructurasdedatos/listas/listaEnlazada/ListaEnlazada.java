package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada;

public class ListaEnlazada<TipoDato> {
    private ElementoLE<TipoDato> primero;
    public ListaEnlazada() {
        this.primero = null;
    }
    public Boolean isVacia() {
        return primero == null;
    }
    public void vaciar() {
        this.primero = null;
    }
    protected void add(ElementoLE<TipoDato> elem) {
        if (isVacia()) {
            this.primero = elem;
        }else{
            ElementoLE<TipoDato> actual = this.primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            elem.insertarmeEn(actual);
        }
    }
    public void add(TipoDato dato) {
        ElementoLE<TipoDato> el = new ElementoLE<>();
        el.setData(dato);
        add(el);
    }
    public ElementoLE<TipoDato> getElemento(int posicion) {
        if (isVacia()) {
            return null;
        }else{
            ElementoLE<TipoDato> first = this.primero;
            for (int i=0; i != posicion; i++) {
                first = first.getSiguiente();
            }
            return first;
        }
    }
    public void insert(TipoDato obj, int posicion) {
        ElementoLE<TipoDato> objeto = new ElementoLE<>();
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
            ElementoLE<TipoDato> first = primero;
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
            ElementoLE<TipoDato> first = this.primero;
            for (int i=0; i != pos - 1; i++) {
                first = first.getSiguiente();
                if (first.getSiguiente().getSiguiente() == null) {
                    first.setSiguiente(null);
                    return this.getNumeroElementos();
                }
            }
            first.setSiguiente(first.getSiguiente().getSiguiente());
            return this.getNumeroElementos();
        }
    }
    public Integer getPosicion(ElementoLE<TipoDato> el) {
        int pos = 0;
        ElementoLE<TipoDato> first = this.primero;
        for (int i = 0; (pos < getNumeroElementos()) && (first.getData() != el.getData()); i++) {
            first = first.getSiguiente();
            pos += 1;
        }
        if ((pos>=getNumeroElementos())&&(el.getData()!= getUltimo().getData())) {
            return null;
        }
        return pos;
    }
    public Integer getPosicion(TipoDato el) {
        if (!this.isVacia()) {
            int pos = 0;
            ElementoLE<TipoDato> first = this.primero;
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
    public ElementoLE<TipoDato> getPrimero() {
        if (isVacia()) {
            return null;
        }else{
            return this.primero;
        }
    }
    public ElementoLE<TipoDato> getUltimo() {
        if (this.isVacia()) {
            return null;
        }else{
            ElementoLE<TipoDato> first = this.primero;
            while (first.getSiguiente() != null) {
                first = first.getSiguiente();
            }
            return first;
        }
    }

    public ListaEnlazada<TipoDato> reverse(ListaEnlazada<TipoDato> lista) {
        ListaEnlazada<TipoDato> listaInvertida = new ListaEnlazada<>();
        if (lista.getNumeroElementos() <= 1) {
            return lista;
        }
        reverseRecursivo(lista, listaInvertida, 0);
        return listaInvertida;
    }

    private void reverseRecursivo(ListaEnlazada<TipoDato> lista, ListaEnlazada<TipoDato> listaInvertida, int index) {
        if (index == lista.getNumeroElementos() ) {
            return;
        }
        reverseRecursivo(lista, listaInvertida, index + 1);
        listaInvertida.add(lista.getElemento(index));
    }

    public ElementoLE<TipoDato> getSiguiente(ElementoLE<TipoDato> el) {
        return el.getSiguiente();
    }
    public String toString() {
        return "[" + toStrings(this.primero) + "]";
    }
    private String toStrings(ElementoLE<TipoDato> n) {
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
