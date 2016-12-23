package ca.ulaval.glo4002.thunderbird.boarding.services;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.exceptions.PlaneNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PlaneServiceGlo3000BorderTest {

    private static final String VALID_PLANE_MODEL = "dash-8";
    private static final String INVALID_PLANE_MODEL = "asimfsa";

    private PlaneService planeService = new PlaneServiceGlo3000();

    @Test
    public void givenAValidPlaneId_whenGettingPlaneFromService_shouldObtainAPlane() {
        PlaneId validPlaneId = new PlaneId(VALID_PLANE_MODEL);

        Plane actualPlane = planeService.getPlane(validPlaneId);

        assertNotNull(actualPlane);
    }

    @Test(expected = PlaneNotFoundException.class)
    public void givenAnInvalidPlaneId_whenGettingPlaneFromService_shouldThrowAnException() {
        PlaneId invalidPlaneId = new PlaneId(INVALID_PLANE_MODEL);

        planeService.getPlane(invalidPlaneId);
    }
}