package cars.models.serializers;

import cars.models.Body;
import cars.models.Engine;
import cars.models.Gearbox;
import cars.models.Model;
import com.google.gson.*;

import java.lang.reflect.Type;
/**
 * Реализовать площадку продаж машин. [#4747].
 * Model serializer. Exclude unnecessary information.
 */
public class ModelSerializer implements JsonSerializer<Model> {
    @Override
    public JsonElement serialize(Model model, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", model.getId());
        result.addProperty("make", model.getMake().getName());
        result.addProperty("name", model.getName());
        result.addProperty("description", model.getDescription());
        JsonArray engines = new JsonArray();
        for (Engine engine : model.getEngines()) {
            engines.add(jsonSerializationContext.serialize(engine));
        }
        result.add("engine", engines);
        JsonArray gearboxes = new JsonArray();
        for (Gearbox gearbox : model.getGearboxes()) {
            gearboxes.add(jsonSerializationContext.serialize(gearbox));
        }
        result.add("gearbox", gearboxes);
        JsonArray bodies = new JsonArray();
        for (Body body : model.getBodies()) {
            bodies.add(jsonSerializationContext.serialize(body));
        }
        result.add("body", bodies);
        return result;
    }
}
