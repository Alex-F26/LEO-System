import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Handles reading, parsing, and displaying data from CSV files
 * for space object tracking and analysis. This class uses a custom
 * CSV parser to properly handle quoted fields and formats the output
 * with ANSI styling.
 * <p>
 * It provides functionality to locate and display specific rows from a CSV file
 * based on a keyword or value match, skipping the header during the search.
 * </p>
 * 
 * @author Alejandro Flores
 * @version 2.3
 */
public class ReadFile implements Aesthetic {

    public Assessment assess = new Assessment();
    public String[] row = {};
    public int rowCount = 0;
    public boolean isHeader = true;
    public String line;

    /**
     * Constructs a new ReadFile instance.
     */
    public ReadFile() {
    }

    /**
     * Reads the given CSV file line by line and searches for a row
     * containing the specified input value. If a match is found,
     * it prints the entire row to the console in a formatted style.
     * The header row is skipped during the search.
     *
     * @param file  Path to the input CSV file.
     * @param input Value to search for within each row of the file.
     */
    public void readFileInfo(String file, String input) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((this.line = br.readLine()) != null) {
                List<String> parsedRow = FileParser.parseCSVLine(this.line);
                this.row = parsedRow.toArray(new String[0]);

                for (String data : this.row) {
                    if (this.isHeader) {
                        this.isHeader = false;
                        continue;
                    }

                    if (input.equals(data)) {
                        for (String rowData : this.row) {
                            System.out.print("|" + rowData + " ");
                        }
                        System.out.println();
                        break;
                    }
                }
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
