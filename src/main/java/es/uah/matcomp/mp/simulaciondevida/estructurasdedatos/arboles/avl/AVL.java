package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.avl;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.avl.exceptions.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AVL<T> extends ArbolBinario<T> {
    private static final Logger log = LogManager.getLogger();
    private nodoAVL<T> raiz;
    public AVL () {
        super();
    }
    public AVL (T dato) {
        this.raiz= new nodoAVL<>(dato);
    }
    public AVL (nodoAVL<T> nodo) {
        this.raiz=nodo;
    }

    @Override
    public nodoAVL<T> getRaiz () {
        return raiz;
    }
    @Override
    public AVL<T> getSubArbolIzquierda() {
        if (this.raiz.getIzquierda() == null) {
            return null;
        } else {
            return new AVL<>(this.raiz.getIzquierda());
        }
    }

    @Override
    public AVL<T> getSubArbolDerecha() {
        if (this.raiz.getDerecha() == null) {
            return null;
        } else {
            return new AVL<>(this.raiz.getDerecha());
        }
    }

    @Override
    public int getAltura () {
        return raiz.getAltura();
    }

    @Override
    public ListaDE<T> getListaPreOrden() {
        ListaDE<T> lista = new ListaDE<>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaPreOrdenAux(this.raiz, lista);
        return lista;
    }

    public ListaDE<T> getListaPreOrdenAux(nodoAVL<T> nodoAVL, ListaDE<T> lista) {
        if ((nodoAVL != null)) {
            lista.add(nodoAVL.getDato());
            getListaPreOrdenAux(nodoAVL.getIzquierda(), lista);
            getListaPreOrdenAux(nodoAVL.getDerecha(), lista);
        }
        return lista;
    }

    @Override
    public ListaDE<T> getListaPostOrden() {
        ListaDE<T> lista = new ListaDE<>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaPostOrdenAux(this.raiz, lista);
        return lista;
    }

    public ListaDE<T> getListaPostOrdenAux(nodoAVL<T> nodoAVL, ListaDE<T> lista) {
        if ((nodoAVL != null)) {
            getListaPostOrdenAux(nodoAVL.getIzquierda(), lista);
            getListaPostOrdenAux(nodoAVL.getDerecha(), lista);
            lista.add(nodoAVL.getDato());
        }
        return lista;
    }

    @Override
    public ListaDE<T> getListaOrdenCentral() {
        ListaDE<T> lista = new ListaDE<>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaOrdenCentralAux(this.raiz, lista);
        return lista;
    }

    public ListaDE<T> getListaOrdenCentralAux(nodoAVL<T> nodoAVL, ListaDE<T> lista) {
        if ((nodoAVL != null)) {
            getListaOrdenCentralAux(nodoAVL.getIzquierda(), lista);
            lista.add(nodoAVL.getDato());
            getListaOrdenCentralAux(nodoAVL.getDerecha(), lista);
        }
        return lista;
    }

    public ListaDE<nodoAVL<T>> getcamino(T dato) {
        try {
            ListaDE<nodoAVL<T>> camino = new ListaDE<>();
            ListaDE<nodoAVL<T>> caminoEncontrado = getCaminoAux(this.raiz, dato, camino);
            if (caminoEncontrado.isVacia()) throw new datoNoExistenteException();
            else return caminoEncontrado;
        } catch (datoNoExistenteException e) {
            log.warn("Se esta intentando encontrar un camino y alguno de los nodos no existe");
            return null;
        }
    }

    private ListaDE<nodoAVL<T>> getCaminoAux(nodoAVL<T> raiz, T dato, ListaDE<nodoAVL<T>> camino) {
        if (raiz == null) return camino;
        getCaminoAux(raiz.getIzquierda(), dato, camino);
        if (camino.isVacia()) getCaminoAux(raiz.getDerecha(), dato, camino);
        if (raiz.getDato() == dato || !camino.isVacia()) camino.insert(raiz, 0);
        return camino;
    }

    public int getGradoNodo (nodoAVL<T> nodo) {
        return super.getGradoNodo(nodo);
    }

    public int getAlturaNodo (nodoAVL<T> nodo) {
        return nodo.getAltura();
    }

    private void actualizarAltura (nodoAVL<T> nodo) {
        if (nodo != null) {
            int alturaIzq;
            int alturaDcha;
            if (nodo.getIzquierda() != null) {
                alturaIzq = nodo.getIzquierda().getAltura();
            } else {
                alturaIzq = -1;
            }
            if (nodo.getDerecha() != null) {
                alturaDcha = nodo.getDerecha().getAltura();
            } else {
                alturaDcha = -1;
            }
            nodo.setAltura(Math.max(alturaIzq, alturaDcha) + 1);
        }
    }

    private nodoAVL<T> rotar_s (nodoAVL<T> raiz, Boolean izq) {
        if (izq) {
            nodoAVL<T> nuevoNodoDcha = new nodoAVL<>(raiz.getDato());
            nuevoNodoDcha.setIzquierda(raiz.getIzquierda().getDerecha());
            nuevoNodoDcha.setDerecha(raiz.getDerecha());
            raiz = raiz.getIzquierda();
            raiz.setDerecha(nuevoNodoDcha);
        } else {
            nodoAVL<T> nuevoNodoIzq = new nodoAVL<>(raiz.getDato());
            nuevoNodoIzq.setDerecha(raiz.getDerecha().getIzquierda());
            nuevoNodoIzq.setIzquierda(raiz.getIzquierda());
            raiz = raiz.getDerecha();
            raiz.setIzquierda(nuevoNodoIzq);
        }
        actualizarAltura(raiz);
        actualizarAltura(raiz.getIzquierda());
        return raiz;
    }

    private nodoAVL<T> rotar_d (nodoAVL<T> raiz, Boolean izq) {
        if (izq) {
            raiz.setIzquierda(rotar_s(raiz.getIzquierda(), false));
            raiz = rotar_s(raiz, true);
        } else {
            raiz.setDerecha(rotar_s(raiz.getDerecha(), true));
            raiz = rotar_s(raiz, false);
        }
        return raiz;
    }

    public void balancear () {
        try {
            if (getAltura() > -1) {
                int alturaSubIzq;
                int alturaSubDcha;
                if (getSubArbolIzquierda() != null) {
                    alturaSubIzq = getSubArbolIzquierda().getAltura();
                } else {
                    alturaSubIzq = -1;
                }
                if (getSubArbolDerecha() != null) {
                    alturaSubDcha = getSubArbolDerecha().getAltura();
                } else {
                    alturaSubDcha = -1;
                }
                if (alturaSubIzq - alturaSubDcha == 2) {
                    if(getSubArbolIzquierda().getSubArbolDerecha() == null) {
                        raiz = rotar_s(raiz, true);
                    } else if (getSubArbolIzquierda().getSubArbolIzquierda() == null) {
                        raiz = rotar_d(raiz, true);
                    } else if (getSubArbolIzquierda().getSubArbolIzquierda().getAltura() >= getSubArbolIzquierda().getSubArbolDerecha().getAltura()) {
                        raiz = rotar_s(raiz, true);
                    } else {
                        raiz = rotar_d(raiz, true);
                    }
                } else if (alturaSubDcha - alturaSubIzq == 2) {
                    if (getSubArbolDerecha().getSubArbolIzquierda() == null) {
                        raiz = rotar_s(raiz, false);
                    } else if (getSubArbolDerecha().getSubArbolDerecha() == null) {
                        raiz = rotar_d(raiz, true);
                    } else if (getSubArbolDerecha().getSubArbolDerecha().getAltura() >= getSubArbolDerecha().getSubArbolIzquierda().getAltura()) {
                        raiz = rotar_s(raiz, false);
                    } else {
                        raiz = rotar_d(raiz, false);
                    }
                } else if (Math.abs(alturaSubDcha - alturaSubIzq) > 2) throw new desbalanceoMayorQueDosException();
            }
        } catch (desbalanceoMayorQueDosException e) {log.error("Se esta intentando equilibrar un desbalance de mas de dos de altura");}
    }

    @Override
    public void add (T dato) {
        nodoAVL<T> nuevoNodoAVL = new nodoAVL<>(dato);
        add(nuevoNodoAVL);
    }
    public void add (nodoAVL<T> nuevoNodoAVL) {
        if (getAltura() == -1) {
            raiz = nuevoNodoAVL;
        } else {
            addAux(raiz, nuevoNodoAVL);
        }
    }

    public void addAux (nodoAVL<T> raiz, nodoAVL<T> dato) {
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
        actualizarAltura(raiz);
        this.balancear();
    }

    public void del (T dato) {
        raiz = delAux(raiz, dato);
    }

    private nodoAVL<T> delAux (nodoAVL<T> raiz, T dato) {
        Comparable r = (Comparable) raiz.getDato();
        Comparable d = (Comparable) dato;
        if (d.compareTo(r) < 0) {
            raiz.setIzquierda(delAux(raiz.getIzquierda(), dato));
        } else if (d.compareTo(r) > 0){
            raiz.setDerecha(delAux(raiz.getDerecha(), dato));
        } else {
            if (raiz.getDerecha() == null && raiz.getIzquierda() == null) raiz = null;
            else if (raiz.getIzquierda() == null) raiz = raiz.getDerecha();
            else if (raiz.getDerecha() == null) raiz = raiz.getIzquierda();
            else {
                raiz = delAux2(raiz.getDerecha());
            }
        }
        actualizarAltura(raiz);
        this.balancear();
        return raiz;
    }

    private nodoAVL<T> delAux2 (nodoAVL<T> raiz) {
        if (raiz.getIzquierda() != null) {
             nodoAVL<T> devolverNodo = delAux2(raiz.getIzquierda());
             AVL<T> arbolRaiz = new AVL<>(raiz);
             arbolRaiz.balancear();
             actualizarAltura(raiz);
             return devolverNodo;
        } else {
            nodoAVL<T> devolverNodo = raiz;
            raiz = raiz.getDerecha();
            AVL<T> arbolRaiz = new AVL<>(raiz);
            arbolRaiz.balancear();
            actualizarAltura(raiz);
            return devolverNodo;
        }
    }
}