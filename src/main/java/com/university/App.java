package com.university;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // El test espera que solution.csv esté en src/main/resources
        Path output = Paths.get("src", "main", "resources", "solution.csv");

        try {
            Map<String, Integer> counts;

            if (args.length >= 1) {
                // Si se pasa ruta explícita
                Path input = Paths.get(args[0]);
                counts = EnrollmentCounter.countByStudentName(input);
            } else {
                // Por defecto: lee input.csv desde resources
                counts = EnrollmentCounter.countByStudentNameFromResource("input.csv");
            }

            EnrollmentCounter.writeCountsByStudentName(counts, output);
            System.out.println(" Archivo generado en: " + output.toAbsolutePath());

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}