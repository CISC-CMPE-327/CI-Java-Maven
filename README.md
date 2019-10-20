# CI-Java-Maven-Template

[![](https://github.com/CISC-CMPE-327/CI-Java-Maven/workflows/Java%20CI/badge.svg)](https://github.com/CISC-CMPE-327/CI-Java-Maven/actions)

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
│   │                       App.java
│   │
│   └───test
│       ├───java
│       │   └───ca
│       │       └───queensu
│       │           └───cisc327
│       │                   AppTest.java
│       │
│       └───resources
│           └───R2
│                   expected.txt
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

This example app has two simple requirements:

```java
public class App 
{
    /***
     * An example program that has two functionalities:
        R1/ Ask for user's name and print it out
        R2/ Write 'hello 327' to the file specified by app
            argument if the user's name is 327.
     */
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello world!" );

        // R1:
        System.out.println("What is your name please?:");
        Scanner scanner = new Scanner(System.in);
        String name =scanner.nextLine();
        System.out.println("Hello " + name);

        // R2:
        if(name.equals("327")){
            Files.write(new File(args[0]).toPath(), "hello 327".getBytes());
            System.out.println("file written!");
        }
        scanner.close();
    }
}
```
We created a helper function to test for different cases:
```java
public class AppTest {

    @Test
    public void testAppR1() throws Exception {
        runAndTest(Arrays.asList("steven"), Arrays.asList("Hello steven"), null);

    }

    @Test
    public void testAppR2() throws Exception {
        runAndTest(Arrays.asList("327"), Arrays.asList("Hello 327", "file written!"),
                getFileFromResource("/R2/expected.txt"));

    }

    /**
     * Helper function to run the main function and verify the output
     * @param terminal_input A list of string as the terminal input to run the program
     * @param expected_terminal_tails A list of string expected at the tail of terminal output
     * @param expected_output_file A file that contains the expected content for the output file
     * @throws Exception
     */
    public void runAndTest(List<String> terminal_input, List<String> expected_terminal_tails,
            String expected_output_file) throws Exception {

        // setup parameters for the program to run
        // create a temporary file
        File tmpFile = File.createTempFile("temp-test", ".tmp");
        String[] args = { tmpFile.getAbsolutePath() };

        // setup user input
        String userInput = String.join(System.lineSeparator(), terminal_input);
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // setup stdin & stdout:
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // run the program
        App.main(args);

        // capture terminal outputs:
        String[] printed_lines = outContent.toString().split("[\r\n]+");

        // compare the tail of the terminal outputs:
        int diff = printed_lines.length - expected_terminal_tails.size();
        for (int i = 0; i < expected_terminal_tails.size(); ++i) {
            assertEquals(expected_terminal_tails.get(i), printed_lines[i + diff]);
        }

        // compare output file content to the expected content
        if (expected_output_file != null) {
            String expected_output = new String(Files.readAllBytes(Paths.get(expected_output_file)), "UTF-8");
            String actual_output = new String(Files.readAllBytes(tmpFile.toPath()), "UTF-8");
            assertEquals(expected_output, actual_output);
        }

    }

    /**
     * Retrieve the absolute path of the files in the resources folder
     * @param relativePath The file's relative path in the resources folder (/test/resources)
     * @return the absolute path of the file in the resource folder. 
     */
    String getFileFromResource(String relativePath) {
        return new File(this.getClass().getResource(relativePath).getFile()).getAbsolutePath();
    }
}
```

#### Command lines used to generate this project (already done):
```
mvn archetype:generate ^
  -DgroupId=ca.queensu.cisc327 ^
  -DartifactId=java-project ^
  -DarchetypeArtifactId=maven-archetype-quickstart ^
  -DinteractiveMode=false
```
