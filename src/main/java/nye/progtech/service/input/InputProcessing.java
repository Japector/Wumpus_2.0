package nye.progtech.service.input;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.sun.tools.javac.Main;
import nye.progtech.exceptions.InvalidInput;

public class InputProcessing {


    public Stream<String> fileInput(File fileInput) {
        List<String> lines = new ArrayList<>();
        if (!fileInput.exists()) {
            System.out.println("The file does not exist.");
            return Stream.empty();
        }
        try (Scanner scanner = new Scanner(fileInput)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            throw new InvalidInput("File could not be processed: " + ex.getMessage());
        }
        return lines.stream();
    }

    public static Stream<String> readResource(String resourcePath) {
        List<String> lines = new ArrayList<>();
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            throw new InvalidInput("Could not read lines: " + ex.getMessage());
        }
        return lines.stream();
    }
}
