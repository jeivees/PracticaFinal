package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;

public class individuoAvanzado extends individuo<individuoAvanzado> {
    public individuoAvanzado(int I, int G, int TV, float PR, float PC) {
        super(I, G, TV, PR, PC);
    }
    public individuoAvanzado(int I, int PX, int PY, int G, int TV, float PR, float PC) {
        super(I, PX, PY, G, TV, PR, PC);
    }
    @Override
    public Class<individuoAvanzado> getTipo () {
        return individuoAvanzado.class;
    }

    public void mover (ListaEnlazada<recurso> recursos, int caminoMaximo) {
        if (!recursos.isVacia()) {
            recurso recursoCercano = null;
            int recursoCercanoPasos = caminoMaximo + 1;
            for (int i = 0; i != recursos.getNumeroElementos(); i++) {
                int pasosRecurso = Math.abs(recursos.getElemento(i).getData().getPosicionX() - this.getPosicionX()) + Math.abs(recursos.getElemento(i).getData().getPosicionY() - this.getPosicionY());
                if (pasosRecurso < recursoCercanoPasos) {
                    recursoCercano = recursos.getElemento(i).getData();
                    recursoCercanoPasos = pasosRecurso;
                }
            }
            assert recursoCercano != null;
            int[] posicionRecurso = recursoCercano.getPosicion();
            if (Math.abs(posicionRecurso[0] - this.getPosicionX()) > Math.abs(posicionRecurso[1] - this.getPosicionY())) {
                if (posicionRecurso[0] - this.getPosicionX() < 0) {
                    this.setPosicionX(this.getPosicionX() - 1);
                } else {
                    this.setPosicionX(this.getPosicionX() + 1);
                }
            } else {
                if (posicionRecurso[1] - this.getPosicionY() < 0) {
                    this.setPosicionY(this.getPosicionY() - 1);
                } else {
                    this.setPosicionY(this.getPosicionY() + 1);
                }
            }
        } else {
            this.moverAleatorio();
        }
    }
}
