package ca.queensu.cisc327;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

import ca.queensu.cisc327.App;

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
