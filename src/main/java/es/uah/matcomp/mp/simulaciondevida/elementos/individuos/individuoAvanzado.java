package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

public class individuoAvanzado extends individuoAbstract {
    public individuoAvanzado(int I, int G, int T, float PR, float PC) {
        super(I, G, T, PR, PC);
    }
    @Override
    public String getTipo () {
        return "individuoAvanzado";
    }
}
