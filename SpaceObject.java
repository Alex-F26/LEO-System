import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * The {@code SpaceObject} class represents a general object in space,
 * such as debris, satellites, rocket bodies, or unknown objects.
 * It encapsulates key properties related to identification, origin,
 * orbital characteristics, and age for use in a space object tracking system.
 * <p>
 * This class also provides functionality to initialize its fields dynamically
 * from a CSV data row by mapping column headers to object attributes.
 * </p>
 * 
 * <p><strong>CSV Column Mapping Assumption:</strong></p>
 * <ul>
 *   <li>recordID → "norad_cat_id"</li>
 *   <li>satelliteName → "satellite_name"</li>
 *   <li>country → "country"</li>
 *   <li>orbitType → "approximate_orbit_type"</li>
 *   <li>objectType → "object_type"</li>
 *   <li>launchYear → "launch_year"</li>
 *   <li>launchSite → "launch_site"</li>
 *   <li>longitude → "longitude"</li>
 *   <li>avgLongitude → "avg_longitude"</li>
 *   <li>geohash → "geohash"</li>
 *   <li>daysOld → "days_old"</li>
 *   <li>conjunctionCount → "conjunction_count"</li>
 * </ul>
 * 
 * @author Alejandro Flores
 * @version 2.3
 */
public class SpaceObject {

    /** Unique identifier for the object record. */
    protected String recordID;

    /** Name of the satellite or space object. */
    protected String satelliteName;

    /** Country that owns or launched the object. */
    protected String country;

    /** Orbit type (e.g., LEO, MEO, GEO, HEO). */
    protected String orbitType;

    /** Type of object (e.g., ROCKET BODY, DEBRIS, PAYLOAD, UNKNOWN). */
    protected String objectType;

    /** Year the object was launched. */
    protected int launchYear;

    /** Launch site from which the object was deployed. */
    protected String launchSite;

    /** Current longitude of the object. */
    protected double longitude;

    /** Average historical longitude of the object. */
    protected double avgLongitude;

    /** Geohash representing spatial location encoding. */
    protected String geohash;

    /** Age of the object in days since launch. */
    protected int daysOld;

    /** Number of recorded conjunctions (close approaches with other objects). */
    protected Long conjuctionCount;

    // Index positions of the CSV fields
    protected int recordIDIndex;
    protected int satelliteNameIndex;
    protected int countryIndex;
    protected int orbitTypeIndex;
    protected int objectTypeIndex;
    protected int launchYearIndex;
    protected int launchSiteIndex;
    protected int longitudeIndex;
    protected int avgLongitudeIndex;
    protected int geohashIndex;
    protected int daysOldIndex;
    protected int conjuctionCountIndex;

    /** Utility class used for file reading operations. */
    public ReadFile readFile = new ReadFile();

    /**
     * Default constructor for {@code SpaceObject}.
     * Initializes a blank instance without assigning values to its fields.
     */
    public SpaceObject() {
    }

    /**
     * Loads the space object's attributes from a given row of CSV data and the header row of a file.
     * Dynamically assigns the values to corresponding fields by matching column headers.
     *
     * <p>Expected row values (in any order) must include:</p>
     * <pre>
     * "norad_cat_id", "satellite_name", "country", "approximate_orbit_type", "object_type",
     * "launch_year", "launch_site", "longitude", "avg_longitude", "geohash", "days_old", "conjunction_count"
     * </pre>
     *
     * @param row An array of string values representing one CSV line of space object data.
     * @param inputFilePath The path to the CSV file, from which the header row is read to determine column mappings.
     * @throws NumberFormatException if a numeric value (e.g., year, longitude, count) is improperly formatted.
     * @throws ArrayIndexOutOfBoundsException if the row does not contain enough values.
     * @throws IOException if an error occurs while reading the header row from the file.
     */
    public void loadFromRow(String[] row, String inputFilePath) {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line = br.readLine(); // read header line
            List<String> firstRow = FileParser.parseCSVLine(line);
            String[] rowHeader = firstRow.toArray(new String[0]);

            for (String headerData : rowHeader) {
                switch (headerData) {
                    case "norad_cat_id":
                        this.recordID = row[i];
                        this.recordIDIndex = i;
                        break;
                    case "satellite_name":
                        this.satelliteName = row[i];
                        this.satelliteNameIndex = i;
                        break;
                    case "country":
                        this.country = row[i];
                        this.countryIndex = i;
                        break;
                    case "approximate_orbit_type":
                        this.orbitType = row[i];
                        this.orbitTypeIndex = i;
                        break;
                    case "object_type":
                        this.objectType = row[i];
                        this.objectTypeIndex = i;
                        break;
                    case "launch_year":
                        this.launchYear = Integer.parseInt(row[i]);
                        this.launchYearIndex = i;
                        break;
                    case "launch_site":
                        this.launchSite = row[i];
                        this.launchSiteIndex = i;
                        break;
                    case "longitude":
                        this.longitude = Double.parseDouble(row[i]);
                        this.longitudeIndex = i;
                        break;
                    case "avg_longitude":
                        this.avgLongitude = Double.parseDouble(row[i]);
                        this.avgLongitudeIndex = i;
                        break;
                    case "geohash":
                        this.geohash = row[i];
                        this.geohashIndex = i;
                        break;
                    case "days_old":
                        this.daysOld = Integer.parseInt(row[i]);
                        this.daysOldIndex = i;
                        break;
                    case "conjunction_count":
                        this.conjuctionCount = Long.parseLong(row[i]);
                        this.conjuctionCountIndex = i;
                        break;
                    default:
                        // Unknown column header - skip
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
