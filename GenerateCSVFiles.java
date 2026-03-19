import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The {@code GenerateCSVFiles} class is responsible for generating output CSV files 
 * containing orbit status and collision risk assessments for space objects.
 * <p>
 * It reads input CSV data, parses and processes it using the {@code SpaceObject} and 
 * {@code Assessment} classes, then writes the augmented results to a specified output file.
 * <p>
 * Output types supported:
 * <ul>
 *   <li><b>Orbit</b> - Adds columns for orbit likelihood and recommended action</li>
 *   <li><b>Collision</b> - Adds columns for collision risk and recommended action</li>
 *   <li><b>Tracking</b> - Adds both orbit and collision analysis results</li>
 * </ul>
 * 
 * @author Alejandro
 * @version 2.3
 */
public class GenerateCSVFiles {

    public int rowCount = 0;
    public boolean isHeader = true;
    public String line;

    /**
     * Generates a new CSV file with additional data for orbit or collision risk assessments
     * based on the provided type.
     *
     * @param inputFilePath   Path to the input CSV file containing space object data.
     * @param outputFilePath  Path to the output CSV file to be created.
     * @param type            Type of data to generate: "Orbit", "Collision", or "Tracking".
     */
    public static void generateCSV(String inputFilePath, String outputFilePath, String type) {

        int geohash = 0;
        int i = 0;
        String line;
        String typeA = "Orbit";
        String typeB = "Collision";
        String typeC = "Tracking";
        String[] row = {};
        SpaceObject obj = new SpaceObject();
        Assessment assess = new Assessment();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            line = br.readLine();

            List<String> firstRow = FileParser.parseCSVLine(line);
            row = firstRow.toArray(new String[0]);

            for (String headerData : row) {
                if ("geohash".equals(headerData)) {
                    geohash = i + 1;
                }
                i++;
            }

            if (type.equals(typeA)) {
                writer.write(String.join(",", row) + ",still_in_orbit,Orb_status_action_recom");
                SystemLogger.getInstance("System generated Orbit Status Assessment CSV File");
            } else if (type.equals(typeB)) {
                writer.write(String.join(",", row) + ",coll_risk,coll_action_recom");
                SystemLogger.getInstance("System generated Collision Risk Assessment CSV File");
            } else if (type.equals(typeC)) {
                writer.write(String.join(",", row) + ",still_in_orbit,Orb_status_action_recom,coll_risk,coll_action_recom");
            }
            writer.newLine();

            while ((line = br.readLine()) != null) {
                List<String> parsedRow = FileParser.parseCleanCSV(line, geohash);
                row = parsedRow.toArray(new String[0]);

                obj.loadFromRow(row, inputFilePath);
                String resultOrbit = assess.calcOrbLikelyScore(obj);
                String resultOrbAction = assess.displayOrbitAction(obj);
                String resultCollision = assess.calcCollRiskScore(obj);
                String resultColAction = assess.displayColAction(obj);

                if (type.equals(typeA)) {
                    writer.write(String.join(",", row) + "," + resultOrbit + "," + resultOrbAction);
                } else if (type.equals(typeB)) {
                    writer.write(String.join(",", row) + "," + resultCollision + "," + resultColAction);
                } else if (type.equals(typeC)) {
                    writer.write(String.join(",", row) + "," + resultOrbit + "," + resultOrbAction + "," + resultCollision + "," + resultColAction);
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
