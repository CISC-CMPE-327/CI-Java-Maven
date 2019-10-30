package ca.queensu.cisc327;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
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
