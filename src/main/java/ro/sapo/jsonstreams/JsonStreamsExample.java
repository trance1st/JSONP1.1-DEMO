package ro.sapo.jsonstreams;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 * Created by sapo on 22-Jan-17.
 */
public class JsonStreamsExample {

    public static void main(String args[]) {

        JsonObject person1 = Json.createObjectBuilder()
                .add("firstName", "Posa")
                .add("lastName", "Bogdan")
                .build();

        JsonObject person2 = Json.createObjectBuilder()
                .add("firstName", "Popescu")
                .add("lastName", "Ion")
                .build();

        JsonArray jsonArray = Json.createArrayBuilder().add(person1).add(person2).build();

        System.out.println(
                jsonArray.getValuesAs(JsonObject.class)
                .stream()
                .filter(x -> x.getString("firstName").length() == 4)
                .count()
        );

    }
}
