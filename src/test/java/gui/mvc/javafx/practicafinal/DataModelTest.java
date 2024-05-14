package gui.mvc.javafx.practicafinal;


import com.google.gson.*;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuo;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoAvanzado;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoBasico;
import es.uah.matcomp.mp.simulaciondevida.elementos.individuos.individuoNormal;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class DataModelTest {
    public static void main(String[] args) {
        DataModel model = new DataModel(
                10, 50, 10, 50,25,
                5,15,20,20,20,
                10,10,10,3,5,
                7, 25, 10, 10, 10, 0);

        DataModelTest d = new DataModelTest();
        d.guardar("PruebaTest", model);
        DataModel.cargar("PruebaIndividuos.json");
    }

    public void guardar (String nombreArchivo, DataModel model) {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(individuo.class, new serializer())
                .registerTypeHierarchyAdapter(individuo.class, new deserializer())
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        try (FileWriter writer = new FileWriter("archivosDePartida/" + nombreArchivo + ".json")) {
            model.setGuardado(true);
            gson.toJson(this, writer);
        } catch (IOException e) {

        }
    }

    public static DataModel cargar (String nombreArchivo) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("archivosDePartida/" + nombreArchivo)) {
            return gson.fromJson(reader, DataModel.class);
        } catch (IOException e) {

            return null;
        }
    }


    @Test
    void guardar() {

    }

    @Test
    void cargar() {

    }
}

class serializer implements JsonSerializer<individuo> {

    @Override
    public JsonElement serialize(individuo src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("type", src.getTipo().getSimpleName());
        json.add("properties", context.serialize(src));
        return json;
    }
}

class deserializer implements JsonDeserializer<individuo> {
    @Override
    public individuo deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String animalType = jsonObject.get("type").getAsString();
        switch (animalType) {
            case "individuoAvanzado":
                return jsonDeserializationContext.deserialize(jsonObject.get("properties"), individuoAvanzado.class);
            case "individuoNormal":
                return jsonDeserializationContext.deserialize(jsonObject.get("properties"), individuoNormal.class);
            case "individuoBasico":
                return jsonDeserializationContext.deserialize(jsonObject.get("properties"), individuoBasico.class);
            default:
                throw new JsonParseException("Unknown animal type: " + animalType);
        }
    }
}