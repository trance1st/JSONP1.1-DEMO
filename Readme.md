# Setup

##### Build the reference implementation on JSON-P 1.1
At the time of writing this demo, the JSON-Processing API RI has not been released in the maven central repo. So you have to build the RI by yourself.
- checkout this repo: https://github.com/json-p/api-ri
- simply run mvn clean install


#  What's new in JSON-P 1.1

 * Better builder classes - now you can create a JsonObject/JsonArray builder from an existing JsonObject/JsonArray object, so you don't need to copy all the properties by yourself.
 
 * JSON Pointer implementation. If you are not familiar with what Json Pointer is, please read the IETF specification here -> https://tools.ietf.org/html/rfc6901
 
 * JSON Patch implementation (https://tools.ietf.org/html/rfc6902)

   JSON Patch provides some operations that can be applied to a JSON object in order to modify it. It uses the JSON Pointer to specify a location from the target object which will be modified.
Please read the specification here -> https://tools.ietf.org/html/rfc6902

    There are various implementations of both JSON Pointer or JSON Path in different languages -> here you can find a list http://jsonpatch.com/
 * JSON Merge Patch implementation (https://tools.ietf.org/html/rfc7386)
  
   A JSON merge patch document describes changes to be made to a target
   JSON document using a syntax that closely mimics the document being
   modified. 
 
 
 * Process JSON objects/arrays using the JAVA 8 streams operations.
 * Processing of Big JSON Data

    If you read a very big JSON object you will have some memory issues. To address this problem the JSON-P 1.1 introduces the class JsonParser. This class uses the streaming model to read a JSON object and works at token level.

##### Exploring the API

The main classes in JSON-P 1.1 are:  **Json**, **JsonArray**, **JsonObject**, **JsonPointer**, **JsonPatch**.

The 'starting point' in this API is the class **Json** because it provides methods to create builders, factories and other JSON processing objects.  

#### LAB 

In the source code you will find a separate package for each of the below exercises. 

Each package contains 
* an example class who's name is ending with "Example"
* a txt file describing what needs to be solved
* a class (ending with 'Exercise') where you need to solve the exercise.
* the solution in a class ending with 'Solution' - please see the solution only after you try yourself to solve the exercise.

This lab contains 6 parts

##### 1 - Using the builder classes from JSON-P 1.1

Use JSON-P to create some JSON objects.

Use the following methods:
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

>  Solve the exercise described in JsonBuildersExercise.txt (use the class JsonBuildersExercise to solve)

##### 2 - JSON POINTER

https://tools.ietf.org/html/rfc6901

JSON Pointer defines a string syntax for identifying a specific value within a JSON document.

Example:
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
                .add("name", "Posa Bogdan")
                .build();
//Applying a pointer
JsonPointer p = Json.createPointer("/name");
JsonValue name = p.getValue(person);

// It will print "Posa Bogdan"
System.out.println(name);
```

>  See the class JsonPointerExample for more examples.

>  Solve the exercise described in JsonPointerExercise.txt (use the class JsonPointerExercise to solve)

##### 3 - JSON PATCH

https://tools.ietf.org/html/rfc6902
 
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
JsonPatch jsonPatch = Json.createPatch(patchExpression);
 
JsonStructure beer;
 
// Finally the patch is applied to a JSON document
JsonStructure newBeer = jsonPatch.apply(beer);
```
* using the class JsonPatchBuilder 
```
JsonObject beer;
JsonPatchBuilder patchBuilder = Json.createPatchBuilder();
 
// Construct and apply the JSON Patch
JsonStructure newBeer = patchBuilder.replace("/brewery/key", “GBrewery")
                             .remove("/title")
                                    .apply(beer)
```

>  See the class JsonPatchExample for more examples

>  Solve the exercise described in JsonPatchExercise.txt (use the class JsonPatchExercise to solve)


##### 4 - JSON MERGE PATCH

https://tools.ietf.org/html/rfc7386


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


>  See the class JsonMergePatchExample for more examples

>  Solve the exercise described in JsonMergePatchExercise.txt (use the class JsonMergePatchExercise to solve)

##### 5 - Process JSON objects (actually JSON arrays) like JAVA 8 streams
 You can process JSON arrays in the same way you process JAVA 8 streams:

```
//Get a JAVA 8 stream from a JSON Array.
jsonArrayOfObjects.getValuesAs(JsonObject.class).stream()
```

>  See the class JsonStreamsExample for more examples.

>  Solve the exercise described in JsonStreamsExercise.txt (use the class JsonStreamsExercise to solve)

>  There is an extra exercise in the sub-package 'extra'. See file JsonStreamsMapExercise.txt

##### 6 - Process big JSON files

In all the above examples, when you load a JsonObject or JsonArray, the whole document is loaded into memory. 

To solve the memory problem, the JsonParser class uses the streaming model to parse a JSON.

>  See the class BigJsonProcessing

>  Donwload the file https://drive.google.com/open?id=0B2JsVi_687XKbE91cWE2SDVwMm8 to the root of this project. Name the file 'companies.json'

In our example, first we will try to read a big json file ( ~ 80MB).

As expected, we are getting an exception
```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.LinkedHashMap.newNode(LinkedHashMap.java:256)
```

Then we will try to solve this problem using the JsonParser class
>  See the method BigJsonProcessing#parseWithStreams()


JSON-P 1.1 offers also a method to write JSON documents using the streaming model. See JsonGenerator class.

```
JsonGenerator#writeStartArray()
JsonGenerator#write(JsonValue)
JsonGenerator#writeEnd()
```

>  See the method BigJsonProcessing#parseAndSave()





<center>THANK YOU</center>
	


**USEFULL LINKS:**

https://www.jcp.org/en/jsr/detail?id=374

https://jsonp.java.net/

https://github.com/json-p/api-ri

https://www.voxxed.com/blog/2015/12/10441/

http://blog.rahmannet.net/2017/01/json-p-11-public-review-starts-now.html

http://williamdurand.fr/2014/02/14/please-do-not-patch-like-an-idiot/

http://restful-api-design.readthedocs.io/en/latest/methods.html

