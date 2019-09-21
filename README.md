# CI-Java-Maven-Template

![](https://github.com/CISC-CMPE-327/CI-Java-Maven/workflows/Java%20CI/badge.svg)

Github Actions CI Template for Java+Maven

To install maven: https://maven.apache.org/install.html

Project folder structure. Typically maven arranges test cases in a saperated root folder. 

```cmd
.
│   .gitignore
│   LICENSE
│   pom.xml
│   README.md
│
├───src
│   ├───main
│   │   └───java
│   │       └───ca
│   │           └───queensu
│   │               └───cisc327
│   │                       App.java
│   │
│   └───test
│       └───java
│           └───ca
│               └───queensu
│                   └───cisc327
│                           AppTest.java
```

To compile the project:
```
mvn compile
```
To run the your main class:
```
mvn exec:java -Dexec.mainClass="ca.queensu.cisc327.App"
```
To run the test code:
```
mvn test
```

#### Command lines used to generate this project (already done):
```
mvn archetype:generate ^
  -DgroupId=ca.queensu.cisc327 ^
  -DartifactId=java-project ^
  -DarchetypeArtifactId=maven-archetype-quickstart ^
  -DinteractiveMode=false
```
