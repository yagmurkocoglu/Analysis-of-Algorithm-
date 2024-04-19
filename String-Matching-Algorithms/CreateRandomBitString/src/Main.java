import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int DEFAULT_LENGTH = 1110000;
    private static final int LINE_BREAK_INTERVAL = 200;

    public static void main(String[] args) {
        generateRandomBitStringHTMLFile("random_bits.html", DEFAULT_LENGTH);
    }

    public static void generateRandomBitStringHTMLFile(String fileName, int length) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("<!DOCTYPE html>\n");
            fileWriter.write("<html>\n");
            fileWriter.write("<head>\n");
            fileWriter.write("<title>Random Bit String</title>\n");
            fileWriter.write("</head>\n");
            fileWriter.write("<body>\n");

            Random random = new Random();
            for (int i = 0; i < length; i++) {
                int bit = random.nextInt(2);
                fileWriter.write(bit + "");
                if ((i + 1) % LINE_BREAK_INTERVAL == 0) {
                    fileWriter.write("<br/>\n");
                }
            }

            fileWriter.write("\n</body>\n");
            fileWriter.write("</html>");
            fileWriter.close();

            System.out.println("Random bit string HTML file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}