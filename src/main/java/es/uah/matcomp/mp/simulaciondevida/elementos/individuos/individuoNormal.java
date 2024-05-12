package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import gui.mvc.javafx.practicafinal.configuracionDataModel;

import java.util.Random;

public class individuoNormal extends individuo<individuoNormal> {
    public individuoNormal(int I, int G, int T, float PR, float PC) {
        super(I, G, T, PR, PC);
    }
    public individuoNormal(int I, int PX, int PY, int G, int TV, float PR, float PC) {
        super(I, PX, PY, G, TV, PR, PC);
    }
    @Override
    public Class<individuoNormal> getTipo () {
        return individuoNormal.class;
    }

    public void mover(configuracionDataModel model, tablero tablero) {
        if (model.getRecursos().isVacia()) {
            moverAleatorio(tablero);
        } else {
            Random r = new Random();
            int numAleatorio = r.nextInt(model.getRecursos().getNumeroElementos());
            recurso recursoObjetivo = model.getRecursos().getElemento(numAleatorio).getData();

            if (Math.abs(recursoObjetivo.getPosicionX() - getPosicionX()) >
                    Math.abs(recursoObjetivo.getPosicionY() - getPosicionY()))
            {
                if (recursoObjetivo.getPosicionX() - getPosicionX() < 0) {
                    cambiarPosicion(getPosicionX() - 1, getPosicionY(), tablero);
                } else {
                    cambiarPosicion(getPosicionX() + 1, getPosicionY(), tablero);
                }
            } else {
                if (recursoObjetivo.getPosicionY() - getPosicionY() < 0) {
                    cambiarPosicion(getPosicionX(), getPosicionY() - 1, tablero);
                } else {
                    cambiarPosicion(getPosicionX(), getPosicionY() + 1, tablero);
                }
            }
        }
    }
}
