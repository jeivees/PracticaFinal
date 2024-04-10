package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;

public class tablero {
    private casillaTablero[][] casillasN;

    public tablero (int n, int m) {
        casillasN = new casillaTablero[n][];
        for (int i=0; i != n; i++){
            casillasN[i] = new casillaTablero[m];
        }
    }

    public casillaTablero getCasilla (int n, int m) {
        return null;
    }

    public casillaTablero getCasilla (int[] casilla) {
        return null;
    }
}
