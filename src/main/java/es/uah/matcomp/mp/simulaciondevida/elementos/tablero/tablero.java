package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple.*;
import excepciones.arrayTamañoInvalidoException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class tablero {
    private static final Logger log = LogManager.getLogger("es.uah");
    private ListaSimple<ListaSimple<casillaTablero>> casillas;

    public tablero (int n, int m, DataModel model) {
        casillas = new ListaSimple<>(n);
        for (int i=0; i != n; i++){
            casillas.insert(new ListaSimple<>(m), i);
            for (int j=0; j != m; j++) {
                casillas.getElemento(i).getData().insert(new casillaTablero(i, j, model, this), j);
            }
        }
    }

    public casillaTablero getCasilla (int n, int m) {
        return casillas.getElemento(n).getData().getElemento(m).getData();
    }

    public casillaTablero getCasilla (int[] posicion) {
        try {
            if (posicion.length != 2) throw new arrayTamañoInvalidoException();
            return casillas.getElemento(posicion[0]).getData().getElemento(posicion[1]).getData();
        } catch (arrayTamañoInvalidoException e) {
            log.error("Se ha tratado de obtener la posicion de una casilla con array de tamaño distinto de 2");
            return null;
        }
    }

    public void setCasilla (int n, int m, casillaTablero casilla) {
        casillas.getElemento(n).getData().setElemento(m, casilla);
    }

    public int getNumeroCasillasN () {
        return casillas.getNumeroElementos();
    }

    public int getNumeroCasillasM () {
        return casillas.getPrimero().getData().getNumeroElementos();
    }
}
