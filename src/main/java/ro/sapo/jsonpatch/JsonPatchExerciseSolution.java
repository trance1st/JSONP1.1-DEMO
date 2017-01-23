package ro.sapo.jsonpatch;

import ro.sapo.builders.JsonBuildersExercise;
import ro.sapo.builders.JsonBuildersExerciseSolution;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;

/**
 * Created by sapo on 15-Jan-17.
 */
public class JsonPatchExerciseSolution {

    public static void main (String args[]) {

        JsonObject json = JsonBuildersExerciseSolution.getFinalObject();
        System.out.println("INITIAL JSON: " + json);

        JsonPatch jsonPatch = Json.createPatchBuilder()
                .add("/price", Json.createValue(0.55))
                .replace("/price", Json.createValue(1.55))
                .replace("/batter/1/type", Json.createValue("Vanilla"))
                .add("/topping/-", Json.createObjectBuilder()
                        .add("id", "5004")
                        .add("type", "Vanilla")
                        .build())
                .build();

        System.out.println("PATCH JSON : " + jsonPatch);
        JsonObject result = jsonPatch.apply(json);
        System.out.println("RESULT JSON: " + result);
    }
}
