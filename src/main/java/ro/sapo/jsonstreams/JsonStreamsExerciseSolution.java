package ro.sapo.jsonstreams;

import ro.sapo.builders.JsonBuildersExercise;
import ro.sapo.builders.JsonBuildersExerciseSolution;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;

/**
 * Created by sapo on 15-Jan-17.
 */
public class JsonStreamsExerciseSolution {

    public static void main(String args[]) {
        JsonObject json = JsonBuildersExerciseSolution.getFinalObject();

        JsonArray filteredTopping = json.get("topping").asJsonArray().getValuesAs(JsonObject.class).stream()
                .filter( x -> x.getString("type").length() == 5)
                .collect(JsonCollectors.toJsonArray());

        System.out.println(filteredTopping);
    }

}
