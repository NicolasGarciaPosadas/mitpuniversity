
# 1 - Java CSV Aggregation Assignment

## Objective

The goal of this assignment is to create a Java program that reads student data from a CSV file, processes it, and outputs aggregated data to another CSV file. You will also write tests to verify that you designed classes work as they are supposed to.

## Steps to Complete

1. **Design Classes:**
   - You need to design a set of Java classes to represent the student data (such as `Student`, `Course`, etc.).
   
2. **Read Input CSV:**
   - Read the provided `students.csv` from the `src/main/resources/` folder.
   - Parse the CSV data into a collection of you designed objects.

3. **Aggregate Data:**
   - Write logic to group students by course and compute aggregated data.

4. **Write Output CSV:**
   - Write the aggregated data into a new CSV file named `solution.csv` in the `src/main/resources/` folder.

5. **Test Your Solution:**
   - Write multiple JUnit tests for your classes

## How to Run

1. **Run the Main Class:**
   - The main entry point is the `App.java` file in the `com.university` package.
   - When you run this file, it will generate a CSV file named `solution.csv` in the `src/main/resources/` folder.

2. **Run the Test:**
   - A test class named `SolutionTest.java` is provided to compare `solution.csv` and `expected.csv`.
   - Run the test using your preferred IDE or command line, and it will report if the output matches the expected result.

## Files Provided

- `students.csv`: Input file containing the raw student data.
- `expected.csv`: Expected output after processing the input file.

## What You Need to Do

- Complete the implementation in `App.java` to read, process, and write the CSV files.
- Ensure your code passes the test in `AppTest.java`, plus your own tests.
