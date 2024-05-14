package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import com.google.gson.annotations.Expose;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import gui.mvc.javafx.practicafinal.DataModel;

public class individuoBasico extends individuo<individuoBasico> {
    @Expose
    private final String nombreClase = "individuoBasico";
    public individuoBasico(int I, int G, int T, float PR, float PC) {
        super(I, G, T, PR, PC);
    }
    public individuoBasico(int I, int PX, int PY, int G, int TV, float PR, float PC) {
        super(I, PX, PY, G, TV, PR, PC);
    }

    public individuoBasico(individuo individuo) {
        super(individuo);
    }

    @Override
    public Class<individuoBasico> getTipo () {
        return individuoBasico.class;
    }

    @Override
    public void mover(DataModel model, tablero tablero) {
        this.moverAleatorio(tablero);
    }
}
