package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

public class HibernatePlaneRepositoryIntegrationTest {
    private static final PlaneId EXISTENT_PLANE_ID = new PlaneId(UUID.randomUUID().toString());
    private static final PlaneId INEXISTENT_PLANE_ID = new PlaneId(UUID.randomUUID().toString());
    private static final int NB_SEAT = 10;
    private static final int CARGO_WEIGHT = 10;

    private static final PlaneService planeService = mock(PlaneService.class);
    private static final HibernatePlaneRepository repository = new HibernatePlaneRepository(planeService);

    @BeforeClass
    public static void setUpClass() {
        repository.savePlane(createPlane(EXISTENT_PLANE_ID));
        willReturn(createPlane(INEXISTENT_PLANE_ID)).given(planeService).getPlane(INEXISTENT_PLANE_ID);
    }

    @Test
    public void whenGettingAnExistentPlan_shouldBeAbleToRetrieve() {
        Plane plane = repository.getPlane(EXISTENT_PLANE_ID);

        verify(planeService, never()).getPlane(EXISTENT_PLANE_ID);
        assertEquals(EXISTENT_PLANE_ID, plane.getId());
        assertFalse(plane.getSeats().isEmpty());
    }

    @Test
    public void whenGettingAnInexistentPlan_shouldBeAbleToRetrieveFromPlaneService() {
        Plane plane = repository.getPlane(INEXISTENT_PLANE_ID);

        verify(planeService).getPlane(INEXISTENT_PLANE_ID);
        assertEquals(INEXISTENT_PLANE_ID, plane.getId());
        assertFalse(plane.getSeats().isEmpty());
    }

    private static Plane createPlane(PlaneId planeId) {
        return new Plane(planeId, NB_SEAT, CARGO_WEIGHT, Arrays.asList(createSeat()));
    }

    public static Seat createSeat() {
        int rowNumber = 1;
        String seatName = "name";
        int legRoom = 0;
        boolean hasWindow = false;
        boolean hasClearView = false;
        double price = 0;
        Seat.SeatClass seatClass = Seat.SeatClass.BUSINESS;
        boolean isExitRow = false;
        return new Seat(rowNumber, seatName, legRoom, hasWindow, hasClearView, price, seatClass, isExitRow);
    }
}