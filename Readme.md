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

#### LAB 

In the source code you will find a separate package for each of the below exercise. 

Each pacakge contains 
* an example class who's name is ending with "Example"
* a txt file describing what needs to be solved
* a class (ending with 'Exercise') where you need to solve the exercise.
* the solution in a class ending with 'Solution' - please see the solution only after you try yourself to solve the exercise.

This lab contains 6 parts

##### 1 - Using the builder classes from JSON-P 1.1

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
>  See the class JsonBuildersExample for more examples.

>  Solve the exercise described in JsonBuildersExercise.txt (use the class JsonBuildersExercise for solution)

##### 2 - JSON POINTER
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

>  See the class JsonPointerExample for more examples.

>  Solve the exercise described in JsonPointerExercise.txt (use the class JsonPointerExercise for solution)

##### 3 - JSON PATCH
JSON Patch is a JSON document that contains a sequence of modifications, which they are executed all of them or none of them.
The sequence of modifications that JSON Patch support are **test**, **remove**, **add**, **replace**, **move** and **copy**.

Given:
```
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
In JSON-P 1.1 you can apply a PATCH to a JSON document using two methods
* creating the JsonObject from scratch
```
// JSON Array with JSON Objects containing JSON Patch fields
JsonArray patchExpression = Json.createArrayBuilder().add(Json.createObjectBuilder().add(…))
                   .add(…);
JsonPatch jsonPatch = new JsonPatch(patchExpression);
 
JsonStructure beer;
 
// Finally the patch is applied to a JSON document
JsonStructure newBeer = jsonPatch.apply(beer);
```
* using the class JsonPatchBuilder 
```
JsonObject beer;
JsonPatchBuilder patchBuilder = new JsonPatchBuilder();
 
// Construct and apply the JSON Patch
JsonStructure newBeer = patchBuilder.replace("/brewery/key", “GBrewery")
                             .remove("/title")
                                    .apply(beer)
```

**Impact on building REST API's

```
PATCH /my/data HTTP/1.1
Host: example.org
Content-Length: 326
Content-Type: application/json-patch+json
```
>  See the class JsonPatchExample for more examples

>  Solve the exercise described in JsonPatchExercise.txt (use the class JsonPatchExercise for solution)


##### 4 - JSON MERGE PATCH

For updating a JSON document there is another specification called JSON Merge Patch (RFC 7386). 
It standardises the update operation, but instead of sending a new JSON document with the operations to apply, you send a document which its syntax mimics the document being modified. You only send the fields with the updates. The special value null is used to indicate that a field should be removed.

Limitation: it is not possible to patch part of a target that is not an object, such as to replace just some of the values in an array.

Given the document
```
{
"firstName":"Posa",
"lastName":"Bogdan"
}
```
if you apply the merge patch
```
{
"firstName":"Ion",
"lastName":null
}
```
you will obtain
```
{
"firstName":"Ion"
}
```
**Impact on building REST API's

```
PATCH /target HTTP/1.1
Host: example.org
Content-Type: application/merge-patch+json
```

>  See the class JsonMergePatchExample for more examples

>  Solve the exercise described in JsonMergePatch.txt (use the class JsonMergePatchExercise for solution)

##### 5 - Process JSON objects (actually JSON arrays) like JAVA 8 streams
 You can process JSON arrays in the same way you process JAVA 8 streams:

```
//Get a JAVA 8 stream from a JSON Array.
jsonArrayOfObjects.getValuesAs(JsonObject.class).stream()
```

>  See the class JsonStreamsExample for more examples.

>  Solve the exercise described in JsonStreamsExercise.txt (use the class JsonStreamsExerciseSolution for solution)

##### 6 - Process big JSON files

In all the above examples, when you load and JsonObject or JsonArray, the whole document is loaded into memory. 

To solve the memory problem, the JsonParser class uses the streaming model to parse a JSON.

>  See the class BigJsonProcessing 

In our example first we will try to read a big json file ( ~ 80MB).

As expected, we are getting an exception
```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.LinkedHashMap.newNode(LinkedHashMap.java:256)
```

Then we will try to solve this problem using the JsonParser class
>  See the method BigJsonProcessing#parseWithStreams()


JSON-P 1.1 offers also a method to write JSON documents using the streaming model. See JsonGenerator class.

```
JsonGenerator#writeStartArray
JsonGenerator#write(String,String)
JsonGenerator#writeEnd
```

>  See the method BigJsonProcessing#parseAndSave()




