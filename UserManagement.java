
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles editing of existing users stored in a CSV file ("User-List.csv").
 * 
 * Allows an administrator to:
 * <ul>
 *   <li>View the current list of users</li>
 *   <li>Select a user type and ID to edit</li>
 *   <li>Edit name, username, or password</li>
 * </ul>
 * 
 * Prevents editing of Root users.
 * 
 * Requires: {@code ReadFile} and {@code UserMenu} classes.
 * 
 * Expected CSV Format: 
 * {@code UserType,ID,Name,Username,Password}
 * 
 * Example Row: {@code Scientist,10,John Doe,jdoe,pass123}
 */
public class UserManagement implements Aesthetic{

    private String userListFile = "User-List.csv";
    public PrintToTerminal printToTerminal = new PrintToTerminal();
    public UserMenu userMenu = new UserMenu();

    /**
     * Default constructor for UserManagement.
     */
    public UserManagement(){

    }

    /**
     * Manages user editing through terminal interaction.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Prints the user list</li>
     *   <li>Prompts admin to select user type (Scientist, Space Agency Rep, Administrator)</li>
     *   <li>Prompts for the user ID</li>
     *   <li>Checks if the user exists and is not a Root user</li>
     *   <li>Prompts for a field to edit: Name, Username, or Password</li>
     *   <li>Writes updated data back to the CSV file</li>
     * </ol>
     * 
     * <p>If the user is not found, or if the selected user is a Root, a warning is printed.
     * 
     * <p>Invalid inputs prompt re-entry until valid.
     */
    public void manageUser(){

            String userList = "";
            String userToEdit = "";
            int userIDToEdit = -1;
            boolean validInput = false;
            boolean validInteger = false;
            boolean userFound = false;
            boolean validEditInput = false;

            

            while (!validInput) {
                System.out.println(ANSI_CYAN + "\nSelect the type of user list to view by typing the corresponding letter" + ANSI_RESET);
                System.out.println("A: Scientist User List");
                System.out.println("B: Space Agency Representative User List");
                System.out.println("C: Administrator User List");

                userList = (ScannerClass.scan.nextLine()).toUpperCase();
                switch (userList) {
                    case "A":
                    userToEdit = "Scientist";
                        validInput = true;
                        printToTerminal.printFileToTerminal(userListFile, userToEdit);
                        break;
                    case "B":
                    userToEdit = "Space Agency Representative";
                        validInput = true;
                        printToTerminal.printFileToTerminal(userListFile, userToEdit);
                        break;
                    case "C":
                    userToEdit = "Administrator";
                        validInput = true;
                        printToTerminal.printFileToTerminal(userListFile, userToEdit);
                        break;
                    default:
                        System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                        
            }
        }

            System.out.println("Type in an integer value representing the ID belonging to the user ");
            System.out.println("Example: " + ANSI_PURPLE + "10" + ANSI_RESET + " (This deletes the user with the ID number 10)");
            System.out.println("Type 0 to CANCEL");

            while (!validInteger) {
                if (ScannerClass.scan.hasNextInt()) {
                    userIDToEdit = ScannerClass.scan.nextInt();
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

                    if(userIDToEdit == 0){
                        userMenu.displayUserMenu("C");
                    }

                    else if(newRow[0].equals(userToEdit) && Integer.parseInt(newRow[1]) == userIDToEdit && newRow[2].equals("Root")){
                        System.out.println(ANSI_YELLOW + "CANNOT EDIT ROOT USER" + ANSI_RESET);
                        allRows.add(newRow);
                        userFound = true;
                        break;
                    }
                    else if (newRow[0].equals(userToEdit) && Integer.parseInt(newRow[1]) == userIDToEdit && !newRow[2].equals("Root")) {
                        System.out.println(ANSI_CYAN + "What would you like to change? (Type the letter corresponding to the value)" + ANSI_RESET);
                        System.out.println("A: Name");
                        System.out.println("B: Username");
                        System.out.println("C: Password");
                        System.out.println("D: Cancel");
                        userFound = true;

                            while(!validEditInput){
                                String editValue = (ScannerClass.scan.nextLine()).toUpperCase();
                                    switch(editValue){

                                        case "A":
                                            System.out.println("Type in the new Name");
                                                String newName =ScannerClass.scan.nextLine();
                                                    newRow[2] = newName;
                                                    allRows.add(newRow);
                                                validEditInput = true;
                                                System.out.println(ANSI_YELLOW + "Modified Name for user with ID: " + userIDToEdit + ANSI_RESET);
                                                SystemLogger.getInstance("Modified Name for user with ID: " + userIDToEdit);
                                            break;
                                        case "B":
                                            System.out.println("Type in the new Username");
                                                String newUsername = ScannerClass.scan.nextLine();
                                                    newRow[3] = newUsername;
                                                    allRows.add(newRow);
                                                validEditInput = true;
                                                System.out.println(ANSI_YELLOW + "Modified Username for user with ID: " + userIDToEdit + ANSI_RESET);
                                                SystemLogger.getInstance("Modified Username for user with ID: " + userIDToEdit);
                                            break;
                                        case "C":
                                            System.out.println("Type in the new Password");
                                                String newPassword = ScannerClass.scan.nextLine();
                                                    newRow[4] = newPassword;
                                                    allRows.add(newRow);
                                                validEditInput = true;
                                                System.out.println(ANSI_YELLOW + "Modified Password for user with ID: " + userIDToEdit + ANSI_RESET);
                                                SystemLogger.getInstance("Modified Password for user with ID: " + userIDToEdit);
                                            break;

                                        case "D":
                                            validEditInput = true;
                                            userMenu.displayUserMenu("C");
                                            break;
                                        default:
                                                System.out.println(ANSI_YELLOW + "\n!!! INVALID INPUT !!!" + ANSI_RESET);
                                                System.out.println(ANSI_CYAN + "What would you like to change? (Type the letter corresponding to the value)" + ANSI_RESET);
                                                System.out.println("A: Name");
                                                System.out.println("B: Username");
                                                System.out.println("C: Password");
                                                System.out.println("D: Cancel");
                                    }
                            }
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