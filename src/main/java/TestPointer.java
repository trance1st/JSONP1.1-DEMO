import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.json.JsonValue;

/**
 * Created by sapo on 09-Jul-16.
 */
public class TestPointer {

    public static void main(String args[]) {

        JsonObject person = Json.createObjectBuilder()
                .add("name", "Posa bogdan")
                .build();

        //Applying a pointer
        JsonPointer p = new JsonPointer("/name");
        JsonValue name = p.getValue(person);

        System.out.println(name);
    }
}
