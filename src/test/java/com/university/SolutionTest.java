package com.university;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SolutionTest {

    @Test
    public void testSolutionCSVMatchesExpected() {
        String solutionFilePath = "src/main/resources/solution.csv";
        String expectedFilePath = "src/main/resources/expected.csv";

        // Check if solution.csv exists before running the test
        if (Files.exists(Paths.get(solutionFilePath))) {
            fail("The solution.csv file exists before the test runs.");
        }

        try {
            App.main(new String[]{});  // Running the App's main method
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute App.main()");
        }

        // Check if solution.csv was created after running the test
        if (!Files.exists(Paths.get(solutionFilePath))) {
            fail("The solution.csv file does not exist after running the test.");
        }

        // Proceed to compare the solution.csv with expected.csv
        try (BufferedReader solutionReader = new BufferedReader(new FileReader(solutionFilePath));
             BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFilePath))) {

            String solutionLine;
            String expectedLine;

            while ((solutionLine = solutionReader.readLine()) != null && 
                   (expectedLine = expectedReader.readLine()) != null) {
                assertEquals(expectedLine, solutionLine, "Mismatch found in the CSV file content.");
            }
            
            // Ensure both files have the same number of lines
            assertEquals(solutionReader.readLine(), expectedReader.readLine(), "Files have different number of lines.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
