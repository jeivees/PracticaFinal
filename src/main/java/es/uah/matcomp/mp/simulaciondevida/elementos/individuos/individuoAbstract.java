package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recursoAbstract;
import excepciones.probabilidadInvalidaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Random;
public abstract class individuoAbstract {
    private int posicionX;
    private int posicionY;
    private int id;
    private int generacion;
    private int tiempoDeVida;
    private float probReproduccion;
    private float probClonacion;
    private float probMuerte;

    private static final Logger logger = LogManager.getLogger("es.uah");
    public individuoAbstract(int I, int G, int T, float PR, float PC) {
        this.id = I;
        this.generacion = G;
        this.tiempoDeVida = T;
        if (PR < 0 || PR > 100 || PC < 0 || PC > 100) throw new probabilidadInvalidaException();
        this.probReproduccion = PR;
        this.probClonacion = PC;
        this.probMuerte = 100-PR;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        logger.info("Id modificado");
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
        logger.info("Generación modificada");
    }

    public int getTiempoDeVida() {
        return tiempoDeVida;
    }

    public void setTiempoDeVida(int tiempoDeVida) {
        this.tiempoDeVida = tiempoDeVida;
        logger.info("Tiempo de vida modificado");
    }

    public float getProbReproduccion() {
        return probReproduccion;
    }

    public void setProbReproduccion(float probReproduccion) {
        if (probReproduccion < 0 || probReproduccion > 100)
            throw new probabilidadInvalidaException();
        this.probReproduccion = probReproduccion;
        this.probMuerte = 100 - probReproduccion;
        logger.info("Probabilidad de reproducción modificada");
    }

    public float getProbClonacion() {
        return probClonacion;
    }

    public void setProbClonacion(float probClonacion) {
        if (probClonacion < 0 || probClonacion > 100)
            throw new probabilidadInvalidaException();
        this.probClonacion = probClonacion;
        logger.info("Probabilidad de clonación modificada");
    }

    public float getProbMuerte() {
        return probMuerte;
    }

    public String getTipo () {return null;}

    public String setTipo (String tipo) {return null;}

    public void reproducirse (individuoAbstract pareja) {

    }
    public void clonarse () {}
    public void morir () {

    }
    public void actualizarTV () {
        tiempoDeVida -= 1;
        if (tiempoDeVida == 0) morir();
        logger.info("Tiempo de vida actualizado");
    }

    public void mover() {}

    public void moverAleatorio() {
        logger.info("Inicio del movimiento aleatorio");
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
        } logger.info("Fin del movimiento aleatorio");
    }

    public void mejorar (recursoAbstract recurso) {}


}
