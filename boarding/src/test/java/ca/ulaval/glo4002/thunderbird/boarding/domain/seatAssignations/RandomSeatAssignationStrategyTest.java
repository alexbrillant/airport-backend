package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class RandomSeatAssignationStrategyTest {
    private final SeatAssignationStrategy strategy = new RandomSeatAssignationStrategy();

    @Test
    public void givenAListOfSeats_whenApplyingStrategy_shouldChooseARandomSeat() {
        List<Seat> seats = Arrays.asList(mock(Seat.class), mock(Seat.class));

        Optional<Seat> takenSeat = strategy.applyStrategy(seats.stream());

        assertTrue(takenSeat.isPresent());
    }
}