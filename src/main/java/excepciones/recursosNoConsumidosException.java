package excepciones;

import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;

public class recursosNoConsumidosException extends RuntimeException{
   private individuo individuoSensor;
    public recursosNoConsumidosException (individuo individuoSensor) {
        this.individuoSensor = individuoSensor;
    }
    public individuo getIndividuoSensor() {
        return individuoSensor;
    }
}
