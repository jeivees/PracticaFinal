package es.uah.matcomp.mp.simulaciondevida.elementos.entorno.recursos;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAbstract;
import excepciones.incrementoInvalidoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class comida extends recursoAbstract{
    private int incrementoTV;
    private static final Logger logger = LogManager.getLogger("es.uah");
    public int getIncrementoTV() {
        return incrementoTV;
    }

    public void setIncrementoTV(int incrementoTV) throws incrementoInvalidoException {
        if (incrementoTV < 0) throw new incrementoInvalidoException();
        this.incrementoTV = incrementoTV;
        logger.info("Incremento de tiempo de vida modificado");
    }
    @Override
    public void aplicarMejora (individuoAbstract individuo) {
        individuo.setTiempoDeVida(individuo.getTiempoDeVida() + incrementoTV);
        logger.info("Mejora aplicada");
    }
}
