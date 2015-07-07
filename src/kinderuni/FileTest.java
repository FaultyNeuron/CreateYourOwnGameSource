package kinderuni;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.*;
import java.lang.System;

/**
 * Created by markus on 07.07.15.
 */
public class FileTest  {
    public static void main(String[] args) {
        java.lang.System.out.println("Hello, World");
        File file = new File("This is a not existing file.txt");
        try {
            FileReader fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            String[] strings = e.getMessage().split("\\(");
            if (strings.length > 0) {
                System.out.println("Exception message: " + strings[0]);
            }
        }

    }
}
