package ro.sapo.jsonmergepath;

import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.*;

/**
 * Created by sapo on 22-Jan-17.
 */
public class JsonMergePatchExample {

    public static void main(String args[]) {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("firstName", "Posa")
                .add("lastName", "Bogdan")
                .build();

        System.out.println(jsonObject);

        //replace 'firstName' and remove 'lastName'
        JsonObject mergePatchDocument = Json.createObjectBuilder()
                .add("firstName", "Ion")
                .add("lastName", JsonValue.NULL)
                .build();
        System.out.println(mergePatchDocument);

        JsonMergePatch jsonMergePatch = Json.createMergePatch(mergePatchDocument);

        System.out.println(jsonMergePatch.apply(jsonObject));

    }
}
