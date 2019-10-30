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
    ├───main
    │   └───java
    │       └───ca
    │           └───queensu
    │               └───cisc327       
    │                       App.java
    │
    └───test
        ├───java
           └───ca
               └───queensu
                   └───cisc327
                          AppTest.java
        
  
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
public class App {
    /***
     * An example program: R1 program only accepts 'login' as key R2 print
     * valid_account_list_file's content R3 write 'hmm i am a transaction.' to the
     * transaction_summary_file
     */
    public static void main(String[] args) throws Exception {
        String valid_account_list_file = args[0];
        String transaction_summary_file = args[1];
        System.out.println("what is the key?");

        // R1:
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        // R2:
        if (key.equals("login")) {
            System.out.println("here is the content");
            System.out.println(
                String.join("\n", Files.readAllLines(new File(valid_account_list_file).toPath())));
            System.out.println("writing transactions!");
            Files.write(new File(transaction_summary_file).toPath(), "hmm i am a transaction.".getBytes());
        } else {
            System.out.print("omg wrong key");
        }
        scanner.close();
    }
}
}
```
We created a helper function to test for different cases:
```java

public class AppTest {

    @Test
    public void testAppR1() throws Exception {
        runAndTest(Arrays.asList("login"), //
                Arrays.asList("123456"), //
                Arrays.asList("123456", "writing transactions!"), //
                Arrays.asList("hmm i am a transaction."));
    }

    /**
     * Helper function to run the main function and verify the output
     * 
     * @param terminal_input                 A list of string as the terminal input
     *                                       to run the program
     * 
     * @param valid_accounts                 A list of valid accounts to be used for
     *                                       the test case
     * 
     * @param expected_terminal_tails        A list of string expected at the tail
     *                                       of terminal output
     * 
     * @param expected_transaction_summaries A list of string expected to be in the
     *                                       output transaction summary file
     * 
     * @throws Exception
     */
    public void runAndTest(List<String> terminal_input, //
            List<String> valid_accounts, //
            List<String> expected_terminal_tails, //
            List<String> expected_transaction_summaries) throws Exception {

        // setup parameters for the program to run
        // create temporary files
        File valid_account_list_file = File.createTempFile("valid-accounts", ".tmp");
        Files.write(valid_account_list_file.toPath(), String.join("\n", valid_accounts).getBytes());

        File transaction_summary_file = File.createTempFile("transactions", ".tmp");

        String[] args = { valid_account_list_file.getAbsolutePath(), transaction_summary_file.getAbsolutePath() };

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
        String actual_output = new String(Files.readAllBytes(transaction_summary_file.toPath()), "UTF-8");
        String[] lines = actual_output.split("[\r\n]+");
        for (int i = 0; i < lines.length; ++i)
            assertEquals(expected_transaction_summaries.get(i), lines[i]);

    }

    /**
     * Retrieve the absolute path of the files in the resources folder
     * 
     * @param relativePath The file's relative path in the resources folder
     *                     (/test/resources)
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
