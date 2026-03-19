import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Prints CSV file contents to the terminal, filtering by user type.
 * Uses ANSI color codes defined in the {@link Aesthetic} interface.
 */
public class PrintToTerminal implements Aesthetic{

/**
 * Prints the CSV file content filtered by the specified user type.
 * 
 * <p>If {@code userType} matches the user type column in a row, that row is printed.
 * The header row is always printed with blue color.
 * 
 * @param file the path to the CSV file
 * @param userType the user type to filter by (e.g., "Scientist", "Administrator")
 */
public void printFileToTerminal(String file, String userType){
    Boolean isHeader = true;
    String[] row = {};
    Boolean newLine = false;
    Boolean firstNewLine = false;

    System.out.println();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = "";

                while ((line = br.readLine()) != null) {
                    List<String> parsedRow = FileParser.parseCSVLine(line);
                    row = parsedRow.toArray(new String[0]);
                        
                        for(String rowData : row){
                                if(isHeader){
                                        System.out.print(ANSI_BLUE + "| " + rowData + "          " + ANSI_RESET);
                                        newLine = true;
                                        firstNewLine = true;
                                    }
                                else if(rowData.equals(userType)){
                                    System.out.print("| " + rowData + "  ");
                                    isHeader = true;
                                    newLine = true;
                                        for(String rowInfo : row){
                                            if(isHeader){
                                                isHeader = false;
                                            }
                                            else {
                                                System.out.print("| " + rowInfo + "          ");}
                                        }
                                    }
                                else{
                                    continue;
                                }
                            }
                    isHeader = false;
                    while(newLine){
                        System.out.println();
                        newLine = false;
                    }
                    while(firstNewLine){
                        System.out.println();
                        firstNewLine = false;
                    }
                }
            }catch (IOException e){
                    e.printStackTrace();
        }
        System.out.println();
    }
}