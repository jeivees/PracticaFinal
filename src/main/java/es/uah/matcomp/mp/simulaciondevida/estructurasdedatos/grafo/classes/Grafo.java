package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.avl.exceptions.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.exceptions.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Grafo<T> {
    private static final Logger log = LogManager.getLogger(Grafo.class);

    private ListaDE<Nodo<T>> nodos = new ListaDE<>();
    private ListaDE<Arco<T>> arcos = new ListaDE<>();
    private boolean isDirigido;
    public Grafo(){}

    public Grafo(boolean isDirigido) {
        this.isDirigido = isDirigido;
    }
    public Grafo(ListaDE<Nodo<T>> nodos, ListaDE<Arco<T>> arcos){this.nodos= nodos; this.arcos=arcos;}

    public boolean isDirigido() {
        return isDirigido;
    }

    public void setDirigido(boolean dirigido) {
        isDirigido = dirigido;
        for (int i=0; i != arcos.getNumeroElementos(); i++) {
            arcos.getElemento(i).getData().setDirigido(dirigido);
        }
    }

    public Nodo<T> getNodo (T dato) {
        try {
            for (int i = 0; i != nodos.getNumeroElementos(); i++) {
                if (nodos.getElemento(i).getData().getDato().equals(dato)) return nodos.getElemento(i).getData();
            }
            throw new datoNoExistenteException();
        } catch (datoNoExistenteException e) {
            log.trace("Se ha intentado encontrar un nodo que no existe en el grafo");
            return null;
        }
    }

    public Arco<T> getArco (String etiqueta) {
        try {
            for (int i = 0; i != arcos.getNumeroElementos(); i++) {
                if (arcos.getElemento(i).getData().getEtiqueta().equals(etiqueta)) return arcos.getElemento(i).getData();
            }
            throw new datoNoExistenteException();
        } catch (datoNoExistenteException e) {
            log.trace("Se ha intentado encontrar un arco que no existe en el grafo");
            return null;
        }
    }

    public void addNodo (T nuevoDato) {
        Nodo<T> nodo = new Nodo<>(nuevoDato);
        nodos.add(nodo);
    }

    public void addNodo (Nodo<T> nodo) {
        nodos.add(nodo);
    }

    public void addNodo (Nodo<T> nodo, Nodo<T> nodoAConectar, double pesoArco) {
        nodos.add(nodo);
        addArco(pesoArco, nodo, nodoAConectar);
    }

    public void addArco (double peso, T datoInicial, T datoFinal, String etiqueta) {
        Nodo<T> nodoInicial = getNodo(datoInicial);
        Nodo<T> nodoFinal = getNodo(datoFinal);
        Arco<T> arco = new Arco<>(peso, nodoInicial, nodoFinal, etiqueta, this.isDirigido);
        if (isDirigido) {
            nodoFinal.getArcosLlegada().add(arco);
            nodoInicial.getArcosSalida().add(arco);
        } else {
            nodoFinal.getArcosSalida().add(arco);
            nodoInicial.getArcosSalida().add(arco);
        }
        arcos.add(arco);
    }

    public void addArco (double peso, Nodo<T> nodoInicial, Nodo<T> nodoFinal) {
        Arco<T> arco = new Arco<>(peso, nodoInicial, nodoFinal, this.isDirigido);
        if (isDirigido) {
            nodoFinal.getArcosLlegada().add(arco);
            nodoInicial.getArcosSalida().add(arco);
        } else {
            nodoFinal.getArcosSalida().add(arco);
            nodoInicial.getArcosSalida().add(arco);
        }
        arcos.add(arco);
    }

    public void addArco (double peso, T datoInicial, T datoFinal) {
        Nodo<T> nodoInicial = getNodo(datoInicial);
        Nodo<T> nodoFinal = getNodo(datoFinal);

        addArco(peso, nodoInicial, nodoFinal);
    }

    public void delNodo (T dato) {
        Nodo<T> nodo = getNodo(dato);
        for (int i=0; i <= nodos.getNumeroElementos(); i++) {
            if (nodos.getElemento(i).getData().getDato() == nodo.getDato()) {
                nodos.del(i);
            }
        }
    }

    public void delArco (String etiqueta) {
        Arco<T> arco = getArco(etiqueta);
        for (int i = 0; i <= arcos.getNumeroElementos(); i++) {
            if (arcos.getElemento(i).getData().getEtiqueta().equals(arco.getEtiqueta())) {
                arcos.del(i);
            }
        }
    }

    public Camino<T> getCaminoMinimo (Nodo<T> nodoInicial, Nodo<T> nodoFinal) {
        try {
            HashMap<Nodo<T>, Camino<T>> grafoMinimoMapa = dijkstra(nodoInicial);
            if (grafoMinimoMapa.get(nodoFinal) == null) throw new caminoNuloException();
            return grafoMinimoMapa.get(nodoFinal);
        } catch (caminoNuloException e) {
            log.warn("El nodo " + nodoFinal + " (" + nodoFinal.getDato() + ")" + " no existe o es inaccesible desde " + nodoInicial + " (" + nodoInicial.getDato() + ")");
            return null;
        }
    }
    
   private HashMap<Nodo<T>, Camino<T>> dijkstra (Nodo<T> nodoInicial) {
       HashMap<Nodo<T>, Double> distancias = new HashMap<>();
       Cola<Nodo<T>> colaPendientes = new Cola<>();
       HashMap<Nodo<T>, Nodo<T>> verticesAnteriores = new HashMap<>();

       this.dijkstra_init(nodoInicial,distancias,colaPendientes);
       this.dijkstra_calcula(distancias,colaPendientes,verticesAnteriores);
       return this.dijkstra_procesaResultados(distancias,verticesAnteriores);
   }

   private void dijkstra_init (Nodo<T> nodoInicial, HashMap<Nodo<T>, Double> distancias, Cola<Nodo<T>> colaPendientes) {
        for (int i=0; i != nodos.getNumeroElementos(); i++) {
            distancias.put(nodos.getElemento(i).getData(), Double.MAX_VALUE);
        }

        distancias.put(nodoInicial, 0.0);
        colaPendientes.add(nodoInicial);
   }

   private void dijkstra_calcula (HashMap<Nodo<T>, Double> distancias, Cola<Nodo<T>> colaPendientes, HashMap<Nodo<T>, Nodo<T>> verticesAnteriores) {
        while (!colaPendientes.isVacia()) {
            Nodo<T> nodoActual = colaPendientes.poll();
            for (int i=0; i != nodoActual.getArcosSalida().getNumeroElementos(); i++) {
                Nodo<T> nodoAnexo = nodoActual.getArcosSalida().getElemento(i).getData().getOtroVertice(nodoActual);
                double calculoDistancia = distancias.get(nodoActual) + nodoActual.getArcosSalida().getElemento(i).getData().getPeso();

                if (calculoDistancia < distancias.get(nodoAnexo)) {
                    distancias.put(nodoAnexo, calculoDistancia);
                    verticesAnteriores.put(nodoAnexo, nodoActual);
                    colaPendientes.add(nodoAnexo);
                }
            }
        }
   }

   private HashMap<Nodo<T>, Camino<T>> dijkstra_procesaResultados (HashMap<Nodo<T>, Double> distancias, HashMap<Nodo<T>, Nodo<T>> verticesAnteriores) {
        HashMap<Nodo<T>, Camino<T>> caminos = new HashMap<>();

        for (int i=0; i != verticesAnteriores.KeySet().getNumeroElementos(); i++) {
            ListaDE<Nodo<T>> caminoCalculado = new ListaDE<>();
            Nodo<T> vertice = verticesAnteriores.KeySet().getElemento(i).getData();
            Nodo<T> v = vertice;
            while (v != null) {
                caminoCalculado.insert(v, 0);
                v = verticesAnteriores.get(v);
            }
            caminos.put(vertice, new Camino<>(caminoCalculado, distancias.get(vertice)));
        }
        return caminos;
   }

    public String listaToString (ListaDE<Nodo<T>> lista) {
        StringBuilder cadena = new StringBuilder("[");
        if (lista.getNumeroElementos() != 0) {
            cadena.append(lista.getPrimero().getData().getDato());
            for (int i = 1; i != lista.getNumeroElementos(); i++) {
                cadena.append(", ").append(lista.getElemento(i).getData().getDato().toString());
            }
        }
        cadena.append("]");
        return cadena.toString();
    }
}