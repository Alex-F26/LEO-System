import java.lang.Math;

/**
 * The {@code Assessment} class serves as an abstract superclass for evaluating
 * orbit and collision risks of space objects.
 * <p>
 * It provides general methods to compute:
 * <ul>
 *   <li>Likelihood of an object remaining in orbit</li>
 *   <li>Recommended actions based on orbit likelihood</li>
 *   <li>Collision risk level</li>
 *   <li>Response actions based on collision risk</li>
 * </ul>
 * This class is designed to be extended by specific assessment implementations.
 * 
 * @author Alejandro Flores
 * @version 2.3
 */
public class Assessment {

    /** Computed orbit likelihood score. */
    private double likelihoodScore;

    /** Textual result of orbit assessment: "Yes", "Maybe", or "No". */
    private String inOrbit;

    /** Recommended action based on orbit score. */
    private String recomAction;

    /** Computed collision risk score. */
    private double riskScore;

    /** Textual classification of collision risk or action. */
    private String riskLevel;

    /** Default constructor for {@code Assessment}. */
    public Assessment() {}

    /**
     * Calculates the orbit likelihood score for the given {@code SpaceObject}.
     * <p>
     * The formula used:
     * <pre>
     * OrbitScore = exp(-daysOld / 3650) *
     *              (1 - |longitude - avgLongitude| / 180) *
     *              (1 + log(conjunctionCount + 1))
     * </pre>
     * Based on the score, returns one of the following:
     * <ul>
     *   <li>"Yes" if score > 0.8</li>
     *   <li>"Maybe" if 0.4 < score ≤ 0.8</li>
     *   <li>"No" if score ≤ 0.4</li>
     * </ul>
     *
     * @param object A {@code SpaceObject} with fields: {@code daysOld}, {@code longitude}, {@code avgLongitude}, {@code conjuctionCount}.
     * @return A string representing orbit likelihood: "Yes", "Maybe", or "No".
     */
    public String calcOrbLikelyScore(SpaceObject object) {
        this.likelihoodScore = Math.pow(Math.E, -(object.daysOld / 3650.0))
                * (1 - Math.abs(object.longitude - object.avgLongitude) / 180.0)
                * (1 + Math.log(object.conjuctionCount + 1));

        if (this.likelihoodScore > 0.8) {
            this.inOrbit = "Yes";
        } else if (this.likelihoodScore > 0.4) {
            this.inOrbit = "Maybe";
        } else {
            this.inOrbit = "No";
        }
        return this.inOrbit;
    }

    /**
     * Suggests an action based on the orbit likelihood score:
     * <ul>
     *   <li>"Continue active monitoring" if score > 0.8</li>
     *   <li>"Recheck or Update data" if 0.4 < score ≤ 0.8</li>
     *   <li>"Deorbited or Lost Track" if score ≤ 0.4</li>
     * </ul>
     *
     * @param object A {@code SpaceObject} with required fields.
     * @return A string indicating the recommended orbit-related action.
     */
    public String displayOrbitAction(SpaceObject object) {
        this.likelihoodScore = Math.pow(Math.E, -(object.daysOld / 3650.0))
                * (1 - Math.abs(object.longitude - object.avgLongitude) / 180.0)
                * (1 + Math.log(object.conjuctionCount + 1));

        if (this.likelihoodScore > 0.8) {
            this.recomAction = "Continue active monitoring";
        } else if (this.likelihoodScore > 0.4) {
            this.recomAction = "Recheck or Update data";
        } else {
            this.recomAction = "Deorbited or Lost Track";
        }
        return this.recomAction;
    }

    /**
     * Calculates the collision risk score for the given {@code SpaceObject} using:
     * <pre>
     * RiskScore = |longitude - avgLongitude| / (daysOld + 1) * log(conjunctionCount + 1)
     * </pre>
     * The resulting score is classified as:
     * <ul>
     *   <li>"Very Low Risk" if score < 0.05</li>
     *   <li>"Low Risk" if 0.05 ≤ score < 0.2</li>
     *   <li>"Moderate Risk" if 0.2 ≤ score < 1.0</li>
     *   <li>"High Risk" if score ≥ 1.0</li>
     * </ul>
     *
     * @param object A {@code SpaceObject} with required fields.
     * @return A string indicating the collision risk level.
     */
    public String calcCollRiskScore(SpaceObject object) {
        this.riskScore = Math.abs(object.longitude - object.avgLongitude) / (object.daysOld + 1.0)
                * Math.log(object.conjuctionCount + 1);

        if (this.riskScore < 0.05) {
            this.riskLevel = "Very Low Risk";
        } else if (this.riskScore < 0.2) {
            this.riskLevel = "Low Risk";
        } else if (this.riskScore < 1.0) {
            this.riskLevel = "Moderate Risk";
        } else {
            this.riskLevel = "High Risk";
        }
        return this.riskLevel;
    }

    /**
     * Suggests a response based on the collision risk score:
     * <ul>
     *   <li>"Routine Monitoring" if score < 0.05</li>
     *   <li>"Periodic Checks" if 0.05 ≤ score < 0.2</li>
     *   <li>"Screening" if 0.2 ≤ score < 1.0</li>
     *   <li>"Detailed Analysis" if score ≥ 1.0</li>
     * </ul>
     *
     * @param object A {@code SpaceObject} with required fields.
     * @return A string indicating the recommended response action.
     */
    public String displayColAction(SpaceObject object) {
        this.riskScore = Math.abs(object.longitude - object.avgLongitude) / (object.daysOld + 1.0)
                * Math.log(object.conjuctionCount + 1);

        if (this.riskScore < 0.05) {
            this.riskLevel = "Routine Monitoring";
        } else if (this.riskScore < 0.2) {
            this.riskLevel = "Periodic Checks";
        } else if (this.riskScore < 1.0) {
            this.riskLevel = "Screening";
        } else {
            this.riskLevel = "Detailed Analysis";
        }
        return this.riskLevel;
    }
}
