# CI-Java-Maven-Template
Github Actions CI Template for Java+Maven

Folder structure. Typically maven arranges test cases in two different root packages. 

```cmd
.
│   .gitignore
│   LICENSE
│   README.md
└───java-project
    │   pom.xml
    └───src
        ├───main
        │   └───java
        │       └───ca
        │           └───queensu
        │               └───cisc327
        │                       App.java
        └───test
            └───java
                └───ca
                    └───queensu
                        └───cisc327
                                AppTest.java
```

To compile the project:
```
cd java-project
mvn compile
```
To run the your main class:
```
# run from the `java-project` folder
mvn exec:java -Dexec.mainClass="ca.queensu.cisc327.App"
```
To run the test code:
```
# run from the `java-project` folder
mvn test
```

### Command lines used to generate this project (already done):
```
mvn archetype:generate -DgroupId=ca.queensu.cisc327 -DartifactId=java-project -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```