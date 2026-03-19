/**
 * Represents a user in the system with basic attributes.
 * 
 * <p>Each user has:
 * <ul>
 *   <li>A user type (e.g., Scientist, Administrator, etc.)</li>
 *   <li>A unique ID</li>
 *   <li>A name</li>
 *   <li>A username</li>
 *   <li>A password</li>
 * </ul>
 */
public class User{
    /** The role/type of the user (e.g., Scientist, Administrator). */
    public String userType;

    /** The unique ID assigned to the user. */
    public int ID;

    /** The name of the user. */
    public String name;

    /** The username used for login. */
    public String username;

    /** The password associated with the account. */
    public String password;

    /**
     * Constructs a new {@code User} object with the provided information.
     *
     * @param userType the type of user (e.g., "Scientist")
     * @param ID the unique identifier for the user
     * @param name the name of the user
     * @param username the username used to log in
     * @param password the user's password
     */
    public User(String userType, int ID, String name, String username, String password){
        this.userType = userType;
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
    }


}