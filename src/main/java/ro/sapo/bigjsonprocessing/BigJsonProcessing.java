package ro.sapo.bigjsonprocessing;

import javax.json.*;
import javax.json.stream.JsonGenerationException;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sapo on 15-Jan-17.
 */
public class BigJsonProcessing {

    public static void main (String args[]) throws FileNotFoundException {
        //try to read to all document in memory
        readJSON();

        //parse the document using the streaming API
        //parseWithStreams();

        //parse, apply some operations on the document and save the result.
        //both operations using the streaming API
        //parseAndSave();
    }

    public static void readJSON() throws FileNotFoundException {
        File file = new File("companies.json");

        JsonReader reader = Json.createReader(new FileReader(file));
        JsonObject companies = (JsonObject) reader.read();
        System.out.println(companies.size());
    }

    public static void parseWithStreams() throws FileNotFoundException {
        File file = new File("companies.json");
        FileInputStream inputStream = new FileInputStream(file);
        JsonParser parser = Json.createParser(inputStream);

        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.START_ARRAY) {
                System.out.println(parser.getArrayStream()
                        .map(v -> v.asJsonObject())
                        .filter(x -> x.getString("name").length() > 10).count());
            }
        }
    }

    private static void parseAndSave() throws FileNotFoundException {
        File file = new File("companies.json");
        FileInputStream inputStream = new FileInputStream(file);
        JsonParser parser = Json.createParser(inputStream);

        //this file will be located in the root of the project
        OutputStream fos = new FileOutputStream("companies_out.json");

        Map<String, Object> properties = new HashMap<String, Object>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonGeneratorFactory jsonGeneratorFactory = Json.createGeneratorFactory(properties);

        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(fos);

        jsonGenerator.writeStartArray();
        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.START_ARRAY) {
                parser.getArrayStream()
                        .map(v -> v.asJsonObject())
                        .filter(x -> x.getString("name").length() > 10)
                        .forEach( x -> jsonGenerator.write(x));
            }
        }
        jsonGenerator.writeEnd();
        jsonGenerator.close();
    }
}
