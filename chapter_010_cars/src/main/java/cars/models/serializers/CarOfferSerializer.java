package cars.models.serializers;

import cars.models.CarOffer;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Car offer serializer. Exclude unnecessary information about inner entities.
 */
public class CarOfferSerializer implements JsonSerializer<CarOffer> {

    @Override
    public JsonElement serialize(CarOffer src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("id", src.getId());
        result.addProperty("make", src.getModel().getMake().getName());
        result.addProperty("model", src.getModel().getName());
        result.addProperty("body", src.getBody().getType());
        JsonObject engine = new JsonObject();
        engine.addProperty("type", src.getEngine().getType());
        engine.addProperty("displacement", src.getEngine().getDisplacement());
        result.add("engine", engine);
        result.addProperty("gearbox", src.getGearbox().getType());
        result.addProperty("description", src.getDescrition());
        result.addProperty("sold", src.isSold());
        JsonArray images = new JsonArray();
        for(String s : src.getImages()) {
            images.add(s);
        }
        result.add("images", images);
        return result;
    }
}
