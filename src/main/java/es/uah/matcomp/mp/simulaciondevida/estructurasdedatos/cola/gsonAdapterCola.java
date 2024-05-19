package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.cola;

import com.google.gson.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAvanzado;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoNormal;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaEnlazada.ListaEnlazada;
import excepciones.tipoInvalidoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class gsonAdapterCola implements JsonSerializer<Cola>, JsonDeserializer<Cola> {
    private static final Logger log = LogManager.getLogger();

    @Override
    public JsonElement serialize(Cola src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getElementos());
    }

    @Override
    public Cola deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Cola<?> cola = new Cola<>();
        cola.setElementos(context.deserialize(json, ListaEnlazada.class));
        return cola;
    }
}
