/**
 * The {@code ExtendedUserMenu} class provides the menu-driven interface for
 * scientists to perform actions related to tracking space objects based
 * on object type or orbit type.
 * <p>
 * This class interacts with the {@code ReadFile} class to read and display
 * relevant data from a CSV file and logs the system interactions using
 * {@code SystemLogger}. It also returns to the main scientist menu after
 * executing each operation.
 * </p>
 *
 * <p>Supported tracking options include:</p>
 * <ul>
 *   <li>Rocket Body</li>
 *   <li>Debris</li>
 *   <li>Payload</li>
 *   <li>Unknown</li>
 *   <li>Objects in specific orbits (GEO, HEO, LEO, MEO, Unknown)</li>
 * </ul>
 *
 * @author Alejandro Flores
 * @version 2.3
 */
public class ExtendedUserMenu implements Aesthetic {

    /** Reads object tracking data from CSV files. */
    public ReadFile read = new ReadFile();

    private String orbitType;
    private boolean validOption;



    /**
     * Default constructor for {@code ExtendedUserMenu}.
     * Initializes the object with default values.
     */
    public ExtendedUserMenu() {
    }

    /**
 * Presents and processes the submenu for scientist activities based on the menu type and option selected.
 * <p>
 * Currently only supports "Track Objects" functionality with sub-options to view different
 * space object types or filter by orbit type. It logs each interaction and reads data from the input CSV.
 * </p>
 *
 * @param menuType the type of menu selected (e.g., "Track Objects")
 * @param option the specific submenu option selected by the user (A–E)
 * @param csvFile the path to the CSV file selected by the user
 */
    public void extendScientistMenu(String menuType, String option, String csvFile) {

        UserMenu userMenu = new UserMenu();

        if (menuType.equals("Track Objects")) {

                    switch (option) {

                        case "A":

                                System.out.println(ANSI_CYAN + "\nRocket Body Orbit Category" + ANSI_RESET);
                                SystemLogger.getInstance("Scientist began tracking Rocket Bodies in Orbit - Rocket Body list created");
                            read.readFileInfo(csvFile, "ROCKET BODY");
                            break;

                        case "B":

                                System.out.println(ANSI_CYAN + "\nDebris Orbit Category" + ANSI_RESET);
                                SystemLogger.getInstance("Scientist began tracking Debris in Orbit - Debris list created");
                            read.readFileInfo(csvFile, "DEBRIS");
                            break;

                        case "C":

                                System.out.println(ANSI_CYAN + "\nPayload Orbit Category" + ANSI_RESET);
                                SystemLogger.getInstance("Scientist began tracking Payloads in Orbit - Payload list created");
                            read.readFileInfo(csvFile, "PAYLOAD");
                            break;

                        case "D":

                                System.out.println(ANSI_CYAN + "\nUnknown Orbit Category" + ANSI_RESET);
                                SystemLogger.getInstance("Scientist began tracking Unknown Objects in Orbit - Unknown Objects list created");
                            read.readFileInfo(csvFile, "UNKNOWN");
                            break;

                        case "E":

                                do {
                                        System.out.println(ANSI_CYAN + "\nSelect Orbit Type" + ANSI_RESET);
                                        SystemLogger.getInstance("Scientist queried objects in Specific Orbit");
                                            System.out.println("A: GEO");
                                            System.out.println("B: HEO");
                                            System.out.println("C: LEO");
                                            System.out.println("D: MEO");
                                            System.out.println("E: Unknown");

                                    validOption = true;
                                    orbitType = (ScannerClass.scan.nextLine()).toUpperCase();

                                        switch (orbitType) {

                                            case "A":
                                                        SystemLogger.getInstance("Scientist queried GEO orbit types");
                                                        orbitType = "GEO";
                                                    break;

                                            case "B":
                                                        SystemLogger.getInstance("Scientist queried HEO orbit types");
                                                        orbitType = "HEO";
                                                    break;

                                            case "C":
                                                        SystemLogger.getInstance("Scientist queried LEO orbit types");
                                                        orbitType = "LEO";
                                                    break;

                                            case "D":
                                                        SystemLogger.getInstance("Scientist queried MEO orbit types");
                                                        orbitType = "MEO";
                                                    break;

                                            case "E":
                                                        SystemLogger.getInstance("Scientist queried Unknown orbit types");
                                                        orbitType = "Unknown Orbit Category";
                                                    break;

                                            default:
                                                        System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                                                        validOption = false;
                                        }
                                } while (!validOption);

                                SystemLogger.getInstance("System began scanning: " + csvFile + " For types: " + orbitType);
                                System.out.println(ANSI_CYAN + ANSI_UNDERLINE + "\n" + orbitType + "\n" + ANSI_RESET);
                                read.readFileInfo(csvFile, orbitType);
                                
                            break;

                        default:

                                System.out.println("!!! INVALID INPUT !!!");
                    }
                    userMenu.displayUserMenu("A");
        }
    }


    /**
 * Presents and processes the submenu for Space Agency Representatives (SAR).
 * <p>
 * Based on the selected option, it performs either an overall or country-specific impact analysis
 * using the provided CSV data. Logs all actions and returns to the SAR menu afterward.
 * </p>
 *
 * @param option the submenu option selected ("A" for overall impact, "B" for country-specific impact)
 * @param csvFile the CSV file path used for analysis
 */

    public void extendSARMenu(String option, String csvFile) {
        ImpactAnalysis impactAnalysis = new ImpactAnalysis();

        UserMenu userMenu = new UserMenu();

                    switch (option) { 

                        case "A":

                                System.out.println(ANSI_CYAN + "\nOverall Impact" + ANSI_RESET);
                                SystemLogger.getInstance("Space Agency Representative began analyzing Overall Impact");
                                impactAnalysis.analyzeImpact(csvFile, option);
                                userMenu.displayUserMenu("B");
                            break;

                        case "B":

                                System.out.println(ANSI_CYAN + "\nCountry-specific Impact(Type in the country name: e.g 'JPN')" + ANSI_RESET);
                                SystemLogger.getInstance("Space Agency Representative began analyzing Country-specific Impact");
                                impactAnalysis.analyzeImpact(csvFile, option);
                                userMenu.displayUserMenu("B");
                            break;

                        case "C":
                                userMenu.displayUserMenu("B");
                            break;

                        default:
                                System.out.println("!!! INVALID INPUT !!!");

                }
                    
    }


    /**
     * Displays and processes submenu options for Administrators, including user creation, deletion, and management.
     * <p>
     * This method prompts the admin for user details and validates them (no spaces allowed in names, usernames, or passwords).
     * Delegates tasks to helper classes: {@code UserCreation}, {@code UserDeletion}, and {@code UserManagement}.
     * </p>
     * 
     * <p>
     * All actions are logged via {@code SystemLogger}, and the admin is returned to the main menu after each action.
     * </p>
     *
     * @param menuType the type of administrative operation ("User Creation", "User Deletion", or "User Management")
     */
    public void extendAdminMenu(String menuType) {

        UserMenu userMenu = new UserMenu();
        String userType = "";
        String name = "";
        String newUsername = " ";
        String newPassword = " ";
        Boolean validInput = false;
        Boolean validName = false;
        Boolean validUsername = false;
        Boolean validPassword = false;
        Boolean canCreateUser = true;
        UserCreation userCreation = new UserCreation();
        UserDeletion userDeletion = new UserDeletion();
        UserManagement userManagement = new UserManagement();
        
        if (menuType.equals("User Creation")) {

                                System.out.println(ANSI_CYAN + "\nCreating New User" + ANSI_RESET);
                                System.out.println(ANSI_CYAN + "\nSelect the type of user to be created by typing the corresponding letter:" + ANSI_RESET);
                                System.out.println("A: Scientist");
                                System.out.println("B: Space Agency Representative");
                                System.out.println("C: Administrator");
                                System.out.println("D: Back");

                                    while(!validInput){
                                        String selection = (ScannerClass.scan.nextLine()).toUpperCase();

                                            switch(selection){
                                                
                                                case "A":
                                                    System.out.println("\nNew Scientist");
                                                        userType = "Scientist";
                                                        validInput = true;
                                                    break;

                                                case "B":
                                                    System.out.println("\nNew Space Agency Representative");
                                                        userType = "Space Agency Representative";
                                                        validInput = true;
                                                    break;

                                                case "C":
                                                    System.out.println("\nNew Administrator");
                                                        userType = "Administrator";
                                                        validInput = true;
                                                    break;

                                                case "D":
                                                        validInput = true;
                                                        validName = true;
                                                        validUsername = true;
                                                        validPassword = true;
                                                        canCreateUser = false;
                                                    break;
                                                
                                                default:
                                                    System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                                                    System.out.println(ANSI_YELLOW + "Type the letter corresponding to the user type" + ANSI_RESET);

                                            }
                                    }

                                    
                                //The following checks to make sure no empty spaces are inputted for password and username
                                while(!validName){
                                    System.out.println(ANSI_CYAN + "Add Name (No Empty Spaces Allowed)" + ANSI_RESET);
                                        name = ScannerClass.scan.nextLine();
                                            for(int i = 0; i < name.length(); i++){

                                                if(String.valueOf(name.charAt(i)).equals(" ")){
                                                    System.out.println("Invalid Name");
                                                    validName = false;
                                                    break;
                                                }
                                                else validName = true;
                                            }
                                }

                                while(!validUsername){
                                    System.out.println(ANSI_CYAN + "Add Username (No Empty Spaces Allowed)" + ANSI_RESET);
                                        newUsername = ScannerClass.scan.nextLine();
                                            for(int i = 0; i < newUsername.length(); i++){

                                                if(String.valueOf(newUsername.charAt(i)).equals(" ")){
                                                    System.out.println("Invalid Username");
                                                    validUsername = false;
                                                    break;
                                                }
                                                else validUsername = true;
                                            }
                                }
                                
                                while(!validPassword){
                                    System.out.println(ANSI_CYAN + "Add Password (No Empty Spaces Allowed)" + ANSI_RESET);
                                        newPassword = ScannerClass.scan.nextLine();
                                            for(int i = 0; i < newPassword.length(); i++){

                                                if(String.valueOf(newPassword.charAt(i)).equals(" ")){
                                                    System.out.println("Invalid Password");
                                                    validPassword = false;
                                                    break;
                                                }
                                                else validPassword = true;
                                            }
                                }
                        if(canCreateUser){
                                userCreation.createNewUser(userType, name, newUsername, newPassword); 
                            }
                        userMenu.displayUserMenu("C");
                        ScannerClass.scan.nextLine();
        }

        else if (menuType.equals("User Deletion")) {

                    System.out.println(ANSI_CYAN + "\nDeleting User" + ANSI_RESET);
                                userDeletion.deleteUser();
        }

        else if (menuType.equals("User Management")) {

                    System.out.println(ANSI_CYAN + "\nManaging User" + ANSI_RESET);
                                userManagement.manageUser();
        }
    }
}
