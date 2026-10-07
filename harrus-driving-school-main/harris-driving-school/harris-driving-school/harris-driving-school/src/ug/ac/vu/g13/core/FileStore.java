package ug.ac.vu.g13.core;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Kefas
 */
public class FileStore {

    public static ArrayList<String> readLines(String fileName) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            Scanner reader = new Scanner(new File("data", fileName));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.length() > 0) {
                    lines.add(line);
                }
            }
            reader.close();
        } catch (Exception e) {
        }
        return lines;
    }

    public static void writeLines(String fileName, ArrayList<String> lines) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(new File("data", fileName)));
            for (int i = 0; i < lines.size(); i++) {
                writer.println(lines.get(i));
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Could not save " + fileName);
        }
    }
}
