package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;

import java.util.Random;

public class individuoNormal extends individuo {
    public individuoNormal(int I, int G, int T, float PR, float PC) {
        super(I, G, T, PR, PC);
    }
    @Override
    public String getTipo () {
        return "individuoNormal";
    }

    public void mover(ListaDE<recurso> recursos) {
        if (!recursos.isVacia()) {
            Random r = new Random();
            int recursoRandom = r.nextInt(recursos.getNumeroElementos());
            int[] posicionRecurso = recursos.getElemento(recursoRandom).getData().getPosicion();
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
