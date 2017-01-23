package ro.sapo.builders;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Created by sapo on 01-Jul-16.
 */
public class JsonBuildersExample {
    public static void main(String args[]) {

        JsonObject book = Json.createObjectBuilder()
                .add("title", "Snow White")
                .add("year", 1812)
                .build();

        System.out.println(book);

        JsonObject bookExtended = Json.createObjectBuilder(book)
                .add("job", "Java Developer")
                .build();

        System.out.println(bookExtended);

        JsonArray jsonArrayOfStrings = Json.createArrayBuilder()
                .add("Posa").add("Popescu")
                .build();

        System.out.println(jsonArrayOfStrings);

        JsonArray jsonArrayOfObjects = Json.createArrayBuilder()
                .add(book).add(book)
                .build();

        System.out.println(jsonArrayOfObjects);


        JsonObject bookExtendedWithArray = Json.createObjectBuilder(bookExtended)
                .add("similarBooks", jsonArrayOfObjects).build();
        System.out.println(bookExtendedWithArray);
    }
}
