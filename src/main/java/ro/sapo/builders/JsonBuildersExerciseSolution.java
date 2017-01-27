package ro.sapo.builders;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Created by sapo on 15-Jan-17.
 */
public class JsonBuildersExerciseSolution {


    public static void main(String args[]) {

        JsonObject initialJSON = createJSON();
        System.out.println(initialJSON);

        JsonObject extendedJSON = extendJSON(initialJSON);
        System.out.println(extendedJSON);
    }

    //TODO part1
    //Create the following JSON object
    public static JsonObject createJSON() {
        return Json.createObjectBuilder()
                .add("id", "0001")
                .add("type", "donut")
                .add("name", "Cake")
                .add("batter", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder().add("id", "1001").add("type", "Regular"))
                        .add(Json.createObjectBuilder().add("id", "1002").add("type", "Chocolate")).build()
                ).build();
    }

    //TODO part2
    public static JsonObject extendJSON(JsonObject jsonObject) {
        JsonArray topping = Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("id", "5001").add("type", "None"))
                .add(Json.createObjectBuilder().add("id", "5002").add("type", "Sugar"))
                .add(Json.createObjectBuilder().add("id", "5003").add("type", "Maple")).build();

        JsonObject extendedJson = Json.createObjectBuilder(jsonObject)
                .add("topping", topping).build();

        return extendedJson;
    }

    public static JsonObject getFinalObject() {
        JsonObject initialJSON = createJSON();
        return extendJSON(initialJSON);
    }
}
