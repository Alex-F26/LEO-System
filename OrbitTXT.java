import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The {@code OrbitTXT} class implements the {@code GenerateTXT} interface to generate
 * a plain text summary report of orbit status based on CSV input data.
 * <p>
 * It identifies and writes all rows where the orbit status is marked as "No" to the output
 * TXT file, and also counts the number of objects with "Yes", "Maybe", and "No" orbit status.
 * Output is formatted for readability and includes ANSI colors in the console.
 * </p>
 * 
 * <p>Implements:</p>
 * <ul>
 *   <li>{@code GenerateTXT} - for TXT file generation behavior</li>
 *   <li>{@code Aesthetic} - for ANSI formatting (console output)</li>
 * </ul>
 * 
 * @author Alejandro Flores
 * @version 2.0
 */
public class OrbitTXT implements GenerateTXT, Aesthetic {

    private String[] row = {};
    public int rowCount = 0;
    public boolean isHeader = true;
    public String line;
    public int yesCount = 0;
    public int maybeCount = 0;
    public int noCount = 0;

    /**
     * Reads orbit status data from a CSV file, identifies objects that are marked as "No"
     * (indicating they are no longer in orbit), prints them to the console, and writes them
     * to a TXT output file. Also summarizes the number of objects in each orbit category.
     *
     * @param file     the path to the input CSV file containing orbit data
     * @param filePath the path where the output TXT file will be written
     */
    public void generateTXT(String file, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(file)); 
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("List of Exited Objects:");
            writer.newLine();
            writer.newLine();

            while ((line = br.readLine()) != null) {
                List<String> parsedRow = FileParser.parseCSVLine(line);
                this.row = parsedRow.toArray(new String[0]);

                for (String data : this.row) {
                    if (isHeader) {
                        for (String headerData : this.row) {
                            System.out.print(ANSI_BLUE + "|" + headerData + " " + ANSI_RESET);
                            writer.write("|" + headerData + " ");
                        }
                        System.out.println();
                        writer.newLine();
                        this.isHeader = false;
                    }

                    if (data.equals("No")) {
                        for (String rowData : this.row) {
                            System.out.print("|" + rowData + " ");
                            writer.write("|" + rowData + " ");
                        }
                        writer.newLine();
                    }
                }

                for (String orbitData : this.row) {
                    if (orbitData.equals("Yes")) {
                        yesCount++;
                    } else if (orbitData.equals("Maybe")) {
                        maybeCount++;
                    } else if (orbitData.equals("No")) {
                        noCount++;
                    }
                }
                rowCount++;
            }

            System.out.println();
            writer.newLine();
            writer.write("Objects in orbit:");
            writer.newLine();
            writer.write("Yes: " + yesCount);
            writer.newLine();
            writer.write("Maybe: " + maybeCount);
            writer.newLine();
            writer.write("No: " + noCount);
            writer.newLine();

            SystemLogger.getInstance("System generated Orbit Status Assessment TXT File");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
