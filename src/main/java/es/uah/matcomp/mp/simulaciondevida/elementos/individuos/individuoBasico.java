package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

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
        this.moverAleatorio();
    }
}
