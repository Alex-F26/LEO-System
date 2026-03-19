import java.util.InputMismatchException;

/**
 * The {@code SubMenu} class handles user interactions for submenus after the
 * main menu login. It displays menus and processes the user's selection based on their role
 * (Scientist in this implementation) and calls the appropriate functionality.
 * <p>
 * Depending on whether the user is a Scientist, Space Agency Representative,
 * or Administrator, a different set of options is printed to the console.
 * This class supports logging, user prompts, and function delegation to other
 * parts of the program such as reading files or displaying nested menus.
 * </p>
 * <p>Example usage:</p>
 * <pre>
 * SubMenu.displayUserMenu("A"); // shows Scientist menu
 * </pre>
 * 
 * 
 * <p>Currently, only Scientist-specific submenu options are implemented.</p>
 * 
 * @author Alejandro Flores
 * @version 2.3
 */
public class UserMenu implements Aesthetic {

        public ExtendedUserMenu extendedUserMenu = new ExtendedUserMenu();
        public OpenFolder openFolder = new OpenFolder();
        public ReadFile readFile = new ReadFile();
        public GenerateCSVFiles generateCSVFiles = new GenerateCSVFiles();
        public GenerateTXT orbitTXT = new OrbitTXT();
        public GenerateTXT collisionTXT = new CollisionTXT();
        private Boolean optionIsValid;
        private String menuType;
        private String csvFile;
       

    /**
     * Default constructor for {@code UserMenu}.
     * <p>No initialization logic is required.</p>
     */
    public UserMenu() {
    }


    /**
     * Displays a user-specific menu based on the provided user type.
     * <p>
     * If the user type is:
     * <ul>
     *     <li>"A" - Scientist menu is shown</li>
     *     <li>"B" - Space Agency Representative menu is shown</li>
     *     <li>"C" - Administrator menu is shown</li>
     * </ul>
     * After displaying the menu, control is passed to {menuType()}.
     * </p>
     *
     * @param userLoggedIn a string representing the type of user ("A", "B", or "C")
     */
    public void displayUserMenu(String userLoggedIn) {

        MainMenu mainMenu = new MainMenu();

        if (userLoggedIn.equals("A")) { // Scientist

            System.out.println(ANSI_CYAN + "\nScientist: Select an Option by typing the corresponding letter" + ANSI_RESET);
                System.out.println("A: Track Objects in Space");
                System.out.println("B: Assess Orbit Status");
                System.out.println("C: Assess Collision Risk");
                System.out.println("D: Back");

                do {
                    optionIsValid = true;
                    //User picks a menu option
                    String userChoice = (ScannerClass.scan.nextLine()).toUpperCase();
                
                
                    switch (userChoice) {

                        case "A":

                                menuType = "Track Objects";
                                    System.out.println(ANSI_CYAN + "\nTrack Objects In Space: Select an Option by typing the corresponding letter" + ANSI_RESET);
                                    SystemLogger.getInstance("Scientist initiated Option: 'Track Objects'");
                                        System.out.println("A: Rocket Body");
                                        System.out.println("B: Debris");
                                        System.out.println("C: Payload");
                                        System.out.println("D: Unknown");
                                        System.out.println("E: Track Objects in Specific Orbit");
                                String option = (ScannerClass.scan.nextLine()).toUpperCase();
                                System.out.println(ANSI_YELLOW + "\nSelect and open your data file" + ANSI_RESET);
                                csvFile = openFolder.openFolder();
                                extendedUserMenu.extendScientistMenu(menuType, option, csvFile);
                                break;

                        case "B":

                                menuType = "Assess Orbit";
                                    System.out.println(ANSI_CYAN + "Assess Orbit Status" + ANSI_RESET);
                                    SystemLogger.getInstance("Scientist initiated Option: 'Assess Orbit Status'");
                                    System.out.println(ANSI_YELLOW + "\nSelect and open your data file" + ANSI_RESET);
                                        csvFile = openFolder.openFolder();
                                        GenerateCSVFiles.generateCSV(csvFile, "newOrbitCSV.csv", "Orbit");
                                        orbitTXT.generateTXT("newOrbitCSV.csv", "Orbit Status Assessment");
                                    
                                displayUserMenu("A");
                                break;

                        case "C":

                                menuType = "Assess Collision";
                                        System.out.println(ANSI_CYAN + "\nAssess Collision Risk" + ANSI_RESET);
                                        SystemLogger.getInstance("Scientist initiated Option: 'Assess Collision Risk'");
                                        System.out.println(ANSI_YELLOW + "\nSelect and open your data file" + ANSI_RESET);
                                            csvFile = openFolder.openFolder();
                                            GenerateCSVFiles.generateCSV(csvFile, "newCollisionCSV.csv", "Collision");
                                            collisionTXT.generateTXT("newCollisionCSV.csv", "Collision Risk Assessment");
                                        
                                displayUserMenu("A");
                                break;

                        case "D":

                                    System.out.println(ANSI_RED + "\n(Scientist logged out - Returned to the Main Menu)" + ANSI_RESET);
                                    SystemLogger.getInstance("Scientist logged out - Returned to the Main Menu");
                                    mainMenu.login();  
                                break;

                        default:

                                    System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                                    displayUserMenu("A");
                                optionIsValid = false;
                    }
                }while (!optionIsValid);
            } 

        else if (userLoggedIn.equals("B")) { // SAR

            System.out.println(ANSI_CYAN + "\nS.A.R: Select an Option by typing the corresponding letter" + ANSI_RESET);
                System.out.println("A: Analyze Long-term Impact");
                System.out.println("B: Generate Density Reports");
                System.out.println("C: Back");

                do {
                    optionIsValid = true;
                    String userChoice = (ScannerClass.scan.nextLine()).toUpperCase();
                    

                    switch (userChoice) {

                        case "A":

                                menuType = "Analyze Long Term Impact";
                                SystemLogger.getInstance("Space Agent Representative initiated option: 'Analyze Long Term Impact'");
                                    System.out.println(ANSI_CYAN + "\nAnalyze Long Term Impact: Select an Option by typing the corresponding letter" + ANSI_RESET);
                                    System.out.println("A: Overall Impact");
                                    System.out.println("B: Country-specific Impact");
                                    System.out.println("C: Back");
                                        String option = (ScannerClass.scan.nextLine()).toUpperCase();
                                        System.out.println(ANSI_YELLOW + "\nSelect and open your data file" + ANSI_RESET);
                                        csvFile = openFolder.openFolder();
                                        extendedUserMenu.extendSARMenu(option, csvFile);
                                    displayUserMenu("B");
                                break;

                        case "B":
                                DensityAnalysis densityAnalysis = new DensityAnalysis();
                                double longitude1 = -1;
                                double longitude2 = -1;
                                Boolean validInput = false;
                                menuType = "Generate Density Reports";
                                    System.out.println(ANSI_CYAN + "\nGenerate Density Reports" + ANSI_RESET);
                                    SystemLogger.getInstance("Space Agent Representative began generating density reports");

                                while(!validInput){
                                        try{
                                                System.out.println(ANSI_CYAN + "\nEnter Two Longitude Values(One at a time)" + ANSI_RESET);
                                                System.out.println(ANSI_GREEN + "First Value:" + ANSI_RESET);
                                                    longitude1 = ScannerClass.scan.nextDouble();
                                                System.out.println(ANSI_GREEN + "Second Value:" + ANSI_RESET);
                                                    longitude2 = ScannerClass.scan.nextDouble();
                                                System.out.println(ANSI_YELLOW + "\nSelect and open your data file" + ANSI_RESET);
                                                    csvFile = openFolder.openFolder();
                                                    validInput = true;

                                        }catch(InputMismatchException e){
                                                System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                                                System.out.println(ANSI_YELLOW + "Type a numerical value\n" + ANSI_RESET);
                                                    validInput = false;
                                            
                                        }
                                        //Next scnr clears the input to prevent a bug showing the incorrect user menu
                                                    ScannerClass.scan.nextLine();
                                    }
                                    densityAnalysis.generateDensityReport(csvFile, longitude1, longitude2);

                                    displayUserMenu("B");
                                break;

                        case "C":

                                    System.out.println(ANSI_RED + "\n(Space Agent Representative logged out - Returned to the Main Menu)" + ANSI_RESET);
                                    SystemLogger.getInstance("Space Agent Representative logged out - Returned to the Main Menu");
                                    mainMenu.login();
                                break;

                        default:

                                    System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                                    displayUserMenu("B");
                                optionIsValid = false;
                    }
                }while (!optionIsValid);
            }

        else if (userLoggedIn.equals("C")) { // ADMIN

                System.out.println(ANSI_CYAN + "\nAdministrator: Select an Option by typing the corresponding letter" + ANSI_RESET);
                System.out.println("A: Create User");
                System.out.println("B: Manage User");
                System.out.println("C: Delete User");
                System.out.println("D: Back");

                do {
                    optionIsValid = true;
                    String userChoice = (ScannerClass.scan.nextLine()).toUpperCase();
                
                    switch (userChoice) {

                        case "A":

                            menuType = "User Creation";
                                SystemLogger.getInstance("Administrator began user creation");
                                    extendedUserMenu.extendAdminMenu(menuType);
                            displayUserMenu("C");
                            break;

                        case "B":
                            menuType = "User Management";
                                System.out.println(ANSI_CYAN + "Manage User" + ANSI_RESET);
                                SystemLogger.getInstance("Administrator began user management");
                                    extendedUserMenu.extendAdminMenu(menuType);
                            displayUserMenu("C");
                            break;

                        case "C":

                            menuType = "User Deletion";
                                SystemLogger.getInstance("Administrator began user deletion");
                                    extendedUserMenu.extendAdminMenu(menuType);
                            displayUserMenu("C");
                            break;

                        case "D":

                                System.out.println(ANSI_RED + "\n(Administrator logged out - Returned to the Main Menu)" + ANSI_RESET);
                                SystemLogger.getInstance("Administrator logged out - Returned to the Main Menu");
                            mainMenu.login();
                            break;

                        default:

                                System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                            displayUserMenu("C");
                            optionIsValid = false;
                    }
                }while (!optionIsValid);
            }
    }
}
