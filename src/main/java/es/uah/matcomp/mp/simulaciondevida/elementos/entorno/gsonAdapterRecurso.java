package es.uah.matcomp.mp.simulaciondevida.elementos.entorno;

import com.google.gson.*;
import excepciones.tipoInvalidoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class gsonAdapterRecurso implements JsonSerializer<recurso>, JsonDeserializer<recurso> {
    private static final Logger log = LogManager.getLogger();
    @Override
    public JsonElement serialize(recurso src, Type typeOfSrc, JsonSerializationContext context) {
        log.debug(STR."Empezando serialización de recurso \{src.getTipo().getSimpleName()} a Json");
        JsonObject recurso = new JsonObject();
        recurso.addProperty("tipo", src.getTipo().getSimpleName());
        recurso.add("propiedades", context.serialize(src));
        return recurso;
    }

    @Override
    public recurso deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.debug(STR."Empezando deserialización de recurso \{json.getAsJsonObject().get("tipo").getAsString()} a Json");
        JsonObject jsonRecurso = json.getAsJsonObject();

        String tipo = jsonRecurso.get("tipo").getAsString();

        return switch (tipo) {
            case "agua" ->
                    context.deserialize(jsonRecurso.get("propiedades").getAsJsonObject(), agua.class);
            case "comida" ->
                    context.deserialize(jsonRecurso.get("propiedades").getAsJsonObject(), comida.class);
            case "montaña" ->
                    context.deserialize(jsonRecurso.get("propiedades").getAsJsonObject(), montaña.class);
            case "biblioteca" ->
                    context.deserialize(jsonRecurso.get("propiedades").getAsJsonObject(), biblioteca.class);
            case "tesoro" ->
                    context.deserialize(jsonRecurso.get("propiedades").getAsJsonObject(), tesoro.class);
            case "pozo" ->
                    context.deserialize(jsonRecurso.get("propiedades").getAsJsonObject(), pozo.class);
            default -> throw new tipoInvalidoException(recurso.class.getSimpleName(), tipo);
        };
    }
}
