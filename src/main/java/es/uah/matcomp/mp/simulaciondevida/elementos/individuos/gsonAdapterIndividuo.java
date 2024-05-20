package es.uah.matcomp.mp.simulaciondevida.elementos.individuos;

import com.google.gson.*;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple.ElementoLS;
import es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple.ListaSimple;
import excepciones.tipoInvalidoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class gsonAdapterIndividuo implements JsonSerializer<individuo>, JsonDeserializer<individuo> {
    private static final Logger log = LogManager.getLogger();
    @Override
    public JsonElement serialize(individuo src, Type typeOfSrc, JsonSerializationContext context) {
        log.trace(STR."Empezando serialización de individuo \{src.getTipo().getSimpleName()} a Json");
        JsonObject individuo = new JsonObject();
        ListaSimple<individuo> padres = src.getPadres();
        if (padres.getPrimero() != null) {
            JsonArray padresJson = new JsonArray(2);
            padresJson.add(serialize(padres.getPrimero().getData(), typeOfSrc, context));
            padresJson.add(serialize(padres.getElemento(1).getData(), typeOfSrc, context));
            individuo.add("padres", padresJson);
        }

        individuo.addProperty("tipo", src.getTipo().getSimpleName());
        individuo.add("propiedades", context.serialize(src));
        return individuo;
    }

    @Override
    public individuo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        log.trace(STR."Empezando deserialización de individuo \{json.getAsJsonObject().get("tipo").getAsString()} a Json");
        JsonObject jsonIndividuo = json.getAsJsonObject();

        String tipo = jsonIndividuo.get("tipo").getAsString();

        Class<?> individuoClase = switch (tipo) {
            case "individuoBasico" -> individuoBasico.class;
            case "individuoNormal" -> individuoNormal.class;
            case "individuoAvanzado" -> individuoAvanzado.class;
            default -> throw new tipoInvalidoException(individuo.class.getSimpleName(), tipo);
        };

        individuo individuo = context.deserialize(jsonIndividuo.get("propiedades").getAsJsonObject(), individuoClase);
        ListaSimple<individuo> lista = new ListaSimple<>(2);

        if (jsonIndividuo.has("padres")) {
            JsonArray padres = jsonIndividuo.get("padres").getAsJsonArray();
            for (int i = 0; i != 2; i++) {
                individuo padre = deserialize(padres.get(i).getAsJsonObject(), typeOfT, context);
                ElementoLS<individuo> elementoLS = new ElementoLS<>(padre);
                lista.add(elementoLS);
            }
            individuo.setPadres(lista);
        }
        return individuo;
    }
}
