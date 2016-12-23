package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AMSSystemRandomTest {
    private static final String QK918_FLIGHT = "QK-918";
    private static final String NK_FLIGHT = "NK-750";
    private static final String QK432_FLIGHT = "QK-432";
    private static final String OTHER_FLIGHT = "AA-123";
    private AMSSystemRandom amsSystem;

    @Before
    public void before() {
        amsSystem = new AMSSystemRandom();
    }

    @Test
    public void givenFlightQK918_whenGettingPlaneModel_shouldReturnDash8Model() {
        String planeModel = amsSystem.getPlaneModel(QK918_FLIGHT);

        assertEquals(AMSSystemRandom.DASH_8, planeModel);
    }

    @Test
    public void givenFlightNK_whenGettingPlaneModel_shouldReturnA320Model() {
        String planeModel = amsSystem.getPlaneModel(NK_FLIGHT);

        assertEquals(AMSSystemRandom.AIRBUS_A320, planeModel);
    }

    @Test
    public void givenFlightQK432_whenGettingPlaneModel_shouldReturnBoeingModel() {
        String planeModel = amsSystem.getPlaneModel(QK432_FLIGHT);

        assertEquals(AMSSystemRandom.BOEING_777_300, planeModel);
    }

    @Test
    public void givenOtherFlight_whenGettingPlaneModel_planeModelShouldBeConsistent() {
        String firstCallPlaneModel = amsSystem.getPlaneModel(OTHER_FLIGHT);
        String secondCallPlaneModel = amsSystem.getPlaneModel(OTHER_FLIGHT);

        assertEquals(firstCallPlaneModel, secondCallPlaneModel);
    }
}
