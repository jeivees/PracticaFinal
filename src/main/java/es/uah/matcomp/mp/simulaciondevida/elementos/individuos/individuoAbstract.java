package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recursoAbstract;

public abstract class individuoAbstract {
    private int posicionX;
    private int posicionY;
    private int id;
    private int generacion;
    private int tiempoDeVida;
    private float probReproduccion;
    private float probClonacion;
    private float probMuerte;
    public individuoAbstract(int I, int G, int T, float PR, float PC) {
        this.id = I;
        this.generacion = G;
        this.tiempoDeVida = T;
        this.probReproduccion = PR;
        this.probClonacion = PC;
        this.probMuerte = 1-PR;
    }

    public int[] getPosicion () {
        int[] posicion = new int[2];
        posicion[0] = posicionX;
        posicion[1] = posicionY;
        return posicion;
    }

    public void morir () {

    }
    public void actualizarTV () {
        tiempoDeVida -= 1;
        if (tiempoDeVida == 0) morir();
    }

    public void mover() {}

    public void mejorar (recursoAbstract recurso) {}
}
