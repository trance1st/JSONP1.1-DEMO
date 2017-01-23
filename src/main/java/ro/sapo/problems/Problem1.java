package ro.sapo.problems;

import javax.json.*;
import javax.json.stream.JsonCollectors;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by sapo on 01-Jul-16.
 */
public class Problem1 {

    public static void main(String args[]) throws FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("problem1.json").getFile());

        JsonReader reader = Json.createReader(new FileReader(file));
        JsonObject books = (JsonObject) reader.read();
        System.out.println(books);

        JsonArray simBooks = books.getJsonArray("similarBooks");
        System.out.println(simBooks);

        //sol 1
        for (int i = 0; i < simBooks.size(); i++) {
            JsonPointer newField = Json.createPointer("/similarBooks/"+i+"/summary");

            //Calculate the new value
            JsonPointer currentAuthorPointer = Json.createPointer("/similarBooks/"+i+"/author");
            JsonPointer currentYearPointer = Json.createPointer("/similarBooks/"+i+"/year");
            String newValue = currentAuthorPointer.getValue(books)
                    + "-"
                    + currentYearPointer.getValue(books);

            //Add the new value in the json object
            books = newField.add(books, Json.createValue(newValue));
        }

        System.out.println(books);

        //sol 2
        JsonArray alteredBooks = simBooks.getValuesAs(JsonObject.class).stream()
                .filter(x -> x.getString("author").equals("Posa"))
                .map(x -> Json.createObjectBuilder(x)
                        .add("summary", x.getString("author") + " - " + x.getInt("year")).build())
                .collect(JsonCollectors.toJsonArray());

        System.out.println(alteredBooks);
    }
}

