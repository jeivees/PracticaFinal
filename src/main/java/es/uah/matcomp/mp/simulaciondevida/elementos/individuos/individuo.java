package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;
import com.google.gson.annotations.Expose;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.casillaTablero;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola.Cola;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple.ListaSimple;
import excepciones.arrayTamañoInvalidoException;
import excepciones.probabilidadInvalidaException;
import gui.mvc.javafx.practicafinal.DataModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public abstract class individuo {
    @Expose
    private Cola<String> acciones = new Cola<>();
    @Expose (serialize = false)
    private ListaSimple<individuo> padres = new ListaSimple<>(2);
    @Expose
    private int posicionX;
    @Expose
    private int posicionY;
    @Expose
    private int id;
    @Expose
    private int generacion;
    @Expose
    private int tiempoDeVida;
    @Expose (serialize = false)
    private IntegerProperty tiempoDeVidaProperty = new SimpleIntegerProperty();
    @Expose
    private float probReproduccion;
    @Expose
    private float probClonacion;
    @Expose
    private float probMuerte;
    @Expose
    private boolean isVivo = true;

    private static final Logger log = LogManager.getLogger();

    public individuo () {}
    public individuo(int id, int generacion, int tiempoDeVida, float probReproduccion, float probClonacion, int turnoActual) {
        this.id = id;
        this.generacion = generacion;
        this.tiempoDeVida = tiempoDeVida;
        updateTiempoDeVidaProperty();
        if (probReproduccion < 0 || probReproduccion > 100 || probClonacion < 0 || probClonacion > 100) throw new probabilidadInvalidaException();
        this.probReproduccion = probReproduccion;
        this.probClonacion = probClonacion;
        this.probMuerte = 100 - probReproduccion;
        acciones.add(STR."Acción: nacer, turno: \{turnoActual}");
        log.debug(STR."Ha nacido el individuo \{id}");
    }

    public individuo(int id, int posicionX, int posicionY, int generacion, int tiempoDeVida, float probReproduccion, float probClonacion, int turnoActual) {
        this.id = id;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.generacion = generacion;
        this.tiempoDeVida = tiempoDeVida;
        updateTiempoDeVidaProperty();
        this.probReproduccion = probReproduccion;
        this.probClonacion = probClonacion;
        this.probMuerte = 100 - probReproduccion;
        acciones.add(STR."Acción: nacer, turno: \{turnoActual}");
        log.debug(STR."Ha nacido el individuo \{id}");
    }

    public individuo(individuo individuo) {
        this.id = individuo.getId();
        this.posicionX = individuo.getPosicionX();
        this.posicionY = getPosicionY();
        this.generacion = getGeneracion();
        this.tiempoDeVida = individuo.getTiempoDeVida();
        updateTiempoDeVidaProperty();
        this.probReproduccion = individuo.getProbReproduccion();
        this.probClonacion = individuo.getProbClonacion();
        this.probMuerte = 100 - probReproduccion;
        this.isVivo = individuo.isVivo();
        this.acciones = individuo.getAcciones();
        log.debug(STR."Se ha creado una copia del individuo \{id}");
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public int[] getPosicion() {
        int[] posicion = new int[2];
        posicion[0] = posicionX;
        posicion[1] = posicionY;
        return posicion;
    }

    public void setPosicion(int[] posicion) {
        try {
            if (posicion.length != 2) throw new arrayTamañoInvalidoException();
            posicionX = posicion[0];
            posicionY = posicion[1];
        } catch (arrayTamañoInvalidoException e) {
            log.error("Se ha intentado establecer la posición de un individuo con un array que no contiene 2 elementos");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        log.debug("Id modificado");
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
        log.debug("Generación modificada");
    }

    public int getTiempoDeVida() {
        return tiempoDeVida;
    }

    public void setTiempoDeVida(int tiempoDeVida, int turnoActual) {
        this.tiempoDeVida = tiempoDeVida;
        updateTiempoDeVidaProperty();
        acciones.add(STR."Acción: actualizarTV (\{getTiempoDeVida()}), turno: \{turnoActual}");
        log.debug("Tiempo de vida modificado");
    }

    private void updateTiempoDeVidaProperty () {
        tiempoDeVidaProperty.set(tiempoDeVida);
    }

    public IntegerProperty getTiempoDeVidaProperty () {
        return tiempoDeVidaProperty;
    }
    public float getProbReproduccion() {
        return probReproduccion;
    }

    public void setProbReproduccion(float probReproduccion, int turnoActual) {
        if (probReproduccion < 0 || probReproduccion > 100) throw new probabilidadInvalidaException();
        this.probReproduccion = probReproduccion;
        this.probMuerte = 100 - probReproduccion;
        acciones.add(STR."Acción: actualizarPR (\{getProbReproduccion()}), turno: \{turnoActual}");
        log.debug("Probabilidad de reproducción modificada");
    }

    public float getProbClonacion() {
        return probClonacion;
    }

    public void setProbClonacion(float probClonacion, int turnoActual) {
        if (probClonacion < 0 || probClonacion > 100) throw new probabilidadInvalidaException();
        this.probClonacion = probClonacion;
        acciones.add(STR."Acción: actualizarPC (\{getProbClonacion()}), turno: \{turnoActual}");
        log.debug("Probabilidad de clonación modificada");
    }

    public float getProbMuerte() {
        return probMuerte;
    }

    public abstract Class<?> getTipo();

    public int getGradoTipo() {
        int grado = -1;
        switch (this.getClass().getSimpleName()) {
            case "individuoBasico":
                grado = 0;
                break;
            case "individuoNormal":
                grado = 1;
                break;
            case "individuoAvanzado":
                grado = 2;
                break;
            default:
                log.error("El grado del tipo es != 0,1,2");
        }
        return grado;
    }

    public <T extends individuo> boolean reproducirse (individuo pareja, DataModel model, casillaTablero casillaActual, int turnoActual) {
        int gradoThis = getGradoTipo();
        int gradoPareja = pareja.getGradoTipo();

        Random r = new Random();
        int p = r.nextInt(1, 100);

        if (p <= probReproduccion) {
            int probMejora;
            individuo individuoSuperior = this;
            individuo individuoInferior =  pareja;

            if (gradoThis > gradoPareja) {
                probMejora = getProbMejora(this, model);
            } else if (gradoThis < gradoPareja) {
                probMejora = getProbMejora(pareja, model);
                individuoSuperior = pareja;
                individuoInferior = this;
            } else {
                probMejora = 100;
            }

            Random s = new Random();
            int q = s.nextInt(1, 100);

            Class<?> hijoTipo;
            if (q <= probMejora) {
                hijoTipo = individuoSuperior.getTipo();
            } else {
                hijoTipo = individuoInferior.getTipo();
            }
            try {
                Constructor<?> constructor = hijoTipo.getConstructor(int.class, int.class, int.class, int.class, int.class, float.class, float.class, int.class);
                int id = model.getHistorialIndividuos().getUltimo().getData().getId() + 1;
                T hijo = (T) constructor.newInstance(id, getPosicionX(), getPosicionY(), this.getGeneracion() + 1,
                        model.getTurnosVidaIniciales(), model.getProbReproIndividuo(), model.getProbClonIndividuo(), model.getTurno());
                hijo.setPadres(this, pareja);
                hijo.añadir(model, casillaActual);
                acciones.add(STR."Acción: reproducirse (con individuo\{pareja.getId()}), turno: \{turnoActual}");
                log.debug(STR."El individuo \{this} se ha reproducido con el individuo \{pareja.getId()}");
                return false; // mueren? no
            } catch (Exception e) {
                log.error("No se ha podido crear una instancia para el individuo hijo");
                return false; // mueren? no
            }
        } else {
            return true; // mueren? si
        }
    }

    private int getProbMejora (individuo individuoSuperior, DataModel model) {
        int probMejora = -1;
        switch (individuoSuperior.getClass().getSimpleName()) {
            case "individuoNormal":
                probMejora = model.getProbMejoraToNormal();
                break;
            case "individuoAvanzado":
                probMejora = model.getProbMejoraToAvanzado();
                break;
            default:
                log.error("El nombre de la clase del individuo superior no es válido");
        }
        return probMejora;
    }

    public void clonarse (DataModel model, casillaTablero casillaActual) {
        try {
            Constructor<? extends individuo> constructor = getClass().getConstructor(individuo.class);
            individuo copia = constructor.newInstance(this);
            copia.setId(model.getHistorialIndividuos().getUltimo().getData().getId() + 1);

            acciones.add(STR."Acción: clonarse, turno: \{model.getTurno()}");
            log.debug(STR."El individuo \{this.getId()} se ha clonado");

            casillaActual.addIndividuo(copia, true);
            copia.getAcciones().add(STR."Acción: nacer, turno: \{model.getTurno()}");
            log.debug(STR."El individuo \{copia.getId()} ha nacido");
        } catch (Exception e) {
            log.error("No se ha podido crear una copia del individuo");
        }
    }

    public void añadir(DataModel model, casillaTablero casillaActual) {
        try {
            model.getIndividuos().add(this);
            casillaActual.getIndividuos().add(this);
            this.setPosicion(casillaActual.getPosicion());

            Constructor<? extends individuo> constructor = getClass().getConstructor(individuo.class);
            model.getHistorialIndividuos().add(constructor.newInstance(this));

        } catch (Exception e) {
            log.error("No se ha podido crear una nueva instancia de individuo para el historial de individuos");
        }
    }
    public void morir(DataModel model, casillaTablero casillaActual) {
        casillaActual.getIndividuos().del(this);
        model.getIndividuos().del(this);
        isVivo = false;
        acciones.add(STR."Acción: morir, turno: \{model.getTurno()}");
        log.debug(STR."El individuo \{this.getId()} ha muerto");
    }

    public boolean isVivo () {
        return isVivo;
    }

    public boolean actualizarTV (casillaTablero casillaActual, int turnoActual) {
        tiempoDeVida -= 1;
        updateTiempoDeVidaProperty();

        probReproduccion -= 10;
        if (probReproduccion < 0) probReproduccion = 0;

        probClonacion -= 10;
        if (probClonacion < 0) probClonacion = 0;

        acciones.add(STR."Acción: actualizarTV (\{getTiempoDeVida()}), turno: \{turnoActual}");
        log.debug("Tiempo de vida actualizado");
        if (tiempoDeVida <= 0) {
            casillaActual.delIndividuo(this);
            return true;
        }
        return false;
    }

    public abstract void mover(DataModel model, tablero tablero);

    protected void moverAleatorio(tablero tablero, int turnoActual) {
        Random r = new Random();
        int movimiento = r.nextInt(1,8);
        try {
            switch (movimiento) {
                case 1:
                    cambiarPosicion(getPosicionX() + 1, getPosicionY(), tablero);
                    break;
                case 2:
                    cambiarPosicion(getPosicionX() + 1, getPosicionY() - 1, tablero);
                    break;
                case 3:
                    cambiarPosicion(getPosicionX(), getPosicionY() - 1, tablero);
                    break;
                case 4:
                    cambiarPosicion(getPosicionX() - 1, getPosicionY() - 1, tablero);
                    break;
                case 5:
                    cambiarPosicion(getPosicionX() - 1, getPosicionY(), tablero);
                    break;
                case 6:
                    cambiarPosicion(getPosicionX() - 1, getPosicionY() + 1, tablero);
                    break;
                case 7:
                    cambiarPosicion(getPosicionX(), getPosicionY() + 1, tablero);
                    break;
                case 8:
                    cambiarPosicion(getPosicionX() + 1, getPosicionY() + 1, tablero);
                    break;
                default:
                    log.error("Se ha intentado hacer un movimiento aleatorio inválido (numero generado < 1 o > 8)");
            }
            this.getAcciones().add(STR."Acción: moverse (\{getPosicionX()}, \{getPosicionY()}), turno: \{turnoActual}");
            log.debug(STR."El individuo se ha movido a \{getPosicionX()}, \{getPosicionY()}");
        } catch (IndexOutOfBoundsException e) {
            moverAleatorio(tablero, turnoActual);
        }
    }

    protected void cambiarPosicion (int nuevaPosicionX, int nuevaPosicionY, tablero tablero) {
        casillaTablero nuevaCasilla = tablero.getCasilla(nuevaPosicionX, nuevaPosicionY);

        casillaTablero casillaActual = tablero.getCasilla(getPosicion());
        casillaActual.getIndividuos().del(this);
        casillaActual.resetVisual();

        setPosicionX(nuevaPosicionX);
        setPosicionY(nuevaPosicionY);

        nuevaCasilla.getIndividuos().add(this);
        nuevaCasilla.resetVisual();
    }

    public Cola<String> getAcciones() {
        return acciones;
    }

    public void setAcciones(Cola<String> acciones) {
        this.acciones = acciones;
    }

    public void addAccion (String accion) {
        acciones.add(accion);
    }

    public ListaSimple<individuo> getPadres() {
        return padres;
    }

    public void setPadres(individuo padre1, individuo padre2) {
        this.padres.setElemento(0, padre1);
        this.padres.setElemento(1, padre2);
    }

    protected void setPadres(ListaSimple<individuo> padres) {
        this.padres = padres;
    }
}
