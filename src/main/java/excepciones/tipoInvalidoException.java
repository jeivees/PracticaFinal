package excepciones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class tipoInvalidoException extends RuntimeException{
    private static final Logger log = LogManager.getLogger();
    public tipoInvalidoException(String superclase, String subclase) {
        log.error("el tipo de " + superclase + " no es válido (" + subclase + ")");
    }
}
