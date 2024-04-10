package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arbol;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arbol.exceptions.datoNoExistenteException;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arbol.exceptions.mismoDatoException;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;

public class ArbolBinarioDeBusqueda<T> {
    private Nodo<T> raiz;
    public ArbolBinarioDeBusqueda(){}
    public ArbolBinarioDeBusqueda(T raiz){
        this.raiz = new Nodo<>(raiz);
    }
    public ArbolBinarioDeBusqueda(Nodo<T> raiz){
        this.raiz = raiz;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public void add(T dato) {
        try {
            Nodo<T> nuevoNodo = new Nodo<>(dato);
            if (getAltura() == 0) {
                this.raiz = nuevoNodo;
            } else {
                addAux(this.raiz, nuevoNodo);
            }
        } catch (mismoDatoException e) {
            System.out.println("El dato que estas intentando introducir ya existe");
        }
    }
    private Nodo<T> addAux (Nodo<T> raiz, Nodo<T> dato) throws mismoDatoException{
        Comparable r = (Comparable) raiz.getDato();
        Comparable d = (Comparable) dato.getDato();

        if (d.compareTo(r) == 0) {
            throw new mismoDatoException();
        } else if (d.compareTo(r) < 0) {
            if (raiz.getIzquierda() == null) {
                raiz.setIzquierda(dato);
                return raiz;
            } else {
                return addAux(raiz.getIzquierda(),dato);
            }
        } else {
            if (raiz.getDerecha() == null) {
                raiz.setDerecha(dato);
                return raiz;
            } else {
                return addAux(raiz.getDerecha(),dato);
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
        ListaDE<T> lista = new ListaDE<T>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaPreOrdenAux(this.raiz,lista);
        return lista;
    }

    public ListaDE<T> getListaPreOrdenAux(Nodo<T> nodo, ListaDE<T> lista){
        if ((nodo != null)){
            lista.add(nodo.getDato());
            getListaPreOrdenAux(nodo.getIzquierda(), lista);
            getListaPreOrdenAux(nodo.getDerecha(), lista);
        }
        return lista;
    }
    public ListaDE<T> getListaPostOrden() {
        ListaDE<T> lista = new ListaDE<T>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaPostOrdenAux(this.raiz,lista);
        return lista;
    }
    public ListaDE<T> getListaPostOrdenAux(Nodo<T> nodo, ListaDE<T> lista){
        if ((nodo != null)){
            getListaPostOrdenAux(nodo.getIzquierda(), lista);
            getListaPostOrdenAux(nodo.getDerecha(), lista);
            lista.add(nodo.getDato());
        }
        return lista;
    }
    public ListaDE<T> getListaOrdenCentral() {
        ListaDE<T> lista = new ListaDE<T>(); // ya que no sabemos el numero de elementos que va a tener la lista empleamos una LinkedList
        lista = getListaOrdenCentralAux(this.raiz,lista);
        return lista;
    }
    public ListaDE<T> getListaOrdenCentralAux(Nodo<T> nodo, ListaDE<T> lista){
        if((nodo != null)){
            getListaOrdenCentralAux(nodo.getIzquierda(), lista);
            lista.add(nodo.getDato());
            getListaOrdenCentralAux(nodo.getDerecha(), lista);
        }
        return lista;
    }

    public int getGradoNodo(Nodo<T> nodo) {
        int GradoIzq = 0;
        int GradoDcha = 0;
        int GradoNodo = 0;
        if (nodo.getIzquierda() != null && nodo.getDerecha() != null) {
            GradoNodo = 2;
            GradoIzq = getGradoNodo(nodo.getIzquierda());
            GradoDcha = getGradoNodo(nodo.getDerecha());
        } else if (nodo.getIzquierda() != null && nodo.getDerecha() == null) {
            GradoNodo = 1;
            GradoIzq = getGradoNodo(nodo.getIzquierda());
        } else if (nodo.getIzquierda() == null && nodo.getDerecha() != null) {
            GradoNodo = 1;
            GradoDcha = getGradoNodo(nodo.getDerecha());
        }
        return Math.max(GradoIzq,Math.max(GradoDcha,GradoNodo));
    }
    public int getGrado(){
        if (getAltura() == 0) return 0;
        else {
            return getGradoNodo(this.raiz);
        }
    }

    public int getAltura() { // Hay que generalizar a la altura de cualquier nodo?
        return getAlturaAux(this.raiz);
    }
    private int getAlturaAux(Nodo<T> nodo) {
        if (nodo == null) return 0;
        int alturaIzq = getAlturaAux(nodo.getIzquierda()) + 1;
        int alturaDcha = getAlturaAux(nodo.getDerecha()) + 1;
        return Math.max(alturaIzq,alturaDcha);
    }

    public ListaDE<Nodo<T>> getListaDatosNivel (int nivel) {
        ListaDE<Nodo<T>> lista = new ListaDE<Nodo<T>>();
        getListaDatosNivelAux(this.raiz,nivel,lista);
        return lista;
    }
    private void getListaDatosNivelAux (Nodo<T> nodo, int nivel, ListaDE<Nodo<T>> lista) {
        if (nodo == null) return;
        if (nivel == 1) {
            lista.add(nodo);
            return;
        }
        getListaDatosNivelAux(nodo.getIzquierda(),nivel - 1, lista);
        getListaDatosNivelAux(nodo.getDerecha(),nivel - 1, lista);
    }

    public Boolean isArbolHomogeneo () {
        return isArbolHomogeneoAux(this.raiz);
    }
    private Boolean isArbolHomogeneoAux (Nodo<T> nodo) {
        if (nodo == null || (nodo.getDerecha() != null && nodo.getIzquierda() != null)) {
            return true;
        }
        if (nodo.getDerecha() == null || nodo.getIzquierda() == null) {
            return false;
        }
        return isArbolHomogeneoAux(nodo.getDerecha()) && isArbolHomogeneoAux(nodo.getIzquierda());
    }
    public Boolean isArbolCompleto () {
        return isArbolCompletoAux(getAltura());
    }
    private Boolean isArbolCompletoAux (int nivel) {
        if (nivel == 0) {
            return true;
        }
        if (getListaDatosNivel(nivel).getNumeroElementos() != Math.pow(2,nivel - 1)) {
            return false;
        }
        return isArbolCompletoAux(nivel - 1);
    }

    public Boolean isArbolCasiCompleto() {
        if (!isArbolCompletoAux(getAltura() - 1)) {
            return false;
        } else {
            ListaDE<Nodo<T>> lista = getListaDatosNivel(getAltura()-1);
            return isArbolCasiCompletoAux1(lista);
        }
    }
    private Boolean isArbolCasiCompletoAux1 (ListaDE<Nodo<T>> lista) {
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
    private Boolean isArbolCasiCompletoAux2 (ListaDE<Nodo<T>> lista) {
        if (lista.isVacia()) return true;
        ArbolBinarioDeBusqueda<T> arbolNodo = new ArbolBinarioDeBusqueda<>(lista.getPrimero().getData().getDato());
        if (arbolNodo.getGrado() != 0) {
            return false;
        } else {
            lista.del(0);
            return isArbolCasiCompletoAux2(lista);
        }
    }

    public ListaDE<Nodo<T>> getCamino(T dato){
        try {
            ListaDE<Nodo<T>> camino = new ListaDE<Nodo<T>>();
            ListaDE<Nodo<T>> caminoEncontrado = getCaminoAux(this.raiz, dato, camino);
            if (caminoEncontrado.isVacia()) throw new datoNoExistenteException();
            else return caminoEncontrado;
        } catch (datoNoExistenteException e) {
            System.out.println("El dato que estas buscando no existe");
            return null;
        }
    }

    private ListaDE<Nodo<T>> getCaminoAux (Nodo<T> raiz, T dato, ListaDE<Nodo<T>> camino) {
        if (raiz == null) return camino;
        getCaminoAux(raiz.getIzquierda(),dato, camino);
        if (camino.isVacia()) getCaminoAux(raiz.getDerecha(),dato, camino);
        if (raiz.getDato() == dato || !camino.isVacia()) camino.insert(raiz,0);
        return camino;
    }
}
