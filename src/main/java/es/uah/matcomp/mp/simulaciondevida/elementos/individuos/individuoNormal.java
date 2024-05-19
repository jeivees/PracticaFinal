package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recurso;
import es.uah.matcomp.mp.simulaciondevida.elementos.tablero.tablero;
import excepciones.recursosNoConsumidosException;
import gui.mvc.javafx.practicafinal.DataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class individuoNormal extends individuo {
    private static final Logger log = LogManager.getLogger();
    public individuoNormal () {
        super();
    }
    public individuoNormal(int I, int G, int TV, float PR, float PC, int T) {
        super(I, G, TV, PR, PC, T);
    }
    public individuoNormal(int I, int PX, int PY, int G, int TV, float PR, float PC, int T) {
        super(I, PX, PY, G, TV, PR, PC, T);
    }
    public individuoNormal(individuo individuo) {
        super(individuo);
    }
    @Override
    public Class<individuoNormal> getTipo () {
        return individuoNormal.class;
    }

    public void mover(DataModel model, tablero tablero) throws recursosNoConsumidosException{
        if (model.getRecursos().isVacia()) {
            moverAleatorio(tablero, model.getTurno());
        } else {
            Random r = new Random();
            int numAleatorio = r.nextInt(model.getRecursos().getNumeroElementos());
            recurso recursoObjetivo = model.getRecursos().getElemento(numAleatorio).getData();

            int distanciaEnX = recursoObjetivo.getPosicionX() - getPosicionX();
            int distanciaEnY = recursoObjetivo.getPosicionY() - getPosicionY();
            int coordenadaXDestino = getPosicionX();
            int coordenadaYDestino = getPosicionY();

            if (distanciaEnX == 0 && distanciaEnY == 0) throw new recursosNoConsumidosException(this);

            if (distanciaEnX < 0) {
                coordenadaXDestino -= 1;
            } else if (distanciaEnX > 0) {
                coordenadaXDestino += 1;
            }
            if (distanciaEnY < 0) {
                coordenadaYDestino -= 1;
            } else if (distanciaEnY > 0) {
                coordenadaYDestino += 1;
            }
            cambiarPosicion(coordenadaXDestino, coordenadaYDestino, tablero);
            this.getAcciones().add(STR."Acción: moverse (\{coordenadaXDestino}, \{coordenadaYDestino}), turno: \{model.getTurno()}");
            log.debug(STR."El individuo se ha movido a \{coordenadaXDestino}, \{coordenadaYDestino}");
        }
    }
}
