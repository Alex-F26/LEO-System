import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing CSV lines, including handling quoted fields and embedded commas.
 * 
 * <p>Provides two parsing methods:
 * <ul>
 *   <li>{@link #parseCSVLine(String)}: Parses a standard CSV line, handling quotes and commas.</li>
 *   <li>{@link #parseCleanCSV(String, int)}: Parses a CSV line but replaces the comma at a specified index with a semicolon and space (useful for fields like geohashes that may contain commas).</li>
 * </ul>
 */
public class FileParser{

/**
 * Parses a single line from a CSV file, correctly handling quoted values and embedded commas.
 *
 * @param line The raw CSV line.
 * @return A list of parsed column values as strings.
 */
public static List<String> parseCSVLine(String line) {
        
    List<String> result = new ArrayList<>();
    StringBuilder current = new StringBuilder();
    boolean inQuotes = false;
    
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    current.append('\"');
                    i++; 
                } else {
                    inQuotes = !inQuotes;
                }
        } else if (c == ',' && !inQuotes) {
            result.add(current.toString());
            current.setLength(0);
        } else {
            current.append(c);
        }
        }

    result.add(current.toString());
    return result;
}

/**
 * Parses a CSV line similarly to {@link #parseCSVLine(String)}, but replaces the comma
 * at the specified index (geohashIndex - 1) with a semicolon and a space.
 * 
 * <p>This is useful if a field at a certain position contains embedded commas that should be
 * replaced to avoid splitting incorrectly.
 *
 * @param line The raw CSV line.
 * @param geohashIndex The 1-based index of the column whose embedded commas should be replaced.
 * @return A list of parsed column values with commas replaced by "; " at the specified index.
 */
public static List<String> parseCleanCSV(String line, int geohashIndex) {
        
    List<String> result = new ArrayList<>();
    StringBuilder current = new StringBuilder();
    boolean inQuotes = false;
    int commaCount = 0;
    
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
                if (c == '\"') {
                    if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                        current.append('\"');
                        i++; 
                    } else {
                        inQuotes = !inQuotes;
                    }
                } else if(c == ',' && commaCount == geohashIndex-1){
                    current.append(';');
                    current.append(' ');

                    commaCount++;
                }
                
                else if (c == ',' && !inQuotes && commaCount != geohashIndex-1) {
                    result.add(current.toString());
                    current.setLength(0);
                    commaCount++;
                } else {
                    current.append(c);
                }
    }

    result.add(current.toString());
    return result;
}
}