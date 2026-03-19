
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDeletion} class handles the process of deleting a user from a CSV file ("User-List.csv")
 * based on user type and user ID. It supports input validation and prevents deletion of protected (Root) users.
 *
 * <p>The deletion process consists of:</p>
 * <ul>
 *     <li>Displaying a list of users</li>
 *     <li>Selecting a user type</li>
 *     <li>Inputting a user ID</li>
 *     <li>Validating and removing the user from the file</li>
 * </ul>
 *
 * This class makes use of the {@code ReadFile} utility to print and parse CSV content.
 *
 * @author Alejandro Flores
 * @version 2.0
 */
public class UserDeletion implements Aesthetic{

    /** Path to the CSV file containing user data. */
    private String userListFile = "User-List.csv";

    /** Instance of ReadFile used for printing and parsing CSV file. */
    public ReadFile readFile = new ReadFile();
    public PrintToTerminal printToTerminal = new PrintToTerminal();
    public UserMenu userMenu = new UserMenu();

    /**
     * Constructs a new {@code UserDeletion} object.
     * Initializes the path to the user list file and the ReadFile utility.
     */
    public UserDeletion(){

    }

    /**
     * Interactively deletes a user from the CSV file based on type and ID.
     * <p>
     * The method performs the following steps:
     * <ol>
     *     <li>Displays the user list</li>
     *     <li>Prompts the user to select a user type: Scientist, Space Agency Representative, or Administrator</li>
     *     <li>Prompts the user to input a valid user ID</li>
     *     <li>Deletes the matching user (unless they are a Root user)</li>
     *     <li>Writes the updated list back to the CSV file</li>
     * </ol>
     * <p>
     * The method handles input validation and ensures that only non-Root users can be deleted.
     * It also prints informative messages using ANSI color-coded output.
     * </p>
     */
    public void deleteUser() {

            String userType = "";
            String userToDelete = "";
            int userIDToDelete = -1;
            boolean validInput = false;
            boolean validInteger = false;
            boolean userFound = false;

            while (!validInput) {
                System.out.println(ANSI_CYAN + "\nSelect the type of user list to view by typing the corresponding letter" + ANSI_RESET);
                System.out.println("A: Scientist User List");
                System.out.println("B: Space Agency Representative User List");
                System.out.println("C: Administrator User List");

                userType = (ScannerClass.scan.nextLine()).toUpperCase();
                switch (userType) {
                    case "A":
                     userToDelete = "Scientist";
                        validInput = true;
                        printToTerminal.printFileToTerminal(userListFile, userToDelete);
                        break;
                    case "B":
                     userToDelete = "Space Agency Representative";
                        validInput = true;
                        printToTerminal.printFileToTerminal(userListFile, userToDelete);
                        break;
                    case "C":
                     userToDelete = "Administrator";
                        validInput = true;
                        printToTerminal.printFileToTerminal(userListFile, userToDelete);
                        break;
                    default:
                        System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                }
            }

            System.out.println("Type in an integer value representing the ID belonging to the user to delete");
            System.out.println("Example: " + ANSI_PURPLE + "1051" + ANSI_RESET + " (This deletes the user with the ID number 1051)");
            System.out.println("Type 0 to CANCEL");

            while (!validInteger) {
                if (ScannerClass.scan.hasNextInt()) {
                    userIDToDelete = ScannerClass.scan.nextInt();
                    validInteger = true;
                        
                } else {
                    System.out.println(ANSI_YELLOW + "\n!!! INVALID INPUT !!!" + ANSI_RESET);
                    System.out.println("Type in an integer value representing the ID belonging to the user ");
                    System.out.println("Example: " + ANSI_PURPLE + "10" + ANSI_RESET);
                }
                ScannerClass.scan.nextLine();
            }

            List<String[]> allRows = new ArrayList<>();
            boolean isHeader = true;

            try (BufferedReader br = new BufferedReader(new FileReader(userListFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    List<String> parsedRow = FileParser.parseCSVLine(line);
                    String[] newRow = parsedRow.toArray(new String[0]);

                    if (isHeader) {
                        allRows.add(newRow);
                        isHeader = false;
                        continue;
                    }
                    if(userIDToDelete == 0){
                        userMenu.displayUserMenu("C");
                    }

                    else if(newRow[0].equals(userToDelete) && Integer.parseInt(newRow[1]) == userIDToDelete && newRow[2].equals("Root")){
                        System.out.println(ANSI_YELLOW + "CANNOT DELETE ROOT USER" + ANSI_RESET);
                        allRows.add(newRow);
                        userFound = true;
                        break;
                    }
                    else if (newRow[0].equals(userToDelete) && Integer.parseInt(newRow[1]) == userIDToDelete && !newRow[2].equals("Root")) {
                        System.out.println(ANSI_YELLOW + userToDelete + " with ID " + userIDToDelete + " has been deleted" + ANSI_RESET);
                        SystemLogger.getInstance(userToDelete + " with ID " + userIDToDelete + " was deleted");
                        userFound = true;
                    } else {
                        allRows.add(newRow);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!userFound) {
                System.out.println(ANSI_RED + "No such user found." + ANSI_RESET);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userListFile))) {
                for (String[] row : allRows) {
                    writer.write(String.join(",", row));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}