package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class recursoAbstract {
    private int posicionX;
    private int posicionY;
    private int tiempoDeAparicion;

    private static final Logger logger = LogManager.getLogger("es.uah");
    public recursoAbstract () {}

    public recursoAbstract (int T) {
        this.tiempoDeAparicion = T;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
        logger.info("PosiciónX modificada");
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
        logger.info("PosiciónY modificada");
    }

    public int[] getPosicion () {
        int[] posicion = new int[2];
        posicion[0] = posicionX;
        posicion[1] = posicionY;
        return posicion;
    }

    public void setPosicion (int[] posicion) {
        posicionX = posicion[0];
        posicionY = posicion[1];
        logger.info("Posición modificada");
    }

    public int getTiempoDeAparicion() {
        return tiempoDeAparicion;
    }

    public void setTiempoDeAparicion(int tiempoDeAparicion) {
        this.tiempoDeAparicion = tiempoDeAparicion;
        logger.info("Tiempo de aparición modificado");
    }
    public void eliminar() {

    }
    public void actualizarTA () {
        tiempoDeAparicion -= 1;
        if (tiempoDeAparicion == 0) eliminar();
        logger.info("Tiempo de aparición actualizado");
    }

    public void aplicarMejora (individuoAbstract individuo) {}
}
