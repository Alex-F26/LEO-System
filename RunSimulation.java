/**
 * Entry point of the space object tracking simulation.
 * <p>
 * The {@code RunSimulation} class is responsible for launching the application.
 * It creates an instance of the {@link MainMenu} class and starts the login process,
 * enabling role-based access and navigation through the simulation's features.
 */
public class RunSimulation {

    /**
     * Main method that runs the simulation program.
     * <p>
     * This method initializes the {@link MainMenu} and calls its {@code login()} method
     * to present a role-based login menu to the user.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.login();
    }
}
