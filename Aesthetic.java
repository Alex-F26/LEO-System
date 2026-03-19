/**
 * Provides ANSI escape code constants for coloring and formatting terminal output.
 *
 * <p>This interface defines string constants for various text colors, background colors,
 * and text styles such as bold, italics, and underline using ANSI escape codes.
 * Classes implementing this interface can use these constants to format console output
 * for improved readability and user experience.
 *
 * <p>Note: ANSI escape codes may not be supported on all terminals or consoles.
 */
public interface Aesthetic{
    // ANSI escape codes for terminal colors
    final static String ANSI_RESET = "\u001B[0m";
    final static String ANSI_BLACK = "\u001B[30m";
    final static String ANSI_RED = "\u001B[31m";
    final static String ANSI_GREEN = "\u001B[32m";
    final static String ANSI_YELLOW = "\u001B[33m";
    final static String ANSI_BLUE = "\u001B[34m";
    final static String ANSI_PURPLE = "\u001B[35m";
    final static String ANSI_CYAN = "\u001B[36m";
    final static String ANSI_WHITE = "\u001B[97m";

    final static String ANSI_BRIGHT_BLACK = "\u001B[90m";
    final static String ANSI_BRIGHT_RED = "\u001B[91m";
    final static String ANSI_BRIGHT_GREEN = "\u001B[92m";
    final static String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    final static String ANSI_BRIGHT_BLUE = "\u001B[94m";
    final static String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    final static String ANSI_BRIGHT_CYAN = "\u001B[96m";

    final static String ANSI_DARK_WHITE = "\u001B[97m";

    // ANSI escape codes for terminal color backgrounds
    final static String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    final static String ANSI_RED_BACKGROUND = "\u001B[41m";
    final static String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    final static String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    final static String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    final static String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    final static String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    final static String ANSI_WHITE_BACKGROUND = "\u001B[107m";

    // ANSI escape codes for terminal fromat
    final static String ANSI_BOLD = "\u001B[1m";
    final static String ANSI_ITALICS = "\u001B[3m";
    final static String ANSI_UNDERLINE = "\u001B[4m";
    final static String ANSI_NOUNDERLINE = "\u001B[24m";

}


