package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import java.util.Random;

public class individuoBasico extends individuoAbstract {
    public individuoBasico(int I, int G, int T, float PR, float PC) {
        super(I, G, T, PR, PC);
    }

    @Override
    public String getTipo () {
        return "individuoBasico";
    }

    @Override
    public void mover() {
        Random r = new Random();
        int movimiento = r.nextInt(4);
        if (movimiento == 1) {
            this.setPosicionX(this.getPosicionX() - 1);
        } else if (movimiento == 2) {
            this.setPosicionX(this.getPosicionX() + 1);
        } else if (movimiento == 3) {
            this.setPosicionY(this.getPosicionY() - 1);
        } else {
            this.setPosicionY(this.getPosicionY() + 1);
        }
    }
}
