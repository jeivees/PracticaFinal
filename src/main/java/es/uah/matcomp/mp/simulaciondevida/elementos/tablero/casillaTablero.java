package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import gui.mvc.javafx.practicafinal.configuracionDataModel;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class casillaTablero extends Button {
    private int posicionX;
    private int posicionY;
    private configuracionDataModel model;
    private ListaDE<individuo> individuos = new ListaDE<>();
    private ListaDE<recurso> recursos = new ListaDE<>();

    private static final Logger log = LogManager.getLogger(casillaTablero.class);

    public casillaTablero (int x, int y) {
        posicionX = x;
        posicionY = y;
    }
    public ListaDE<individuo> getIndividuos() {
        return individuos;
    }

    public ListaDE<recurso> getRecursos() {
        return recursos;
    }

    public void setIndividuos(ListaDE<individuo> individuos) {
        this.individuos = individuos;
    }

    public void setRecursos(ListaDE<recurso> recursos) {
        this.recursos = recursos;
    }
    public configuracionDataModel getModel() {
        return model;
    }

    public void setModel(configuracionDataModel model) {
        this.model = model;
    }

    public void addIndividuo (individuo individuo) {
        individuos.add(individuo);
    }

    public void delIndividuo (individuo individuo) {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            if (individuo == individuos.getElemento(i).getData()) {
                individuos.del(i);
                individuo.morir();
                return;
            }
        }
        log.debug("Se ha tratado de eliminar un individuo de una casilla en la que no está contenido");
    }

    public void addRecurso (recurso recurso) {
        recursos.add(recurso);
    }

    public void delRecurso (recurso recurso) {
        for (int i = 0; i != recursos.getNumeroElementos(); i++) {
            if (recurso == recursos.getElemento(i).getData()) {
                recursos.del(i);
                recurso.eliminar();
                return;
            }
        }
        log.debug("Se ha tratado de eliminar un recurso de una casilla en la que no está contenido");
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

    public void setPosicionY(int getPosicionY) {
        this.posicionY = getPosicionY;
    }

    public int[] getPosicion () {
        return new int[]{posicionX, posicionY};
    }
}
