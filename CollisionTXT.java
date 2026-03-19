import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The {@code CollisionTXT} class reads collision risk data from a CSV file
 * and generates a TXT report summarizing object types and their associated risk levels.
 * <p>
 * It categorizes space objects (Rocket Bodies, Debris, Payloads, and Unknown)
 * and counts their occurrences by risk severity (Very Low, Low, Moderate, High).
 * </p>
 */
public class CollisionTXT implements GenerateTXT, Aesthetic{

    /** Stores the current CSV row as an array. */
    private String[] row = {};

    /** Tracks the number of rows processed. */
    public int rowCount = 0;

    /** Indicates whether the current row is the header row. */
    public boolean isHeader = true;

    /** Stores the line currently being read from the CSV. */
    public String line;

    /**
     * Reads collision risk data from a CSV file and writes a TXT file summarizing
     * the number of each object type (e.g., Rocket Body, Debris, etc.) by risk level.
     *
     * @param file     Input CSV with collision risk data.
     * @param filePath Output TXT file summarizing object risks.
     */
public void generateTXT(String file, String filePath){

        int rocketVeryLowCount = 0;
        int rocketLowCount = 0;
        int rocketModerateCount = 0;
        int rocketHighCount = 0;

        int debrisVeryLowCount = 0;
        int debrisLowCount = 0;
        int debrisModerateCount = 0;
        int debrisHighCount = 0;

        int payloadVeryLowCount = 0;
        int payloadLowCount = 0;
        int payloadModerateCount = 0;
        int payloadHighCount = 0;

        int unknownVeryLowCount = 0;
        int unknownLowCount = 0;
        int unknownModerateCount = 0;
        int unknownHighCount = 0;

        int rocketCount = 0;
        int debrisCount = 0;
        int payloadCount = 0;
        int unknownCount = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(file));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {


                        while ((line = br.readLine()) != null) {
                            List<String> parsedRow = FileParser.parseCSVLine(line);
                            row = parsedRow.toArray(new String[0]);

                            if (isHeader) {
                                isHeader = false;
                                continue; 
                            }

                            for(String rowData : row){
                            switch (rowData) {

                                case "ROCKET BODY":

                                    rocketCount++;
                                    switch (rowData) {
                                            case "Very Low Risk": rocketVeryLowCount++; break;
                                            case "Low Risk": rocketLowCount++; break;
                                            case "Moderate Risk": rocketModerateCount++; break;
                                            case "High Risk": rocketHighCount++; break;
                                        }
                                    break;

                                case "DEBRIS":

                                    debrisCount++;
                                    switch (rowData) {
                                            case "Very Low Risk": debrisVeryLowCount++; break;
                                            case "Low": debrisLowCount++; break;
                                            case "Moderate": debrisModerateCount++; break;
                                            case "High Risk": debrisHighCount++; break;
                                        }
                                    break;

                                case "PAYLOAD":

                                    payloadCount++;
                                    switch (rowData) {
                                            case "Very Low Risk": payloadVeryLowCount++; break;
                                            case "Low Risk": payloadLowCount++; break;
                                            case "Moderate Risk": payloadModerateCount++; break;
                                            case "High Risk": payloadHighCount++; break;
                                        }
                                    break;

                                case "UNKNOWN":

                                    unknownCount++;
                                    switch (rowData) {
                                            case "Very Low Risk": unknownVeryLowCount++; break;
                                            case "Low Risk": unknownLowCount++; break;
                                            case "Moderate Risk": unknownModerateCount++; break;
                                            case "High Risk": unknownHighCount++; break;
                                        }
                                    break;
                            }
                        }
                    }

                        System.out.print(ANSI_CYAN + ANSI_UNDERLINE + "\nObject Types and Risk Assessments\n\n" + ANSI_RESET);

                        System.out.print("Rocket Body: " + rocketCount + "\n");
                        System.out.print("Very Low Risk: " + rocketVeryLowCount + "\n");
                        System.out.print("Low Risk: " + rocketLowCount + "\n");
                        System.out.print("Moderate Risk: " + rocketModerateCount + "\n");
                        System.out.print("High Risk: " + rocketHighCount + "\n\n");

                        System.out.print("Debris: " + debrisCount + "\n");
                        System.out.print("Very Low Risk: " + debrisVeryLowCount + "\n");
                        System.out.print("Low Risk: " + debrisLowCount + "\n");
                        System.out.print("Moderate Risk: " + debrisModerateCount + "\n");
                        System.out.print("High Risk: " + debrisHighCount + "\n\n");

                        System.out.print("Payload: " + payloadCount + "\n");
                        System.out.print("Very Low Risk: " + payloadVeryLowCount + "\n");
                        System.out.print("Low Risk: " + payloadLowCount + "\n");
                        System.out.print("Moderate Risk: " + payloadModerateCount + "\n");
                        System.out.print("High Risk: " + payloadHighCount + "\n\n");

                        System.out.print("Unknown: " + unknownCount + "\n");
                        System.out.print("Very Low Risk: " + unknownVeryLowCount + "\n");
                        System.out.print("Low Risk: " + unknownLowCount + "\n");
                        System.out.print("Moderate Risk: " + unknownModerateCount + "\n");
                        System.out.print("High Risk: " + unknownHighCount + "\n\n");
                        
                        writer.write("Object Types and Risk Assessments\n\n");

                        writer.write("Rocket Body: " + rocketCount + "\n");
                        writer.write("Very Low Risk: " + rocketVeryLowCount + "\n");
                        writer.write("Low Risk: " + rocketLowCount + "\n");
                        writer.write("Moderate Risk: " + rocketModerateCount + "\n");
                        writer.write("High Risk: " + rocketHighCount + "\n\n");

                        writer.write("Debris: " + debrisCount + "\n");
                        writer.write("Very Low Risk: " + debrisVeryLowCount + "\n");
                        writer.write("Low Risk: " + debrisLowCount + "\n");
                        writer.write("Moderate Risk: " + debrisModerateCount + "\n");
                        writer.write("High Risk: " + debrisHighCount + "\n\n");

                        writer.write("Payload: " + payloadCount + "\n");
                        writer.write("Very Low Risk: " + payloadVeryLowCount + "\n");
                        writer.write("Low Risk: " + payloadLowCount + "\n");
                        writer.write("Moderate Risk: " + payloadModerateCount + "\n");
                        writer.write("High Risk: " + payloadHighCount + "\n\n");

                        writer.write("Unknown: " + unknownCount + "\n");
                        writer.write("Very Low Risk: " + unknownVeryLowCount + "\n");
                        writer.write("Low Risk: " + unknownLowCount + "\n");
                        writer.write("Moderate Risk: " + unknownModerateCount + "\n");
                        writer.write("High Risk: " + unknownHighCount + "\n\n");

                        SystemLogger.getInstance("System generated Collision Risk Assessment TXT File");

            } catch (IOException e) {
                        e.printStackTrace();
        }
    }
}