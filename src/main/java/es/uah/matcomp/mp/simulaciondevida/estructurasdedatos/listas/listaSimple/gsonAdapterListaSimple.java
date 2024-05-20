package es.uah.matcomp.mp.simulaciondevida.estructurasdedatos.listas.listaSimple;

import com.google.gson.*;

import java.lang.reflect.Type;

public class gsonAdapterListaSimple implements JsonSerializer<ListaSimple>, JsonDeserializer<ListaSimple> {
    @Override
    public JsonElement serialize(ListaSimple src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject listaJson = new JsonObject();
        listaJson.addProperty("maximo", src.getMaximo());
        JsonArray jsonArray = new JsonArray();
        for (ElementoLS elemento : src.getDatos()) {
            jsonArray.add(context.serialize(elemento).getAsJsonObject());
        }
        listaJson.addProperty("datos", jsonArray.getAsString());
        return jsonArray;
    }

    @Override
    public ListaSimple deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int i = 0;
        for (JsonElement elemento : jsonObject.get("datos").getAsJsonArray()) i++;
        ElementoLS[] arrayElementos = new ElementoLS[i];
        for (int j = 0; j != i; j ++) {
            arrayElementos[j] = context.deserialize(jsonObject.get("datos").getAsJsonArray().get(j), ElementoLS.class);
        }
        ListaSimple listaSimple = new ListaSimple<>(i);
        listaSimple.setDatos(arrayElementos);
        return listaSimple;
    }
}
