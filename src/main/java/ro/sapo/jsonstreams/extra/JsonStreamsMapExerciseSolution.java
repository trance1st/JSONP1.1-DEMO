package ro.sapo.jsonstreams.extra;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonCollectors;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by sapo on 01-Jul-16.
 */
public class JsonStreamsMapExerciseSolution {

    public static void main(String args[]) throws FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("books.json").getFile());

        JsonReader reader = Json.createReader(new FileReader(file));
        JsonArray books = (JsonArray) reader.read();

        System.out.println(books);

        JsonArray alteredBooks = books.getValuesAs(JsonObject.class).stream()
                .filter(x -> x.getString("author").equals("Posa"))
                .map(x -> Json.createObjectBuilder(x)
                        .add("summary", x.getString("author") + " - " + x.getInt("year")).build())
                .collect(JsonCollectors.toJsonArray());

        System.out.println(alteredBooks);
    }
}

