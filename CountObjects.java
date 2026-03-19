import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The {@code CountObjects} class provides static methods to analyze
 * the impact of space object launches from CSV data.
 * <p>
 * It supports both overall impact and country-specific impact analysis,
 * categorizing objects by type and orbit.
 * </p>
 */
public class CountObjects implements Aesthetic {

    /**
     * Analyzes and displays overall statistics about space object launches
     * from the given CSV file. The output includes total counts by object type,
     * orbit distribution, and leading countries for each category.
     *
     * @param file The path to the input CSV file containing launch data.
     */
    public static void overallImpact(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //This line reads the header
            String line = br.readLine(); 
            if (line == null) return;

            //The following retrieves the indices for the titles we need in the header row
            List<String> headers = FileParser.parseCSVLine(line);
            int countryIdx = getIndex(headers, "COUNTRY");
            int objectTypeIdx = getIndex(headers, "OBJECT_TYPE");
            int orbitTypeIdx = getIndex(headers, "ORBIT");

            //The following initialize the various counters needed
            int totalCount = 0;
            Map<String, Integer> typeCount = new HashMap<>();
            Map<String, Map<String, Integer>> typeOrbitCount = new HashMap<>();
            Map<String, Map<String, Integer>> typeCountryCount = new HashMap<>();
            Map<String, Integer> orbitCountryCount = new HashMap<>();

            //Typical while loop to read through the rest of the csv file
            while ((line = br.readLine()) != null) {
                List<String> row = FileParser.parseCSVLine(line);

                //If the number of items in the row List is less than or equal to the max of (countryIdx and (the max of objectIdx and orbitIdx))
                if (row.size() <= Math.max(countryIdx, Math.max(objectTypeIdx, orbitTypeIdx))) continue;

                //The following initializes Strings according to the values at each index
                String objectType = row.get(objectTypeIdx).trim().toUpperCase();
                String orbitType = row.get(orbitTypeIdx).trim().toUpperCase();
                String country = row.get(countryIdx).trim().toUpperCase();

                typeCount.put(objectType, typeCount.getOrDefault(objectType, 0) + 1);
                typeOrbitCount.putIfAbsent(objectType, new HashMap<>());
                typeOrbitCount.get(objectType).put(orbitType, typeOrbitCount.get(objectType).getOrDefault(orbitType, 0) + 1);

                //
                typeCountryCount.putIfAbsent(objectType, new HashMap<>());
                typeCountryCount.get(objectType).put(country, typeCountryCount.get(objectType).getOrDefault(country, 0) + 1);

                orbitCountryCount.put(country + ":" + orbitType, orbitCountryCount.getOrDefault(country + ":" + orbitType, 0) + 1);

                totalCount++;
            }

            System.out.println(ANSI_BOLD + ANSI_UNDERLINE + ANSI_CYAN + "\nIf an orbit type is not mentioned below, there are 0 objects of said type in that orbit: " + ANSI_RESET);
                        System.out.println();
            System.out.println(ANSI_BOLD + ANSI_UNDERLINE + ANSI_CYAN + "Total number of objects launched: " + totalCount + ANSI_RESET);
                        System.out.println();
            
            for (String type : typeCount.keySet()) {
                System.out.println(ANSI_CYAN + "Total " + type + " objects: " + typeCount.get(type) + ANSI_RESET);
                for (String orbit : typeOrbitCount.get(type).keySet()) {
                    System.out.println("   in " + orbit + ": " + typeOrbitCount.get(type).get(orbit));
                }

                Map<String, Integer> countryMap = typeCountryCount.get(type);

                //The following gets the values from the countryMap and creates a stream of those value and then compares them using the compareTo method, if there is no max, max is set to 0
                int max = countryMap.values().stream().max(Integer::compareTo).orElse(0);

                //
                List<String> maxCountries = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : countryMap.entrySet()) {
                    if (entry.getValue() == max) maxCountries.add(entry.getKey());
                }
                System.out.println(ANSI_PURPLE + "   Country with most " + type + ": " + ANSI_BRIGHT_PURPLE + String.join(", ", maxCountries) + " (" + max + ")" + ANSI_RESET);
                        System.out.println();
            }

            //Set<> creates a collection of objects that cant have duplicates
            Set<String> orbits = new HashSet<>();
            for (String key : orbitCountryCount.keySet()) {
                String[] parts = key.split(":");
                orbits.add(parts[1]);
            }

            System.out.println();
            for (String orbit : orbits) {
                int max = 0;
                List<String> maxCountries = new ArrayList<>();
                for (String key : orbitCountryCount.keySet()) {
                    if (key.endsWith(":" + orbit)) {
                        int val = orbitCountryCount.get(key);
                        if (val > max) {
                            max = val;
                            maxCountries.clear();
                            maxCountries.add(key.split(":")[0]);
                        } else if (val == max) {
                            maxCountries.add(key.split(":")[0]);
                        }
                    }
                }

                System.out.println("Country with most in " + ANSI_BRIGHT_PURPLE + ANSI_BOLD + ANSI_UNDERLINE + orbit + ANSI_RESET + " orbit: " + String.join(", ", maxCountries) + " (" + max + ")");
            }

        } catch (IOException e) {
            System.out.println(ANSI_YELLOW + "Error reading CSV file" + ANSI_RESET + e.getMessage());
        }
        SystemLogger.getInstance("Overall impact analysis report generated");
    }

    /**
     * Prompts the user to enter a country and then analyzes the CSV data
     * to show statistics for launches specific to that country. This includes
     * total counts and orbit distribution per object type.
     *
     * @param file The path to the input CSV file containing launch data.
     */
    public static void countrySpecificImpact(String file) {

        System.out.print("Enter country: ");
        String inputCountry = ScannerClass.scan.nextLine().trim().toUpperCase();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) return;

            List<String> headers = FileParser.parseCSVLine(line);
            int countryIdx = getIndex(headers, "COUNTRY");
            int objectTypeIdx = getIndex(headers, "OBJECT_TYPE");
            int orbitTypeIdx = getIndex(headers, "ORBIT");

            int total = 0;
            Map<String, Integer> typeCount = new HashMap<>();
            Map<String, Map<String, Integer>> orbitCountPerType = new HashMap<>();

            while ((line = br.readLine()) != null) {
                List<String> row = FileParser.parseCSVLine(line);
                if (row.size() <= Math.max(countryIdx, Math.max(objectTypeIdx, orbitTypeIdx))) continue;

                String objectType = row.get(objectTypeIdx).trim().toUpperCase();
                String orbitType = row.get(orbitTypeIdx).trim().toUpperCase();
                String country = row.get(countryIdx).trim().toUpperCase();

                if (!country.equals(inputCountry)) continue;

                total++;
                typeCount.put(objectType, typeCount.getOrDefault(objectType, 0) + 1);
                orbitCountPerType.putIfAbsent(objectType, new HashMap<>());
                orbitCountPerType.get(objectType).put(orbitType, orbitCountPerType.get(objectType).getOrDefault(orbitType, 0) + 1);
            }

            if (total == 0) {
                System.out.println(ANSI_YELLOW + "No information on space objects for " + inputCountry + ANSI_RESET);
                return;
            }

            System.out.println(ANSI_BOLD + ANSI_UNDERLINE + ANSI_CYAN + ANSI_ITALICS + "Country: " + inputCountry + ANSI_RESET);
            System.out.println(ANSI_CYAN + "\nTotal objects launched: " + total + "\n" + ANSI_RESET);
            for (String type : typeCount.keySet()) {
                System.out.println(ANSI_CYAN + "Total " + type + " objects: " + typeCount.get(type) + ANSI_RESET);
                Map<String, Integer> orbitMap = orbitCountPerType.get(type);
                for (String orbit : orbitMap.keySet()) {
                    System.out.println("   in " + orbit + ": " + orbitMap.get(orbit));
                }
            }

        } catch (IOException e) {
            System.out.println(ANSI_YELLOW + "Error reading CSV file" + ANSI_RESET + e.getMessage());
        }
        SystemLogger.getInstance("Country-specific impact analysis report generated");
    }

    /**
     * Returns the index of the first column header that contains the given keyword,
     * ignoring case and leading/trailing whitespace.
     *
     * @param headers The list of header strings from the CSV.
     * @param keyword The keyword to search for (e.g., "COUNTRY", "OBJECT_TYPE").
     * @return The index of the matching header, or -1 if not found.
     */
    private static int getIndex(List<String> headers, String keyword) {
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).trim().toUpperCase().contains(keyword)) {
                return i;
            }
        }
        return -1;
    }
}
