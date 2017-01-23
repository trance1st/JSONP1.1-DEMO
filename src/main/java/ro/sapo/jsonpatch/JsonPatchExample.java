package ro.sapo.jsonpatch;

import javax.json.*;

/**
 * Created by sapo on 15-Jan-17.
 */
public class JsonPatchExample {

    public static void main(String args[]) {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("firstName", "Posa")
                .add("lastName", "Bogdan")
                .build();

        JsonPatchBuilder patchBuilder = Json.createPatchBuilder();

        JsonPatch jsonPatch = patchBuilder.add("/city", "Bucharest")
                .replace("/lastName", "Petre").build();

        System.out.println("JSON PATCH : "  + jsonPatch);

        System.out.println("JSON RESULT : "  + jsonPatch.apply(jsonObject));
    }
}
