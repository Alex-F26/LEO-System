
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

/**
 * Handles the creation of new users and appends them to the CSV file ("User-List.csv").
 * 
 * <p>Supported user types:
 * <ul>
 *   <li>Scientist</li>
 *   <li>Space Agency Representative</li>
 *   <li>Administrator</li>
 * </ul>
 *
 * <p>This class:
 * <ol>
 *   <li>Calculates a unique ID based on existing entries in the file</li>
 *   <li>Creates a new {@code User} object</li>
 *   <li>Appends the user to the appropriate list</li>
 *   <li>Writes new user(s) to the file</li>
 * </ol>
 *
 * Requires: {@code ReadFile} and {@code User} classes.
 */
public class UserCreation implements Aesthetic{

    /** The CSV file that stores all user data. */
    private String outputFile = "User-List.csv";

    /** List of new Scientist users to write. */
    private List<User> scientistUsers = new ArrayList<>();

    /** List of new Space Agency Representative users to write. */
    private List<User> SARUsers = new ArrayList<>();

    /** List of new Administrator users to write. */
    private List<User> adminUsers = new ArrayList<>();

    /** ID assigned to the newly created user. */
    private int ID;

    private boolean validID = false;

    /** Helper object for reading and parsing CSV data. */
    public ReadFile readFile = new ReadFile();

    /**
     * Default constructor for UserCreation.
     */
    public UserCreation() {
    }

    /**
     * Creates a new user, assigns an ID, and appends the user to the CSV file.
     *
     * @param userType the type of user to create (must be one of: "Scientist", "Space Agency Representative", or "Administrator")
     * @param name the full name of the user
     * @param username the login username
     * @param password the account password
     */
    public void createNewUser(String userType, String name, String username, String password) {

        String line = "";
        String[] newRow = {};
        int tempID = 0;
        ExtendedUserMenu extendedUserMenu = new ExtendedUserMenu();

        try(BufferedReader br = new BufferedReader(new FileReader(outputFile))){
            while ((line = br.readLine()) != null) {
                validID = false;
                List<String> parsedRow = FileParser.parseCSVLine(line);
                newRow = parsedRow.toArray(new String[0]);

                for(String data : newRow){
                    if(data.equals(username)){
                        System.out.println("Username already in use");
                        extendedUserMenu.extendAdminMenu("User Creation");
                        continue;
                    }
                }

                while(!validID){
                    tempID = (int)((Math.random()* (9999 - 1000)) + 111);
                    for(String data : newRow){
                        if(data.equals(String.valueOf(tempID))){
                            tempID = (int)((Math.random()* (9999 - 1000)) + 111);
                        }
                    }
                    ID = tempID;
                    validID = true;
                }
            }
            }catch(IOException e){
            e.printStackTrace();
        }

        if (userType.equals("Scientist")) {
                userType = "Scientist";
                scientistUsers.add(new User(userType, ID, name, username, password));
                System.out.println(ANSI_YELLOW + "New Scientist user was created" + ANSI_RESET);
                SystemLogger.getInstance("New Scientist user was created");
        } else if (userType.equals("Space Agency Representative")) {
                userType = "Space Agency Representative";
                SARUsers.add(new User(userType, ID, name, username, password));
                System.out.println(ANSI_YELLOW + "New Space Agency Representative user was created" + ANSI_RESET);
                SystemLogger.getInstance("New Space Agency Representative user was created");
        } else if (userType.equals("Administrator")) {
                userType = "Administrator";
                adminUsers.add(new User(userType, ID, name, username, password));
                System.out.println(ANSI_YELLOW + "New Administrator user was created" + ANSI_RESET);
                SystemLogger.getInstance("New Administrator user was created");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {

            for (User Scientist : scientistUsers) {
                writer.write(Scientist.userType + "," + Scientist.ID + "," + Scientist.name + "," + Scientist.username + "," + Scientist.password);
                writer.newLine();
            }
            scientistUsers.clear();

            for (User SAR : SARUsers) {
                writer.write(SAR.userType + "," + SAR.ID + "," + SAR.name + "," + SAR.username + "," + SAR.password);
                writer.newLine();
            }
            SARUsers.clear();

            for (User Admin : adminUsers) {
                writer.write(Admin.userType + "," + Admin.ID + "," + Admin.name + "," + Admin.username + "," + Admin.password);
                writer.newLine();
            }
            adminUsers.clear();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
