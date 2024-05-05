package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;

import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple.*;

public class tablero {
    private ListaSimple<ListaSimple<casillaTablero>> casillas;

    public tablero (int n, int m) {
        casillas = new ListaSimple<>(n);
        for (int i=0; i != n; i++){
            casillas.insert(new ListaSimple<>(m), i);
        }
    }

    public casillaTablero getCasilla (int n, int m) {
        return casillas.getElemento(n).getData().getElemento(m).getData();
    }

    public void setCasilla (int n, int m, casillaTablero casilla) {
        casillas.getElemento(n).getData().setElemento(m, casilla);
    }
}
