package es.uah.matcomp.mp.simulaciondevida;

import es.uah.matcomp.mp.simulaciondevida.control.simuladorDeVida;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.Grafo;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.grafo.classes.Nodo;
import excepciones.argumentosInvalidosException;
import gui.mvc.javafx.practicafinal.DataModel;

import java.io.File;

public class informacionPartidaMain {
    public static void main(String[] args) {
        // Arguments: <nombreArchivo>
        // Acción syntax: [Acción: <accion> <optional:(extraData)>, turno: <turno>]
        try {
            if (args.length != 1) throw new argumentosInvalidosException();
            DataModel model = DataModel.cargar(STR."\{args[0]}.json");
            if (model == null) {
                System.out.println("El nombre del archivo no es válido, partidas existentes:");

                File carpetaDeArchivos = new File("archivosDePartida");
                File[] archivos = carpetaDeArchivos.listFiles();
                if (archivos != null) {
                    String[] nombresDeArchivos = new String[archivos.length];
                    for (int i = 0; i != archivos.length; i++) {
                        nombresDeArchivos[i] = archivos[i].getName();
                    }
                    System.out.println(nombresDeArchivos);
                } else {
                    System.out.println("No existen partidas guardadas");
                }
            } else {
                simuladorDeVida juegoActual = new simuladorDeVida(model, true);

                juegoActual.crearInfoPartida();
                Grafo<String> grafoAcciones = juegoActual.getGrafoAcciones();

                System.out.print("\nIndividuo más longevo: ");
                individuo individuoLongevo = getIndividuoLongevo(model, grafoAcciones);
                System.out.println(STR."Individuo \{individuoLongevo.getId()}");

                System.out.println("Acciones que ha realizado:");
                System.out.println(individuoLongevo.getAcciones());

                System.out.print("Clonaciones ocurridas: ");
                System.out.println(getNumeroAcciones(grafoAcciones, "clonarse"));

                System.out.print("Reproducciones ocurridas: ");
                System.out.println(getNumeroAcciones(grafoAcciones, "reproducirse"));

                System.out.print("Individuo con más reproducciones: ");
                System.out.println(STR."Individuo \{getIndividuoMasAccion(model, grafoAcciones, "reproducirse").getId()}");

                System.out.print("Individuo con más clonaciones: ");
                System.out.println(STR."Individuo \{getIndividuoMasAccion(model, grafoAcciones, "clonarse").getId()}");

                System.out.print("Individuo que ha bebido más agua: ");
                System.out.println(STR."Individuo \{getIndividuoMasAccion(model, grafoAcciones, "agua").getId()}");

                System.out.print("Individuo con máximo tiempo de vida: ");
                individuo individuoTVMaximo = getIndividuoTVMaximo(model, grafoAcciones);
                System.out.println(STR."Individuo \{individuoTVMaximo.getId()}, ha llegado a tener \{getTVMaximoIndividuo(grafoAcciones, individuoTVMaximo)} TV");
                if (individuoLongevo == individuoTVMaximo) {
                    System.out.println("El individuo más longevo y el que ha llegado a tener un TV máximo coinciden\n");
                } else {
                    System.out.println("El individuo más longevo y el que ha llegado a tener un TV máximo no coinciden\n");
                }
            }
        } catch (argumentosInvalidosException e) {
            System.out.println("Introduce un argumento y sólo uno con la ruta al archivo del que quieres obtener información");
        }
    }

    private static individuo getIndividuoLongevo (DataModel model, Grafo<String> grafoAcciones) {
        Nodo<String> nodoIndividuos = grafoAcciones.getNodo("individuos");
        int idIndividuoLongevo = -1;
        int turnosIndividuoLongevo = -1;

        int numeroIndividuos = nodoIndividuos.getArcosSalida().getNumeroElementos();
        for (int i = 0; i != numeroIndividuos; i ++) {
            Nodo<String> nodoIndividuoActual = nodoIndividuos.getArcosSalida().getElemento(i).getData().getOtroVertice(nodoIndividuos);
            int accionesIndividuo = nodoIndividuoActual.getArcosSalida().getNumeroElementos();

            int turnoNacimiento = 0;
            int turnoMuerte = model.getTurno();
            for (int j = 0; j != accionesIndividuo; j++) {
                String stringAccion = nodoIndividuoActual.getArcosSalida().getElemento(j).getData().getOtroVertice(nodoIndividuoActual).getDato();
                if (!stringAccion.equals("individuos")) {
                    int indexOfFinalAccion = Math.min(stringAccion.substring(8).indexOf(" ") + 8, stringAccion.indexOf(","));
                    String accionReducida = stringAccion.substring(8, indexOfFinalAccion);
                    if (accionReducida.equals("nacer")) {
                        int indexOfInicioTurno = stringAccion.indexOf("turno:") + 7;
                        turnoNacimiento = Integer.parseInt(stringAccion.substring(indexOfInicioTurno));
                    } else if (accionReducida.equals("morir")) {
                        int indexOfInicioTurno = stringAccion.indexOf("turno:") + 7;
                        turnoMuerte = Integer.parseInt(stringAccion.substring(indexOfInicioTurno));
                    }
                }
                if (turnoMuerte - turnoNacimiento > turnosIndividuoLongevo) {
                    turnosIndividuoLongevo = turnoMuerte - turnoNacimiento;
                    idIndividuoLongevo = Integer.parseInt(nodoIndividuoActual.getDato().substring(10));
                }
            }

        }

        individuo individuoLongevo = null;
        for (int k = 0; k != numeroIndividuos; k ++) {
            individuo individuoActual = model.getHistorialIndividuos().getElemento(k).getData();
            if (individuoActual.getId() == idIndividuoLongevo) {
                individuoLongevo = individuoActual;
            }
        }
        return individuoLongevo;
    }

    private static int getNumeroAcciones (Grafo<String> grafoAcciones, String accion) {
        Nodo<String> nodoAccion = grafoAcciones.getNodo(accion);
        return nodoAccion.getArcosSalida().getNumeroElementos();
    }

    private static individuo getIndividuoMasAccion (DataModel model, Grafo<String> grafoAcciones, String accion) {
        int idIndividuoMasAccion = -1;
        int accionesMaximas = -1;

        Nodo<String> nodoIndividuos = grafoAcciones.getNodo("individuos");
        int numeroIndividuos = nodoIndividuos.getArcosSalida().getNumeroElementos();
        for (int i = 0; i != numeroIndividuos; i ++) {
            Nodo<String> nodoIndividuoActual = nodoIndividuos.getArcosSalida().getElemento(i).getData().getOtroVertice(nodoIndividuos);
            int acciones = 0;
            int accionesIndividuo = nodoIndividuoActual.getArcosSalida().getNumeroElementos();
            for (int j = 0; j != accionesIndividuo; j ++) {
                if (nodoIndividuoActual.getArcosSalida().getElemento(j).getData().getOtroVertice(nodoIndividuoActual).getDato().equals(accion)) {
                    acciones += 1;
                }
            }
            if (acciones > accionesMaximas) {
                accionesMaximas = acciones;
                idIndividuoMasAccion = Integer.parseInt(nodoIndividuoActual.getDato().substring(10));
            }
        }
        individuo individuoMasAccion = null;
        for (int k = 0; k != numeroIndividuos; k ++) {
            individuo individuoActual = model.getHistorialIndividuos().getElemento(k).getData();
            if (individuoActual.getId() == idIndividuoMasAccion) {
                individuoMasAccion = individuoActual;
            }
        }
        return individuoMasAccion;
    }

    private static individuo getIndividuoTVMaximo (DataModel model, Grafo<String> grafoAcciones) {
        int idIndividuoTVMaximo = -1;
        int TVMaximo = -1;

        Nodo<String> nodoIndividuos = grafoAcciones.getNodo("individuos");
        int numeroIndividuos = nodoIndividuos.getArcosSalida().getNumeroElementos();
        for (int i = 0; i != numeroIndividuos; i ++) {
            Nodo<String> nodoIndividuoActual = nodoIndividuos.getArcosSalida().getElemento(i).getData().getOtroVertice(nodoIndividuos);
            int accionesIndividuo = nodoIndividuoActual.getArcosSalida().getNumeroElementos();
            for (int j = 0; j != accionesIndividuo; j ++) {
                String accionActual = nodoIndividuoActual.getArcosSalida().getElemento(j).getData().getOtroVertice(nodoIndividuoActual).getDato();
                if (!accionActual.equals("individuos")) {
                    int indexOfFinalAccion = Math.min(accionActual.substring(8).indexOf(" ") + 8, accionActual.indexOf(","));
                    String accionReducida = accionActual.substring(8, indexOfFinalAccion);
                    if (accionReducida.equals("actualizarTV")) {
                        int indexOfInicioTV = accionActual.indexOf("(") + 1;
                        int indexOfFinalTV = accionActual.indexOf(")");
                        int TVAccion = Integer.parseInt(accionActual.substring(indexOfInicioTV, indexOfFinalTV));
                        if (TVAccion > TVMaximo) {
                            idIndividuoTVMaximo = Integer.parseInt(nodoIndividuoActual.getDato().substring(10));
                        }
                    }
                }
            }
        }
        individuo individuoTVMaximo = null;
        for (int k = 0; k != numeroIndividuos; k ++) {
            individuo individuoActual = model.getHistorialIndividuos().getElemento(k).getData();
            if (individuoActual.getId() == idIndividuoTVMaximo) {
                individuoTVMaximo = individuoActual;
            }
        }
        return individuoTVMaximo;
    }

    private static int getTVMaximoIndividuo (Grafo<String> grafoAcciones, individuo individuo) {
        int TVMaximo = -1;
        Nodo<String> nodoIndividuoTVMaximo = grafoAcciones.getNodo(STR."Individuo \{individuo.getId()}");
        int accionesIndividuo = nodoIndividuoTVMaximo.getArcosSalida().getNumeroElementos();
        for (int j = 0; j != accionesIndividuo; j ++) {
            String accionActual = nodoIndividuoTVMaximo.getArcosSalida().getElemento(j).getData().getOtroVertice(nodoIndividuoTVMaximo).getDato();
            if (!accionActual.equals("individuos")) {
                int indexOfFinalAccion = Math.min(accionActual.substring(8).indexOf(" ") + 8, accionActual.indexOf(","));
                String accionReducida = accionActual.substring(8, indexOfFinalAccion);
                if (accionReducida.equals("actualizarTV")) {
                    int indexOfInicioTV = accionActual.indexOf("(") + 1;
                    int indexOfFinalTV = accionActual.indexOf(")");
                    int TVAccion = Integer.parseInt(accionActual.substring(indexOfInicioTV, indexOfFinalTV));
                    if (TVAccion > TVMaximo) {
                        TVMaximo = TVAccion;
                    }
                }
            }
        }
        return TVMaximo;
    }
}
