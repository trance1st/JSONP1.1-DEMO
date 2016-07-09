package ro.sapo.problems;

import javax.json.*;
import java.util.Iterator;

/**
 * Created by sapo on 01-Jul-16.
 */
public class Problem1 {

    public static void main(String args[]) {
        JsonObject book = Json.createObjectBuilder()
                .add("title", "Snow White")
                .add("author", "Posa")
                .add("year", 1812)
                .build();

        JsonObject book2 = Json.createObjectBuilder()
                .add("title", "Snow White")
                .add("author", "Sapo")
                .add("year", 1812)
                .build();

        JsonArray jsonArrayOfObjects = Json.createArrayBuilder()
                .add(book).add(book2)
                .build();

        JsonObject bookExtendedWithArray = Json.createObjectBuilder(book)
                .add("similarBooks", jsonArrayOfObjects).build();

        System.out.println(bookExtendedWithArray);


        JsonArray simBooks = bookExtendedWithArray.getJsonArray("similarBooks");

        //sol 1
        for (int i = 0; i < simBooks.size(); i++) {
            JsonPointer newValue = new JsonPointer("/similarBooks/"+i+"/newField");
            bookExtendedWithArray = newValue.add(bookExtendedWithArray, Json.createValue("asd"));
            System.out.println(simBooks.get(i));
        }

        //sol 2
        jsonArrayOfObjects.getValuesAs(JsonObject.class).stream()
                .filter(x -> x.getString("author").equals("Posa"))
                .map(x -> Json.createObjectBuilder(x)
                        .add("newFiled", "asd").build())
                .forEach(System.out::println);
        System.out.println(bookExtendedWithArray);
    }
}

