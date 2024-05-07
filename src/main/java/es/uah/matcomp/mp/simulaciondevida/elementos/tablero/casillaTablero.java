package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaDoblementeEnlazada.ListaDE;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recursoAbstract;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class casillaTablero extends Button {
    private ListaDE<individuoAbstract> individuos;
    private ListaDE<recursoAbstract> recursos;

    private static final Logger log = LogManager.getLogger(casillaTablero.class);

    public ListaDE<individuoAbstract> getIndividuos() {
        return individuos;
    }

    public ListaDE<recursoAbstract> getRecursos() {
        return recursos;
    }

    public void setIndividuos(ListaDE<individuoAbstract> individuos) {
        this.individuos = individuos;
    }

    public void setRecursos(ListaDE<recursoAbstract> recursos) {
        this.recursos = recursos;
    }

    public void addIndividuo (individuoAbstract individuo) {
        individuos.add(individuo);
    }

    public void delIndividuo (individuoAbstract individuo) {
        for (int i = 0; i != individuos.getNumeroElementos(); i++) {
            if (individuo == individuos.getElemento(i).getData()) {
                individuos.del(i);
                individuo.morir();
                return;
            }
        }
        log.debug("Se ha tratado de eliminar un individuo de una casilla en la que no está contenido");
    }

    public void addRecurso (recursoAbstract recurso) {
        recursos.add(recurso);
    }

    public void delRecurso (recursoAbstract recurso) {
        for (int i = 0; i != recursos.getNumeroElementos(); i++) {
            if (recurso == recursos.getElemento(i).getData()) {
                recursos.del(i);
                recurso.eliminar();
                return;
            }
        }
        log.debug("Se ha tratado de eliminar un recurso de una casilla en la que no está contenido");
    }
}
