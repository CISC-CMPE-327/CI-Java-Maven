# CI-Java-Maven-Template

[![](https://github.com/CISC-CMPE-327/CI-Java-Maven/workflows/Java%20CI/badge.svg)]((https://github.com/CISC-CMPE-327/CI-Java-Maven/actions)

Github Actions CI Template for Java+Maven 

To install maven: https://maven.apache.org/install.html

Project folder structure. Typically maven arranges test cases in a saperated root folder. 

```cmd
C:.
│   .gitignore
│   LICENSE
│   pom.xml  ============> define dependencies and your main class
│   README.md
│
├───.github
│   └───workflows
│           maven.yml  =========> defined CI process for GitHub Actions
│
├───src
│   ├───main
│   │   └───java
│   │       └───ca
│   │           └───queensu
│   │               └───cisc327
│   │                       App.java  ======> general class
│   │
│   └───test
│       └───java
│           └───ca
│               └───queensu
│                   └───cisc327
│                           AppTest.java ===> Unit test class for App.java
```

To compile the project:
```
mvn compile
```
To run the unit test code:
```
mvn test
```
Package for release
```
mvn package
```
If successful, the package will be in the target folder.
To run the package:
```
cd target
java -jar java-project-1.0-SNAPSHOT.jar your_class
```

#### Command lines used to generate this project (already done):
```
mvn archetype:generate ^
  -DgroupId=ca.queensu.cisc327 ^
  -DartifactId=java-project ^
  -DarchetypeArtifactId=maven-archetype-quickstart ^
  -DinteractiveMode=false
```
