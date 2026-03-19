/**
 * The {@code ImpactAnalysis} class analyzes the impact of space object collisions
 * based on different modes specified by the user.
 * <p>
 * It provides options for overall or country-specific analysis.
 * </p>
 */
public class ImpactAnalysis implements Aesthetic {

    /** Constructs a new ImpactAnalysis object. */
    public ImpactAnalysis() {
    }

    /**
     * Analyzes impact based on the specified option.
     *
     * @param file   The path to the input file containing collision data.
     * @param option The analysis option: "A" for overall impact,
     *               "B" for country-specific impact.
     */
    public void analyzeImpact(String file, String option) {

        if (option.equals("A")) {
            CountObjects.overallImpact(file);
        } else if (option.equals("B")) {
            CountObjects.countrySpecificImpact(file);
        }

    }
}
