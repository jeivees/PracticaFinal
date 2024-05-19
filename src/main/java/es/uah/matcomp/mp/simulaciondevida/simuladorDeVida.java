package es.uah.matcomp.mp.simulaciondevida;

import es.uah.matcomp.mp.simulaciondevida.control.bucleDeControl;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.ArbolBinario;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.arboles.bst.nodoBST;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola.Cola;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.HashMap;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import excepciones.numeroPadresInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class simuladorDeVida {
    private static final Logger log = LogManager.getLogger();
    private tablero tablero;
    private bucleDeControl bucle;
    private DataModel model;
    private HashMap<individuo, ArbolBinario<individuo>> arbolesGenealogicos = new HashMap<>();
    private Grafo<String> grafoAcciones = new Grafo<>();

    public simuladorDeVida (DataModel model) {
        this.model = model;
        model.setJuegoActual(this);
        tablero = new tablero(model.getFilasTablero(), model.getColumnasTablero(), model);
        bucle = new bucleDeControl(tablero, model);
    }

    public simuladorDeVida (DataModel model, tablero tablero) {
        this.model = model;
        model.setJuegoActual(this);
        bucle = new bucleDeControl(tablero, model);
    }

    public void comenzar (boolean unTurno) {
        if (unTurno) {
            bucle.setUnTurno(true);
        } else {
            bucle.setUnTurno(false);
        }
        Thread threadBucle = new Thread(bucle);
        threadBucle.start();
    }

    public void finalizarPartida () {
        arbolesGenealogicos = crearArbolesGenealogicos();
        grafoAcciones = crearGrafoAcciones();
    }

    private HashMap<individuo, ArbolBinario<individuo>> crearArbolesGenealogicos () {
        HashMap<individuo, ArbolBinario<individuo>> arbolesGenealogicos = new HashMap<>();

        int totalIndividuos = model.getIndividuos().getNumeroElementos();
        for (int i = 0; i != totalIndividuos; i ++) {
            individuo individuoActual = model.getIndividuos().getElemento(i).getData();
            ArbolBinario<individuo> arbolGenealogico = new ArbolBinario<>(individuoActual);

            añadirPadres(arbolGenealogico.getRaiz());
            arbolesGenealogicos.put(individuoActual, arbolGenealogico);
        }
        return arbolesGenealogicos;
    }

    private void añadirPadres (nodoBST<individuo> hijo) {
        try {
            if (!hijo.getDato().getPadres().isVacia()) {
                if (hijo.getDato().getPadres().getNumeroElementos() != 2) throw new numeroPadresInvalidoException();
                hijo.setDerecha(new nodoBST<>(hijo.getDato().getPadres().getPrimero().getData()));
                hijo.setIzquierda(new nodoBST<>(hijo.getDato().getPadres().getElemento(1).getData()));

                añadirPadres(hijo.getDerecha());
                añadirPadres(hijo.getIzquierda());
            }
        } catch (numeroPadresInvalidoException e) {
            log.error("El número de padres no es 2");
        }
    }

    private Grafo<String> crearGrafoAcciones () {
        Grafo<String> grafoAcciones = new Grafo<>(false);
        Nodo<String> accion = new Nodo<>("accion");
        grafoAcciones.addNodo(accion);

        grafoAcciones.addNodo(new Nodo<>("nacer"), accion, 1);
        grafoAcciones.addNodo(new Nodo<>("morir"), accion, 1);

        Nodo<String> aplicarRecurso = new Nodo<>("aplicarRecurso");
        grafoAcciones.addNodo(aplicarRecurso, accion, 1);
        grafoAcciones.addNodo(new Nodo<>("agua"), aplicarRecurso, 1);
        grafoAcciones.addNodo(new Nodo<>("comida"), aplicarRecurso, 1);
        grafoAcciones.addNodo(new Nodo<>("montaña"), aplicarRecurso, 1);
        grafoAcciones.addNodo(new Nodo<>("actualizarTV"), accion, 1);
        grafoAcciones.addNodo(new Nodo<>("tesoro"), aplicarRecurso, 1);
        grafoAcciones.addNodo(new Nodo<>("actualizarPR"), accion, 1);
        grafoAcciones.addNodo(new Nodo<>("biblioteca"), aplicarRecurso, 1);
        grafoAcciones.addNodo(new Nodo<>("actualizarPC"), accion, 1);
        grafoAcciones.addNodo(new Nodo<>("pozo"), aplicarRecurso, 1);

        grafoAcciones.addNodo(new Nodo<>("moverse"), accion, 1);

        grafoAcciones.addNodo(new Nodo<>("reproducirse"), accion, 1);
        grafoAcciones.addNodo(new Nodo<>("clonarse"), accion, 1);


        try {
            int totalIndividuos = model.getHistorialIndividuos().getNumeroElementos();
            for (int i = 0; i != totalIndividuos; i++) {
                individuo individuoActual = model.getHistorialIndividuos().getElemento(i).getData();
                Nodo<String> nodoIndividuo = new Nodo<>(STR."Individuo \{individuoActual.getId()}");
                Cola<String> accionesIndividuo = individuoActual.getAcciones();
                int numeroAcciones = accionesIndividuo.getNumeroElementos();
                for (int j = 0; j != numeroAcciones; j++) {
                    String accionActual = accionesIndividuo.poll();
                    Nodo<String> nodoAccion = new Nodo<>(accionActual);

                    grafoAcciones.addArco(1, nodoAccion, nodoIndividuo); // añadir arco de la accion al individuo

                    int indexOfFinAccion = accionActual.indexOf(",");
                    String accionReducida = accionActual.substring(8, indexOfFinAccion);
                    grafoAcciones.addArco(1, nodoAccion, grafoAcciones.getNodo(accionReducida)); // añadir arco de la accion al tipo de la acción

                    int indexOfInicioTurno = accionActual.indexOf("turno:") + 7;
                    String turno = accionActual.substring(indexOfInicioTurno);
                    Nodo<String> nodoTurno = grafoAcciones.getNodo(STR."Turno \{turno}");
                    if (nodoTurno == null) {
                        grafoAcciones.addNodo(new Nodo<>(STR."Turno \{turno}"), nodoAccion, 1); // crear nodo de ese turno y añadir arco de la accion al turno
                    } else {
                        grafoAcciones.addArco(1, nodoAccion, nodoTurno); // añadir arco de la accion al turno
                    }
                }
            }
        } catch (Exception e) {
            log.error("No se ha encontrado el nodo correcto al que conectar la acción");
            e.printStackTrace();
        }
        return grafoAcciones;
    }

    public tablero getTablero() {
        return tablero;
    }

    public void setTablero(tablero tablero) {
        this.tablero = tablero;
    }

    public bucleDeControl getBucle() {
        return bucle;
    }

    public void setBucle(bucleDeControl bucle) {
        this.bucle = bucle;
    }

    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel model) {
        this.model = model;
    }

    public HashMap<individuo, ArbolBinario<individuo>> getArbolesGenealogicos() {
        return arbolesGenealogicos;
    }
}
