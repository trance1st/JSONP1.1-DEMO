package ro.sapo.builders;

import javax.json.JsonObject;

/**
 * Created by sapo on 15-Jan-17.
 */
public class JsonBuildersExercise {


    public static void main(String args[]) {

        JsonObject initialJSON = createJSON();
        System.out.println(initialJSON);

        JsonObject extendedJSON = extendJSON(initialJSON);
        System.out.println(extendedJSON);
    }

    //TODO part1
    public static JsonObject createJSON() {
        return null;
    }

    //TODO part2
    public static JsonObject extendJSON(JsonObject jsonObject) {
        return null;
    }

    public static JsonObject getFinalObject() {
        JsonObject initialJSON = createJSON();
        return extendJSON(initialJSON);
    }
}
