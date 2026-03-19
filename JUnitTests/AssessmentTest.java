
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssessmentTest {

    private Assessment assessment;
    private SpaceObject object;

    @BeforeEach
    public void setUp() {
        assessment = new Assessment();
        object = new SpaceObject();
    }

    @Test
    public void testCalcOrbLikelyScore_HighLikelihood() {
        object.daysOld = 100;
        object.longitude = 10;
        object.avgLongitude = 10;
        object.conjuctionCount = 50L;

        String result = assessment.calcOrbLikelyScore(object);
        assertEquals("Yes", result);
    }

    @Test
    public void testCalcOrbLikelyScore_MediumLikelihood() {
        object.daysOld = 3000;
        object.longitude = 30;
        object.avgLongitude = 60;
        object.conjuctionCount = 2L;

        String result = assessment.calcOrbLikelyScore(object);
        assertEquals("Yes", result);
    }

    @Test
    public void testCalcOrbLikelyScore_LowLikelihood() {
        object.daysOld = 10000;
        object.longitude = 150;
        object.avgLongitude = 0;
        object.conjuctionCount = 0L;

        String result = assessment.calcOrbLikelyScore(object);
        assertEquals("No", result);
    }

    @Test
    public void testDisplayOrbitAction_HighLikelihood() {
        object.daysOld = 100;
        object.longitude = 10;
        object.avgLongitude = 10;
        object.conjuctionCount = 50L;

        String result = assessment.displayOrbitAction(object);
        assertEquals(" Continue active monitoring", result);
    }

    @Test
    public void testDisplayOrbitAction_LowLikelihood() {
        object.daysOld = 10000;
        object.longitude = 150;
        object.avgLongitude = 0;
        object.conjuctionCount = 0L;

        String result = assessment.displayOrbitAction(object);
        assertEquals("Deorbited or Lost Track", result);
    }

    @Test
    public void testCalcCollRiskScore_VeryLow() {
        object.daysOld = 10000;
        object.longitude = 10;
        object.avgLongitude = 10;
        object.conjuctionCount = 0L;

        String result = assessment.calcCollRiskScore(object);
        assertEquals("Very Low Risk", result);
    }

    @Test
    public void testCalcCollRiskScore_High() {
        object.daysOld = 1;
        object.longitude = 180;
        object.avgLongitude = 0;
        object.conjuctionCount = 100L;

        String result = assessment.calcCollRiskScore(object);
        assertEquals("High Risk", result);
    }

    @Test
    public void testDisplayColAction_VeryLow() {
        object.daysOld = 10000;
        object.longitude = 10;
        object.avgLongitude = 10;
        object.conjuctionCount = 0L;

        String result = assessment.displayColAction(object);
        assertEquals("Routine Monitoring", result);
    }

    @Test
    public void testDisplayColAction_High() {
        object.daysOld = 1;
        object.longitude = 180;
        object.avgLongitude = 0;
        object.conjuctionCount = 100L;

        String result = assessment.displayColAction(object);
        assertEquals("Detailed Analysis", result);
    }
}
