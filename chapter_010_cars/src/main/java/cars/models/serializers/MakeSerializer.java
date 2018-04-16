package cars.models.serializers;

import cars.models.Make;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
/**
 * Реализовать площадку продаж машин. [#4747].
 * Make serializer. Exclude unnecessary information (model of that make).
 */
public class MakeSerializer implements JsonSerializer<Make> {
    @Override
    public JsonElement serialize(Make make, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("id", make.getId());
        result.addProperty("name", make.getName());
        result.addProperty("description", make.getDescription());
        return result;
    }
}
