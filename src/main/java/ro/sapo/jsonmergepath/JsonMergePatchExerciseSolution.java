package ro.sapo.jsonmergepath;

import ro.sapo.builders.JsonBuildersExercise;
import ro.sapo.builders.JsonBuildersExerciseSolution;

import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Created by sapo on 22-Jan-17.
 */
public class JsonMergePatchExerciseSolution {

    public static void main(String args[]) {

        JsonObject jsonObject = JsonBuildersExerciseSolution.getFinalObject();

        JsonObject mergePatchDoc = Json.createObjectBuilder()
                .add("price", "0.55")
                .add("price", "1.55")
                .add("batter", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder().add("id", "1001").add("type", "Regular"))
                        .add(Json.createObjectBuilder().add("id", "1002").add("type", "Chocolate")).build())
                .build();

        JsonMergePatch jsonMergePatch = Json.createMergePatch(mergePatchDoc);

        System.out.println(jsonMergePatch.apply(jsonObject));


    }
}
