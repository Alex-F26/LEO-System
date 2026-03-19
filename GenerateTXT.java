/**
 * The {@code GenerateTXT} interface defines a contract for classes that
 * implement functionality to generate a TXT file based on input data.
 * <p>
 * This interface is typically used for exporting space object data
 * or reports to a plain text file format.
 * </p>
 * 
 * @author Alejandro Flores
 * @version 1.0
 */
public interface GenerateTXT {

    /**
     * Generates a TXT file from the specified input file.
     * 
     * @param inputFile  the path to the input file containing data to process.
     * @param outputFile the path where the output TXT file will be written.
     */
    void generateTXT(String inputFile, String outputFile);
}
