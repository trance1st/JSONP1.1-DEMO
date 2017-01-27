package ro.sapo.jsonstreams.extra;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by sapo on 01-Jul-16.
 */
public class JsonStreamsMapExercise {

    public static void main(String args[]) throws FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("books.json").getFile());
        JsonReader reader = Json.createReader(new FileReader(file));
        JsonArray books = (JsonArray) reader.read();

        System.out.println(books);


    }
}

