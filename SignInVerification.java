import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Provides user sign-in verification by validating credentials against a CSV file.
 * 
 * <p>Supports verification of three user types:
 * <ul>
 *   <li>Scientist</li>
 *   <li>Space Agency Representative</li>
 *   <li>Administrator</li>
 * </ul>
 * 
 * <p>Prints login confirmation messages and logs login events via {@code SystemLogger}.
 * 
 * <p>Implements the {@code Aesthetic} interface to use terminal color codes.
 */
public class SignInVerification implements Aesthetic{

  /**
     * Verifies the login credentials for a given user type.
     * <p>
     * Reads from the user list CSV file and checks for a match with the specified
     * username and password under the correct role (Scientist, Space Agency Representative, or Administrator).
     * </p>
     *
     * @param userType The type of user attempting to log in ("A", "B", or "C").
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return {@code true} if the credentials are valid; {@code false} otherwise.
     */
    public static boolean signInVerification(String userType, String username, String password, String outputFile){
        boolean verified = false;
        String line = "";
        String[] newRow = {};
        boolean isHeader = true;

        try(BufferedReader br = new BufferedReader(new FileReader(outputFile))){

                while((line = br.readLine()) != null && !verified){

                    if(isHeader){
                        isHeader = false;
                        continue;
                    }

                    List<String> parsedRow = FileParser.parseCSVLine(line);
                    newRow = parsedRow.toArray(new String[0]);

                        switch(userType){

                            case "A":

                                    if(newRow[0].equals("Scientist") && newRow[3].equals(username) && newRow[4].equals(password)){
                                        verified = true;
                                            System.out.println(ANSI_RED + "\nScientist logged in" + ANSI_RESET); 
                                                SystemLogger.getInstance("Scientist " + newRow[2] + " logged in");
                                            System.out.println("Hello, " + newRow[2]);
                                        continue;
                                    }
                                    else{
                                        verified = false;
                                    }
                                break;

                            case "B":

                                    if(newRow[0].equals("Space Agency Representative") && newRow[3].equals(username) && newRow[4].equals(password)){
                                        verified = true;
                                            System.out.println(ANSI_RED + "\nSpace Agent Representative logged in" + ANSI_RESET); 
                                                SystemLogger.getInstance("Space Agent Representative " + newRow[2] + " logged in");
                                            System.out.println("Hello, " + newRow[2]);
                                        continue;
                                    }
                                    else{
                                        verified = false;
                                    }
                                break;

                            case "C":
    
                                    if(newRow[0].equals("Administrator") && newRow[3].equals(username) && newRow[4].equals(password)){
                                        verified = true;
                                            System.out.println(ANSI_RED + "\nAdministrator logged in" + ANSI_RESET); 
                                                SystemLogger.getInstance("Administrator " + newRow[2] + " logged in");
                                            System.out.println("Hello, " + newRow[2]);
                                        continue;
                                    }
                                    else{
                                        verified = false;
                                    }
                                break;

                            default:
                    }
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return verified;
    }
}