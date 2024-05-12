package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Arco<T> {
    private static final Logger log = LogManager.getLogger(Grafo.class);
    private double peso;
    private String etiqueta;
    private boolean isDirigido;
    private Nodo<T> nodoInicial;
    private Nodo<T> nodoFinal;

    public Arco(double peso, Nodo<T> nodoInicial, Nodo<T> nodoFinal, boolean isDirigido) {
        this.peso = peso;
        this.isDirigido = isDirigido;
        this.nodoInicial = nodoInicial;
        this.nodoFinal = nodoFinal;
    }
    public Arco(double peso, Nodo<T> nodoInicial, Nodo<T> nodoFinal, String etiqueta, boolean isDirigido) {
        this.peso = peso;
        this.isDirigido = isDirigido;
        this.nodoInicial = nodoInicial;
        this.nodoFinal = nodoFinal;
        this.etiqueta = etiqueta;
    }

    public Arco(double peso) {
        this.peso = peso;
    }

    public Arco(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public boolean isDirigido() {
        return isDirigido;
    }

    public void setDirigido(boolean dirigido) {
        this.isDirigido = dirigido;
    }
    public Nodo<T> getNodoInicial() {
        return nodoInicial;
    }

    public void setNodoInicial(Nodo<T> nodoInicial) {
        if (nodoInicial != null) this.nodoInicial = nodoInicial;
        else log.warn("Se ha intentado establecer un nodo nulo como inicial del arco: " + this);
    }

    public Nodo<T> getNodoFinal() {
        return nodoFinal;
    }

    public void setNodoFinal(Nodo<T> nodoFinal) {
        if (nodoFinal != null) this.nodoFinal = nodoFinal;
        else log.warn("Se ha intentado establecer un nodo nulo como final del arco: " + this);
    }

    public Nodo<T> getOtroVertice (Nodo<T> verticeConocido) {
        if (verticeConocido == nodoInicial) {
            return nodoFinal;
        } else if (verticeConocido == nodoFinal){
            return nodoInicial;
        } else {
            return null;
        }
    }
}
