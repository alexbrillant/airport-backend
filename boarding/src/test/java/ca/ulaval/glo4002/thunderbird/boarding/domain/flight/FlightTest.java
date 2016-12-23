package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlightTest {
    private static final String FLIGHT_NUMBER = "QK-918";
    private static final Instant FLIGHT_DATE = Instant.ofEpochMilli(123456);
    private static final double CARGO_WEIGHT_IN_GRAMS = 1000;
    private static final Mass MAXIMUM_CARGO_WEIGHT = Mass.fromGrams(CARGO_WEIGHT_IN_GRAMS);
    private static final Mass WEIGHT_SMALLER_THAN_MAXIMUM = Mass.fromGrams(CARGO_WEIGHT_IN_GRAMS - 1);
    private static final Mass WEIGHT_EQUAL_TO_MAXIMUM = Mass.fromGrams(CARGO_WEIGHT_IN_GRAMS);
    private static final Mass WEIGHT_BIGGER_THAN_MAXIMUM = Mass.fromGrams(CARGO_WEIGHT_IN_GRAMS + 1);

    private final Seat seat = mock(Seat.class);
    private final Plane plane = mock(Plane.class);
    private final List<Seat> seats = Arrays.asList(seat);
    private final FlightId flightId = new FlightId(FLIGHT_NUMBER, FLIGHT_DATE);
    private final SeatAssignationStrategy strategy = mock(SeatAssignationStrategy.class);
    private final Passenger passenger = mock(Passenger.class);

    private Flight flight = new Flight(flightId, plane);

    @Before
    public void setUp() {
        willReturn(seats).given(plane).getSeats();
        willReturn(MAXIMUM_CARGO_WEIGHT).given(plane).getMaximumCargoWeight();
        flight = new Flight(flightId, plane);
    }

    @Test
    public void givenABestSeat_whenAssigningBestSeat_shouldAssignAndReturnThisSeat() {
        Seat expectedSeat = mock(Seat.class);
        willReturn(expectedSeat).given(strategy).findBestSeat(seats, passenger);

        Seat actualSeat = flight.reserveSeat(strategy, passenger);

        verify(strategy).findBestSeat(seats, passenger);
        assertEquals(expectedSeat, actualSeat);
    }

    @Test
    public void givenABagageAndEnoughSpace_whenCheckingIfThereIsSpace_shouldReturnTrue() {
        Baggage baggage = mock(Baggage.class);
        willReturn(WEIGHT_SMALLER_THAN_MAXIMUM).given(baggage).getWeight();

        boolean haveEnoughSpace = flight.hasSpaceFor(baggage);

        assertTrue(haveEnoughSpace);
    }

    @Test
    public void givenABagageAndExactlyEnoughSpace_whenCheckingIfThereIsSpace_shouldReturnTrue() {
        Baggage baggage = mock(Baggage.class);
        willReturn(WEIGHT_EQUAL_TO_MAXIMUM).given(baggage).getWeight();

        boolean haveEnoughSpace = flight.hasSpaceFor(baggage);

        assertTrue(haveEnoughSpace);
    }

    @Test
    public void givenABagageAndNotEnoughSpace_whenCheckingIfThereIsSpace_shouldReturnFalse() {
        Baggage baggage = mock(Baggage.class);
        willReturn(WEIGHT_BIGGER_THAN_MAXIMUM).given(baggage).getWeight();

        boolean haveEnoughSpace = flight.hasSpaceFor(baggage);

        assertFalse(haveEnoughSpace);
    }
}
