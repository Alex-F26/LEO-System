import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code MainMenu} class handles user interaction at the main menu level.
 * <p>
 * It allows different types of users (Scientist, Space Agency Representative, Administrator)
 * to log into the system and access their respective interfaces.
 * The class also handles user authentication and logs each login activity using {@code SystemLogger}.
 * If the user exits, it generates an updated debris tracking report.
 * </p>
 * 
 * @author Alejandro Flores
 * @version 2.3
 */
public class MainMenu implements Aesthetic{

    /** Flag used to control the main menu loop. */
    public Boolean stopLoop = true;

    /** The name of the CSV file containing user account information. */
    private String outputFile = "User-List.csv";

    /** Username input provided by the user. */
    private String usernameInput = " ";

    /** Password input provided by the user. */
    private String passwordInput = " ";

    /** Stores the user type (A = Scientist, B = Space Rep, C = Admin). */
    private String user = " ";

    /** Instance of {@code ReadFile} used to read CSV files. */
    public ReadFile readFile = new ReadFile();

    /** Instance of {@code UserMenu} used to display role-specific menus. */
    public UserMenu userMenu = new UserMenu();

    /** Instance of {@code GenerateCSVFiles} used for generating tracking reports. */
    public GenerateCSVFiles generateCSVFiles = new GenerateCSVFiles();


    /**
     * Default constructor for {@code MainMenuLogin}.
     * Initializes the menu system and file handler.
     */
    public MainMenu() {
        // No additional setup required
    }

    /**
     * Displays the main login menu and handles login logic for each user role.
     * <p>
     * This method checks if the user file exists and prompts a default Administrator login
     * if it's the first time running the program. Otherwise, it allows all users to log in,
     * validates their credentials using {@code SignInVerification}, and redirects them
     * to their respective menu. On "EXIT" input, the system logs the event and generates a
     * final object tracking report.
     * </p>
     *
     * @return A string representing the user type that successfully logged in,
     *         or an empty string if the system exited or login failed.
     */
    public void login() {

        Boolean fileCreated = false;
        

            try(BufferedWriter writeFile = new BufferedWriter(new FileWriter(outputFile, true));
                BufferedReader read = new BufferedReader(new FileReader(outputFile))){

            if(read.readLine() == null){

                while(!fileCreated){
                    System.out.println(ANSI_CYAN + "\nNo users created, sign in as Administrator to create new users" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Default username is ADMIN" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Default password is ADMIN" + ANSI_RESET);

                    System.out.println(ANSI_CYAN + "\nEnter your username" + ANSI_RESET);
                        usernameInput = ScannerClass.scan.nextLine();
                    System.out.println(ANSI_CYAN + "Enter your password" + ANSI_RESET);
                        passwordInput = ScannerClass.scan.nextLine();

                    if (usernameInput.equals("ADMIN") && passwordInput.equals("ADMIN")) {
                            System.out.println("\nAdministrator logged in");
                            System.out.println("\nHello Root User");
                            SystemLogger.getInstance("Administrator logged in");

                                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))){
                                        writer.write("Role, ID, Name, Username, Password");
                                        writer.newLine();
                                        writer.write("Administrator,0000,Root,ADMIN,ADMIN");
                                        writer.newLine();
                                    }catch(IOException e) {
                                            e.printStackTrace();
                                    }
                            fileCreated = true;
                            userMenu.displayUserMenu("C");
                    } else {
                                System.out.println(ANSI_YELLOW + "\n!!! Incorrect username/password !!!" + ANSI_RESET);
                            stopLoop = false;
                    }
                }
            }

            else{


            do {

            stopLoop = true;
                System.out.println(ANSI_BOLD + ANSI_ITALICS + ANSI_UNDERLINE + ANSI_BRIGHT_YELLOW + "\nMain Menu" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "\nWhich user are you? \n(Type the letter corresponding with the user)" + "\n" + ANSI_RESET);
                System.out.println("A: Scientist \nB: Space Agency Representative \nC: Administrator \n(Type 'EXIT' to exit the program)");
            String userToLogin = (ScannerClass.scan.nextLine()).toUpperCase();
            boolean verified = false;

                switch (userToLogin) {

                        case "A":

                                    user = "A";
                                        System.out.println("\nEnter your username");
                                    usernameInput = ScannerClass.scan.nextLine();
                                        System.out.println("Enter your password");
                                    passwordInput = ScannerClass.scan.nextLine();

                                verified = SignInVerification.signInVerification(user, usernameInput, passwordInput, outputFile);
                                if(verified){
                                            userMenu.displayUserMenu(user);

                                }
                                else {
                                        System.out.println(ANSI_YELLOW + "\n!!! Incorrect username/password !!!" + ANSI_RESET);
                                            stopLoop = false;
                                }

                                break;

                        case "B":
                                
                                user = "B";
                                    System.out.println("\nEnter your username");
                                usernameInput = ScannerClass.scan.nextLine();
                                    System.out.println("Enter your password");
                                passwordInput = ScannerClass.scan.nextLine();

                                verified = SignInVerification.signInVerification(user, usernameInput, passwordInput, outputFile);
                                if(verified){
                                            userMenu.displayUserMenu(user);
                                }
                                else {
                                        System.out.println(ANSI_YELLOW + "\n!!! Incorrect username/password !!!" + ANSI_RESET);
                                            stopLoop = false;
                                }

                                break;

                        case "C":

                                user = "C";
                                    System.out.println("\nEnter your username");
                                usernameInput = ScannerClass.scan.nextLine();
                                    System.out.println("Enter your password");
                                passwordInput = ScannerClass.scan.nextLine();

                                verified = SignInVerification.signInVerification(user, usernameInput, passwordInput, outputFile);
                                if(verified){
                                            userMenu.displayUserMenu(user);
                                }
                                else {
                                        System.out.println(ANSI_YELLOW + "\n!!! Incorrect username/password !!!" + ANSI_RESET);
                                            stopLoop = false;
                                }

                                break;

                        case "EXIT":

                                    GenerateCSVFiles.generateCSV("contour-export-CS3331.csv", "Updated Debris Tracking Report.csv", "Tracking");
                                    SystemLogger.getInstance("System Exited: System Generated Object Tracking Report");
                                    System.out.println("\nProgram Terminated\n");
                                    System.exit(0);

                                break;

                        default:

                                    System.out.println(ANSI_YELLOW + "!!! INVALID INPUT !!!" + ANSI_RESET);
                                    stopLoop = false;

                }} while (!stopLoop);
            }
        
            }catch(IOException e) {
            e.printStackTrace();
        }

        
    }

  
}
