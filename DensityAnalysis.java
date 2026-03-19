import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The {@code DensityAnalysis} class provides functionality to analyze space object density data from a CSV file.
 * It filters the data based on longitude range and outputs detailed statistics, such as counts by orbit type and object type.
 * <p>
 * Output is formatted using ANSI color codes for readability in the terminal.
 * </p>
 * @author Jesus Munoz
 * @version 1.0
 */
public class DensityAnalysis extends SpaceObject implements Aesthetic{

    /**
     * Reads space object data from a CSV file and generates a density report for objects within a specified longitude range.
     * <p>
     * The report includes:
     * <ul>
     *     <li>Counts of objects by orbit type</li>
     *     <li>Counts of objects by object type</li>
     *     <li>Total objects within the specified longitude range</li>
     *     <li>Detailed list of objects in the range</li>
     * </ul>
     * </p>
     *
     * @param csvFilePath  The file path to the CSV containing the space object data.
     * @param minLongitude The minimum longitude value for the analysis range.
     * @param maxLongitude The maximum longitude value for the analysis range.
     */
    public void generateDensityReport(String csvFilePath, double minLongitude, double maxLongitude) {
        String line;

        List<String[]> matchedRows = new ArrayList<>();

        Map<String, Integer> orbitCounts = new HashMap<>();
        Map<String, Integer> objectTypeCounts = new HashMap<>();
        int totalCount = 0;
        int geohashInt = 0;
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {

            line = br.readLine();
            List<String> firstRow = FileParser.parseCSVLine(line);
                String[] newRow = firstRow.toArray(new String[0]);  

                for(String headerData : newRow){
                    switch(headerData){
                        case "geohash":
                                geohashInt = i + 1;
                            break;
                        default:}
                    i++;}


            while ((line = br.readLine()) != null) {
                List<String> nextRow = FileParser.parseCleanCSV(line, geohashInt);
                String[] row = nextRow.toArray(new String[0]); 

                        loadFromRow(row, csvFilePath);


                    if (this.longitude >= minLongitude && this.longitude <= maxLongitude) {
                        matchedRows.add(row);
                        totalCount++;

                        orbitCounts.put(this.orbitType, orbitCounts.getOrDefault(this.orbitType, 0) + 1);

                        objectTypeCounts.put(this.objectType, objectTypeCounts.getOrDefault(this.objectType, 0) + 1);
                    }
            }
        } catch (IOException e) {
            System.out.println(ANSI_YELLOW + "Error reading CSV: " + ANSI_RESET + e.getMessage());
            return;
        }

        // Print results
        System.out.println(ANSI_CYAN + "\nDensity Report" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Longitude Range: " + ANSI_RESET + minLongitude + " to " + maxLongitude);
        System.out.println(ANSI_GREEN + "\nCount by Orbit Type" + ANSI_RESET);
        for (String orbitType : orbitCounts.keySet()) {
            System.out.println(orbitType + ": " + orbitCounts.get(orbitType));
        }

        System.out.println(ANSI_GREEN + "\nCount by Object Type " + ANSI_RESET);
        for (String objectType : objectTypeCounts.keySet()) {
            System.out.println(objectType + ": " + objectTypeCounts.get(objectType));
        }

        System.out.println(ANSI_GREEN + "\nTotal Objects in Range " + ANSI_RESET);
        System.out.println("Total: " + totalCount);

        System.out.println(ANSI_GREEN + "\nObject Details " + ANSI_RESET);
        for (String[] row : matchedRows) {
            System.out.println("ID: " + row[this.recordIDIndex] + ", Satellite Name: " + row[this.satelliteNameIndex] + ", Country: " + row[this.countryIndex] +
                ", Orbit Type: " + row[this.orbitTypeIndex] + ", Launch year: " + row[this.launchYearIndex] + ", Longitude: " + row[this.longitudeIndex] +
                ", Days Old: " + row[this.daysOldIndex] + ", Object Type: " + row[this.objectTypeIndex]);
        }

        SystemLogger.getInstance("System generated Density Reports");
    }
}
