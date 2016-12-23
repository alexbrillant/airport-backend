package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.HibernatePlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class HibernateFlightRepositoryIntegrationTest {
    private static final String PLANE_MODEL = UUID.randomUUID().toString();
    private static final Instant FLIGHT_DATE = Instant.ofEpochMilli(1478195361);
    private static final PlaneId PLANE_ID = new PlaneId(PLANE_MODEL);
    private static final Plane PLANE = new Plane(PLANE_ID, 1, 1, new ArrayList<>());

    private final AMSSystem amsSystem = mock(AMSSystem.class);
    private final PlaneRepository planeRepository = mock(PlaneRepository.class);
    private final FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);

    @BeforeClass
    public static void savePlane() {
        new HibernatePlaneRepository(mock(PlaneService.class)).savePlane(PLANE);
    }

    @Before
    public void setUp() {
        willReturn(PLANE).given(planeRepository).getPlane(PLANE_ID);
    }

    @Test
    public void givenAFlight_whenSaving_shouldBeAbleToRetrieve() {
        String flightNumber = "QK-918";
        willReturn(PLANE_MODEL).given(amsSystem).getPlaneModel(flightNumber);
        FlightId flightId = new FlightId(flightNumber, FLIGHT_DATE);
        Flight expectedFlight = new Flight(flightId, PLANE);

        flightRepository.saveFlight(expectedFlight);

        Flight actualFlight = flightRepository.getFlight(flightNumber, FLIGHT_DATE);
        assertEquals(expectedFlight, actualFlight);
        assertEquals(expectedFlight.getPlane(), actualFlight.getPlane());
    }

    @Test
    public void givenAFlightNumber_whenFetching_shouldReturnAFlight() {
        String flightNumber = "AB-123";
        willReturn(PLANE_MODEL).given(amsSystem).getPlaneModel(flightNumber);

        Flight flight = flightRepository.getFlight(flightNumber, FLIGHT_DATE);

        FlightId expectedId = new FlightId(flightNumber, FLIGHT_DATE);
        assertEquals(expectedId, flight.getId());
    }
}