package com.university;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class EnrollmentCounter {

    // === Lee input.csv desde resources (classpath) ===
    public static Map<String, Integer> countByStudentNameFromResource(String resourceName) throws IOException {
        try (BufferedReader br = openResource(resourceName)) {
            return countUniqueCoursesByStudent(br);
        }
    }

    // === Lee input.csv desde ruta explícita ===
    public static Map<String, Integer> countByStudentName(Path csvPath) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            return countUniqueCoursesByStudent(br);
        }
    }

    // === Lógica: contar cursos únicos por alumno (subject) ===
    private static Map<String, Integer> countUniqueCoursesByStudent(BufferedReader br) throws IOException {
        Map<String, Set<String>> subjectsByStudent = new HashMap<>();

        String headerLine = br.readLine();
        if (headerLine == null) return Map.of();

        char sep = detectSeparator(headerLine);
        List<String> headerCols = splitCsvLine(headerLine, sep);

        int idxStuName = indexOfAlias(headerCols,
                "student name", "student_name", "student", "name", "alumno");
        int idxSubject = indexOfAlias(headerCols,
                "subject", "materia", "curso", "asignatura");

        if (idxStuName < 0 || idxSubject < 0) {
            throw new IOException("Encabezado inválido: falta 'student name' o 'subject'.");
        }

        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) continue;
            List<String> fields = splitCsvLine(line, sep);
            if (fields.size() <= Math.max(idxStuName, idxSubject)) continue;

            String rawName = unquote(fields.get(idxStuName)).trim();
            String subject = unquote(fields.get(idxSubject)).trim();
            if (rawName.isEmpty()) continue;

            subjectsByStudent
                    .computeIfAbsent(rawName, __ -> new HashSet<>())
                    .add(subject);
        }

        Map<String, Integer> counts = new HashMap<>();
        for (var e : subjectsByStudent.entrySet()) {
            counts.put(e.getKey(), e.getValue().size());
        }
        return counts;
    }

    // === Escritura: header EXACTO + salto de línea final extra ===
    public static void writeCountsByStudentName(Map<String, Integer> counts, Path outPath) throws IOException {
        List<String> names = new ArrayList<>(counts.keySet());
        names.sort(String.CASE_INSENSITIVE_ORDER);

        try (BufferedWriter bw = Files.newBufferedWriter(
                outPath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        )) {
            bw.write("Student_Name,Course_Count");
            bw.newLine();
            for (String name : names) {
                bw.write(name + "," + counts.get(name));
                bw.newLine();
            }
            // 👇 este salto extra garantiza que el archivo termina igual que expected.csv
            bw.newLine();
        }
    }

    // =================== Utilidades ===================

    private static char detectSeparator(String headerLine) {
        int commas = countChar(headerLine, ',');
        int semis = countChar(headerLine, ';');
        return (semis > commas) ? ';' : ',';
    }

    private static int countChar(String s, char c) {
        int k = 0;
        for (int i = 0; i < s.length(); i++) if (s.charAt(i) == c) k++;
        return k;
    }

    private static List<String> splitCsvLine(String line, char sep) {
        ArrayList<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == sep && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString());
        return out;
    }

    private static int indexOfAlias(List<String> headerCols, String... aliases) {
        List<String> normAliases = new ArrayList<>();
        for (String a : aliases) normAliases.add(normalizeHeaderToken(a));

        for (int i = 0; i < headerCols.size(); i++) {
            String norm = normalizeHeaderToken(headerCols.get(i));
            for (String a : normAliases) {
                if (norm.equals(a)) return i;
            }
        }
        return -1;
    }

    private static String normalizeHeaderToken(String s) {
        if (s == null) return "";
        s = s.replace("\uFEFF", "");
        s = s.toLowerCase(Locale.ROOT).trim();
        s = s.replace('_', ' ');
        s = s.replaceAll("\\s+", " ");
        return s;
    }

    private static String unquote(String s) {
        if (s == null) return "";
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    private static BufferedReader openResource(String resourceName) throws IOException {
        InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourceName);
        if (in == null) throw new FileNotFoundException("No se encontró en classpath: " + resourceName);
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}