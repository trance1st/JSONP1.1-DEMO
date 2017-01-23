package ro.sapo.jsonpointer;

import javax.json.*;

/**
 * Created by sapo on 01-Jul-16.
 */
public class JsonPointerExample {

    public static void main(String args[]) {
        JsonObject book = Json.createObjectBuilder()
                .add("title", "Snow White")
                .add("author", Json.createObjectBuilder()
                        .add("name", "Posa")
                        .add("address", "Bucharest, Romania")
                        .build())
                .add("year", 1812)
                .build();
        System.out.println(book);

        //Applying a pointer
        JsonPointer p = new JsonPointer("/author/name");
        JsonValue name = p.getValue(book);

        System.out.println(name);

        //Adding a new field. See the docs: add vs replace !!!
        JsonPointer appenderPointer = new JsonPointer("/anotherTitle");
        System.out.println(appenderPointer.add(book, Json.createValue("I am a new field")));

        //Replacing based on a pointer
        JsonObject plagiarismBook = p.replace(book, Json.createValue("Bogdan"));

        System.out.println(plagiarismBook);

        //Replacing based on a pointer in an array
        JsonArray jsonArrayOfObjects = Json.createArrayBuilder()
                .add(book).add(book)
                .build();

        JsonObject bookExtendedWithArray = Json.createObjectBuilder(book)
                .add("similarBooks", jsonArrayOfObjects).build();

        JsonPointer arrayPointer = new JsonPointer("/similarBooks/0/title");
        System.out.println(arrayPointer.replace(bookExtendedWithArray, Json.createValue("new title")));


        JsonPointer arrayPointerAdd = new JsonPointer("/similarBooks/0");
        System.out.println(arrayPointerAdd.add(bookExtendedWithArray, Json.createObjectBuilder().add("asd", "new").build()));

    }
}
