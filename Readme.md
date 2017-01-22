# Setup

##### Build the reference implementation on JSON-P 1.1
At the time of writing this demo, the JSON-Processing API RI has not been released in the maven central repo. So you have to build the RI by yourself.
- checkout this repo: https://github.com/json-processing-inofficial/jsonp
- simply run mvn clean install

##### Use the RI JSON-P 1.1
Create a project and add the following dependencies
```
<dependencies>
    <dependency>
        <groupId>javax.json</groupId>
        <artifactId>javax.json-api</artifactId>
        <version>1.1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>javax.json</artifactId>
        <version>1.1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
#  What's new in JSON-P 1.1

 * Better builder classes - now you can create an JsonObject/JsonArray builder from and existing JsonObject/JsonArray object, so you don't need to copy all the properties by yourself.
 * JSON Pointer implementation. In you are not familliar what Json Pointer is, please read the IETF specification here -> https://tools.ietf.org/html/rfc6901
 * JSON Patch implementation(https://tools.ietf.org/html/rfc6902)

    JSON Patch provides some operations that can be applied to an JSON object in order to modify it. It uses the JSON Pointer to specify o location from the target object which will be modified.
Please read the specificaion here -> https://tools.ietf.org/html/rfc6902

    There are various implementations of both JSON Pointer or JSON Path in different languages -> here you can find a list http://jsonpatch.com/

 * Process JSON objects/arrays using the JAVA 8 streams operations.
 * Processing of Big JSON Data

    If you read a very big json object you will have some memory issues. To addres this problem the JSON-P 1.1 introduces the class JsonParser. This class uses the streaming model to read an JSON object and works at token level.

##### Exploring the API

There are 3 main classes in JSON-P 1.1 :  **Json**, **JsonArray**, **JsonObject**, **JsonPointer**

1. JSON
```
  /** Factory class for creating JSON processing objects.
  * This class provides the most commonly used methods for creating these
  * objects and their corresponding factories. The factory classes provide
  * all the various ways to create these objects.
  public final class Json {
  }
```
2. JsonObject
```
 /**
 * {@code JsonObject} class represents an immutable JSON object value
 * (an unordered collection of zero or more name/value pairs).
 * It also provides unmodifiable map view to the JSON object
 * name/value mappings.
  public interface JsonObject {
  }
```
3. JsonArray
```
/**
 * {@code JsonArray} represents an immutable JSON array
 * (an ordered sequence of zero or more values).
 * It also provides an unmodifiable list view of the values in the array.
 *
  public interface JsonArray {
  }
```

4. JsonPointer

```
/**
 * <p>This class is an immutable representation of a JSON Pointer as specified in
 * <a href="http://tools.ietf.org/html/rfc6901">RFC 6901</a>.
 * </p>
 * <p> A JSON Pointer, when applied to a target {@link JsonValue},
 * defines a reference location in the target.</p>
  public final class JsonPointer {
  }
```

##### Using the builder classes from JSON-P 1.1

Use JSON-P to create some JSON objects.

Use the followings methods:
* Json.createObjectBuilder
* Json.createObjectBuilder(JsonObject object)
* Json.createArrayBuilder()

```
JsonObject book = Json.createObjectBuilder()
                .add("title", "Snow White")
                .add("year", 1812)
                .build();
System.out.println(book);
```
See the class JsonBuildersExample for more examples.

##### JSON POINTER
JSON Pointer defines a string syntax for identifying a specific value within a JSON document.

Exampple:
```
{
    "foo" : ["bar", "baz"],
    "pi" : 3.1416
}
```
The following JSON Pointers resolve this JSON as:

* "/foo" → [ "bar", "baz" ]
* "/foo/0" → "bar"
* "/foo/1" → "baz"
* "/pi" → 3.1416

Retrieving a value based on a JSON Pointer
```
JsonObject person = Json.createObjectBuilder()
                .add("name", "Posa bogdan")
                .build();
//Applying a pointer
JsonPointer p = new JsonPointer("/name");
JsonValue name = p.getValue(person);

// It will print "Posa bogdan"
System.out.println(name);
```

See the class JsonPointerExample for more examples.


##### JSON PATCH
JSON Patch is a JSON document that contains a sequence of modifications, which they are executed all of them or none of them.
The sequence of modifications that JSON Patch support are test, remove, add, replace, move and copy.
```
Given:
{
  "title":"Guinness",
    "brewery": {
      "key": "guinness"
    }
}
```
if you apply the patch
```
[
  {"op":"replace", "path":"/brewery/key", “value”:"GBrewery"},
  {"op":"remove", "path": "/title"}
]
```
the resulting document will be
```
{
  "brewery": {
    "key": "GBrewery"
  }
}
```

##### Process JSON objects (actually JSON arrays) like JAVA 8 streams
 You can process JSON arrays in the same way you process JAVA 8 streams:

```
//Get a JAVA 8 stream from a JSON Array.
jsonArrayOfObjects.getValuesAs(JsonObject.class).stream()
```


##### Problems/Exercies


###### Problem 1


