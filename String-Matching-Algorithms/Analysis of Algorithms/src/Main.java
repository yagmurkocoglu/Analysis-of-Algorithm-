import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {
    public static void main(String[] args) {
        boolean exit = false; // flag to exit the program
        while (!exit) {
            // Ask the user for the HTML file option
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select an HTML file option:");
            System.out.println("1. English Text");
            System.out.println("2. Bit String");
            System.out.println("3. Given Example In The Hw PDF");
            System.out.print("Enter the option number: ");
            int option = scanner.nextInt();

            String filePath;
            switch (option) {
                case 1:
                    filePath = "EnglishText.html";
                    break;
                case 2:
                    filePath = "BitString.html";
                    break;
                case 3:
                    filePath = "GivenExample.html";
                    break;
                default:
                    System.out.println("Invalid option. Exiting the program.");
                    return;
            }

            // Operations for reading HTML file
            String text = ""; // Text variable
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(filePath));

                String line;
                while ((line = reader.readLine()) != null) {
                    text += line;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error reading the HTML file. Exiting the program.");
                return;
            }

            // Ask the user for the pattern
            scanner.nextLine(); // Consume the remaining newline character
            System.out.print("Enter the pattern: ");
            String pattern = scanner.nextLine();
            
            // Run all algorithms and get the results
            int bruteForceCount = 0;
            int horspoolCount = 0;
            int boyerMooreCount = 0;

            BruteForceAlgorithm bruteForce = new BruteForceAlgorithm();
            HorspoolAlgorithm horspool = new HorspoolAlgorithm();
            BoyerMooreAlgorithm boyerMoore = new BoyerMooreAlgorithm();

            List<Long> bruteForceTimes = new ArrayList<>();
            List<Long> horspoolTimes = new ArrayList<>();
            List<Long> boyerMooreTimes = new ArrayList<>();

            for (int i = 1; i <= 10; i++) {
                long bruteForceStartTime = System.currentTimeMillis();
                bruteForceCount = bruteForce.search(text, pattern);
                long bruteForceEndTime = System.currentTimeMillis();
                long bruteForceExecutionTime = bruteForceEndTime - bruteForceStartTime;
                bruteForceTimes.add(bruteForceExecutionTime);

                long horspoolStartTime = System.currentTimeMillis();
                horspoolCount = horspool.search(text, pattern);
                long horspoolEndTime = System.currentTimeMillis();
                long horspoolExecutionTime = horspoolEndTime - horspoolStartTime;
                horspoolTimes.add(horspoolExecutionTime);

                long boyerMooreStartTime = System.currentTimeMillis();
                boyerMooreCount = boyerMoore.search(text, pattern);
                long boyerMooreEndTime = System.currentTimeMillis();
                long boyerMooreExecutionTime = boyerMooreEndTime - boyerMooreStartTime;
                boyerMooreTimes.add(boyerMooreExecutionTime);

                // Process results for each run
                System.out.println("Run #" + i);
                System.out.println("Highlighted pattern occurrences (Brute-Force): " + bruteForceCount);
                System.out.println("Highlighted pattern occurrences (Horspool): " + horspoolCount);
                System.out.println("Highlighted pattern occurrences (Boyer-Moore): " + boyerMooreCount);
                System.out.println("Number of character comparisons: " + text.length());
                System.out.println("Execution time (ms):");
                System.out.println("Brute-Force: " + bruteForceExecutionTime + " ms");
                System.out.println("Horspool: " + horspoolExecutionTime + " ms");
                System.out.println("Boyer-Moore: " + boyerMooreExecutionTime + " ms");
                System.out.println("---------------------------------");
            }

            // Save the updated text after processing is complete
            String markedText = text.replaceAll(pattern, "<mark>" + pattern + "</mark>");
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter("output.html"));
                writer.write(markedText);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Create a dataset for the chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 1; i <= 10; i++) {
                dataset.addValue(bruteForceTimes.get(i - 1), "Brute-Force", "Run " + i);
                dataset.addValue(horspoolTimes.get(i - 1), "Horspool", "Run " + i);
                dataset.addValue(boyerMooreTimes.get(i - 1), "Boyer-Moore", "Run " + i);
            }

            // Create the chart
            JFreeChart chart = ChartFactory.createLineChart("Algorithm Execution Time", "Run", "Time (ms)", dataset);

            // Save the chart as an image
            try {
                ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
                System.out.println("Chart created successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}