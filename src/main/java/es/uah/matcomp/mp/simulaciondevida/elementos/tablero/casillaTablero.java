package es.uah.matcomp.mp.simulaciondevida.elementos.tablero;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple.ListaSimple;
import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos.recursoAbstract;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;

public class casillaTablero {
    private ListaSimple<individuoAbstract> individuos;
    private ListaSimple<recursoAbstract> recursos;

    public ListaSimple<individuoAbstract> getIndividuos() {
        return individuos;
    }

    public ListaSimple<recursoAbstract> getRecursos() {
        return recursos;
    }

}
