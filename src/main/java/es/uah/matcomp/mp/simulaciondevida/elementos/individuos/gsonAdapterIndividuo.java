package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import com.google.gson.*;
import excepciones.tipoInvalidoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class gsonAdapterIndividuo implements JsonSerializer<individuo>, JsonDeserializer<individuo> {
    private static final Logger log = LogManager.getLogger();
    @Override
    public JsonElement serialize(individuo src, Type typeOfSrc, JsonSerializationContext context) {
        log.debug(STR."Empezando serialización de individuo \{src.getTipo().getSimpleName()} a Json");
        JsonObject individuo = new JsonObject();
        individuo.addProperty("tipo", src.getTipo().getSimpleName());
        individuo.add("propiedades", context.serialize(src));
        return individuo;
    }

    @Override
    public individuo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.debug(STR."Empezando deserialización de individuo \{json.getAsJsonObject().get("tipo").getAsString()} a Json");
        JsonObject jsonIndividuo = json.getAsJsonObject();

        String tipo = jsonIndividuo.get("tipo").getAsString();

        return switch (tipo) {
            case "individuoBasico" ->
                    context.deserialize(jsonIndividuo.get("propiedades").getAsJsonObject(), individuoBasico.class);
            case "individuoNormal" ->
                    context.deserialize(jsonIndividuo.get("propiedades").getAsJsonObject(), individuoNormal.class);
            case "individuoAvanzado" ->
                    context.deserialize(jsonIndividuo.get("propiedades").getAsJsonObject(), individuoAvanzado.class);
            default -> throw new tipoInvalidoException(individuo.class.getSimpleName(), tipo);
        };
    }
}
