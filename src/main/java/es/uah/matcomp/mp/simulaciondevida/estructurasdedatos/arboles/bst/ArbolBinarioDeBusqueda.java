package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.exceptions.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;

public class ArbolBinarioDeBusqueda<T> {
    private nodoBST<T> raiz;

    public ArbolBinarioDeBusqueda() {
    }

    public ArbolBinarioDeBusqueda(T raiz) {
        this.raiz = new nodoBST<>(raiz);
    }

    public ArbolBinarioDeBusqueda(nodoBST<T> raiz) {
        this.raiz = raiz;
    }

    public nodoBST<T> getRaiz() {
        return raiz;
    }

    public void add (T dato) {
        nodoBST<T> nuevoNodoBST = new nodoBST<>(dato);
        add(nuevoNodoBST);
    }
    public void add(nodoBST<T> nuevoNodoBST) {
            if (getAltura() == -1) {
                raiz = nuevoNodoBST;
            } else {
                addAux(raiz, nuevoNodoBST);
            }
    }

    private void addAux(nodoBST<T> raiz, nodoBST<T> dato) {
        Comparable r = (Comparable) raiz.getDato();
        Comparable d = (Comparable) dato.getDato();

        if (d.compareTo(r) < 0) {
            if (raiz.getIzquierda() == null) {
                raiz.setIzquierda(dato);
            } else {
                addAux(raiz.getIzquierda(), dato);
            }
        } else {
            if (raiz.getDerecha() == null) {
                raiz.setDerecha(dato);
            } else {
                addAux(raiz.getDerecha(), dato);
            }
        }
    }

    public ArbolBinarioDeBusqueda<T> getSubArbolIzquierda() {
        return new ArbolBinarioDeBusqueda<>(this.raiz.getIzquierda());
    }

    public ArbolBinarioDeBusqueda<T> getSubArbolDerecha() {
        return new ArbolBinarioDeBusqueda<>(this.raiz.getDerecha());
    }


    public ListaDE<T> getListaPreOrden() {
        ListaDE<T> lista = new ListaDE<>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaPreOrdenAux(this.raiz, lista);
        return lista;
    }

    public ListaDE<T> getListaPreOrdenAux(nodoBST<T> nodoBST, ListaDE<T> lista) {
        if ((nodoBST != null)) {
            lista.add(nodoBST.getDato());
            getListaPreOrdenAux(nodoBST.getIzquierda(), lista);
            getListaPreOrdenAux(nodoBST.getDerecha(), lista);
        }
        return lista;
    }

    public ListaDE<T> getListaPostOrden() {
        ListaDE<T> lista = new ListaDE<>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaPostOrdenAux(this.raiz, lista);
        return lista;
    }

    public ListaDE<T> getListaPostOrdenAux(nodoBST<T> nodoBST, ListaDE<T> lista) {
        if ((nodoBST != null)) {
            getListaPostOrdenAux(nodoBST.getIzquierda(), lista);
            getListaPostOrdenAux(nodoBST.getDerecha(), lista);
            lista.add(nodoBST.getDato());
        }
        return lista;
    }

    public ListaDE<T> getListaOrdenCentral() {
        ListaDE<T> lista = new ListaDE<>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaOrdenCentralAux(this.raiz, lista);
        return lista;
    }

    public ListaDE<T> getListaOrdenCentralAux(nodoBST<T> nodoBST, ListaDE<T> lista) {
        if ((nodoBST != null)) {
            getListaOrdenCentralAux(nodoBST.getIzquierda(), lista);
            lista.add(nodoBST.getDato());
            getListaOrdenCentralAux(nodoBST.getDerecha(), lista);
        }
        return lista;
    }

    public int getGradoNodo(nodoBST<T> nodoBST) {
        int GradoIzq = 0;
        int GradoDcha = 0;
        int GradoNodo = 0;
        if (nodoBST.getIzquierda() != null && nodoBST.getDerecha() != null) {
            GradoNodo = 2;
            GradoIzq = getGradoNodo(nodoBST.getIzquierda());
            GradoDcha = getGradoNodo(nodoBST.getDerecha());
        } else if (nodoBST.getIzquierda() != null && nodoBST.getDerecha() == null) {
            GradoNodo = 1;
            GradoIzq = getGradoNodo(nodoBST.getIzquierda());
        } else if (nodoBST.getIzquierda() == null && nodoBST.getDerecha() != null) {
            GradoNodo = 1;
            GradoDcha = getGradoNodo(nodoBST.getDerecha());
        }
        return Math.max(GradoIzq, Math.max(GradoDcha, GradoNodo));
    }

    public int getGrado() {
        if (getAltura() == -1) return 0;
        else {
            return getGradoNodo(this.raiz);
        }
    }

    public int getAltura() { // Hay que generalizar a la altura de cualquier nodo?
        return getAlturaNodo(this.raiz);
    }

    public int getAlturaNodo(nodoBST<T> nodoBST) {
        if (nodoBST == null) return - 1;
        int alturaIzq = getAlturaNodo(nodoBST.getIzquierda()) + 1;
        int alturaDcha = getAlturaNodo(nodoBST.getDerecha()) + 1;
        return Math.max(alturaIzq, alturaDcha);
    }

    public ListaDE<nodoBST<T>> getListaDatosNivel(int nivel) {
        ListaDE<nodoBST<T>> lista = new ListaDE<>();
        getListaDatosNivelAux(this.raiz, nivel, lista);
        return lista;
    }

    private void getListaDatosNivelAux(nodoBST<T> nodoBST, int nivel, ListaDE<nodoBST<T>> lista) {
        if (nodoBST == null) return;
        if (nivel == 1) {
            lista.add(nodoBST);
            return;
        }
        getListaDatosNivelAux(nodoBST.getIzquierda(), nivel - 1, lista);
        getListaDatosNivelAux(nodoBST.getDerecha(), nivel - 1, lista);
    }

    public Boolean isArbolHomogeneo() {
        return isArbolHomogeneoAux(this.raiz);
    }

    private Boolean isArbolHomogeneoAux(nodoBST<T> nodoBST) {
        if (nodoBST == null || (nodoBST.getDerecha() != null && nodoBST.getIzquierda() != null)) {
            return true;
        }
        if (nodoBST.getDerecha() == null || nodoBST.getIzquierda() == null) {
            return false;
        }
        return isArbolHomogeneoAux(nodoBST.getDerecha()) && isArbolHomogeneoAux(nodoBST.getIzquierda());
    }

    public Boolean isArbolCompleto() {
        return isArbolCompletoAux(getAltura());
    }

    private Boolean isArbolCompletoAux(int nivel) {
        if (nivel == 0) {
            return true;
        }
        if (getListaDatosNivel(nivel).getNumeroElementos() != Math.pow(2, nivel - 1)) {
            return false;
        }
        return isArbolCompletoAux(nivel - 1);
    }

    public Boolean isArbolCasiCompleto() {
        if (!isArbolCompletoAux(getAltura() - 1)) {
            return false;
        } else {
            ListaDE<nodoBST<T>> lista = getListaDatosNivel(getAltura() - 1);
            return isArbolCasiCompletoAux1(lista);
        }
    }

    private Boolean isArbolCasiCompletoAux1(ListaDE<nodoBST<T>> lista) {
        if (lista.isVacia()) {
            return false;
        }
        ArbolBinarioDeBusqueda<T> arbolNodo = new ArbolBinarioDeBusqueda<>(lista.getPrimero().getData());
        if (arbolNodo.getGrado() == 2) {
            lista.del(0);
            return isArbolCasiCompletoAux1(lista);
        } else if (arbolNodo.getGrado() == 1) {
            lista.del(0);
            return isArbolCasiCompletoAux2(lista);
        } else return false;
    }

    private Boolean isArbolCasiCompletoAux2(ListaDE<nodoBST<T>> lista) {
        if (lista.isVacia()) return true;
        ArbolBinarioDeBusqueda<T> arbolNodo = new ArbolBinarioDeBusqueda<>(lista.getPrimero().getData().getDato());
        if (arbolNodo.getGrado() != 0) {
            return false;
        } else {
            lista.del(0);
            return isArbolCasiCompletoAux2(lista);
        }
    }

    public ListaDE<nodoBST<T>> getCamino(T dato) {
        try {
            ListaDE<nodoBST<T>> camino = new ListaDE<>();
            ListaDE<nodoBST<T>> caminoEncontrado = getCaminoAux(this.raiz, dato, camino);
            if (caminoEncontrado.isVacia()) throw new datoNoExistenteException();
            else return caminoEncontrado;
        } catch (datoNoExistenteException e) {
            System.out.println("El dato que estas buscando no existe");
            return null;
        }
    }

    private ListaDE<nodoBST<T>> getCaminoAux(nodoBST<T> raiz, T dato, ListaDE<nodoBST<T>> camino) {
        if (raiz == null) return camino;
        getCaminoAux(raiz.getIzquierda(), dato, camino);
        if (camino.isVacia()) getCaminoAux(raiz.getDerecha(), dato, camino);
        if (raiz.getDato() == dato || !camino.isVacia()) camino.insert(raiz, 0);
        return camino;
    }
}
