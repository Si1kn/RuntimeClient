# RuntimeClient

Welcome! Here is my implementation of a runtime client which edits bytecode automaticly.

### Features / Pros

- It's able to override and "patch (still being worked on)" into methods
- Your able to write your client / new clazzes with the custom annotations

### Cons / Features yet to be implemented

- It's only able to override and "patch (still being worked on)" methods, it cannot change field varibles for e.g: "int
  example;" -> "String example;"
- Doesn't patch correctly
- Not very well documented

### How to set up

First set up the project by cloning and opening this in your ide of choice.

Next in the AgentMain.kt file, you must change the location of the File to your main project's jar.
So far I have not found a way to get around this yet.

To set up, you must add the **"-javaagent"** with your jar location to your java arguments.

For example my one
is: ``-javaagent:\Users\si1kn\IdeaProjects\RuntimeInjectorClient\build\libs\RuntimeInjectorClient-1.0-SNAPSHOT.jar``

*NOTE*: YOURS WILL BE DIFFRENT SO PLEASE MAKE SURE YOU GOT THIS CORRECT!

## For your main project

I wrote mine in java like this:

```
project
│
└───src
│   │
│   └───main
|         └───src
|         |   └───org.example
|         |   |    └───Main
|         |   |    └───NewCode
|         |   |    |
|         |   |    |
|         └───resources
|             └───tm.json
│   
endof project
```

When creating your project you must include a "tm.json" you must add your transformer class' to this here is an example:

````{
"transformerMap": [
  "org.example.Main"
  ]
}
```