package ca.queensu.cisc327;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
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
