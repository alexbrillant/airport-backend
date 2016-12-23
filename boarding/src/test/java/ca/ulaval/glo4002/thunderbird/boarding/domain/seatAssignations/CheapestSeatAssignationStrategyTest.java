package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;

public class CheapestSeatAssignationStrategyTest {
    private static final double CHEAPEST_PRICE = 100.0;
    private static final double NORMAL_PRICE = 150.0;

    private final Seat cheapestSeat = getSeat(CHEAPEST_PRICE);
    private final Seat normalSeat = getSeat(NORMAL_PRICE);
    private final SeatAssignationStrategy strategy = new CheapestSeatAssignationStrategy();

    @Test
    public void givenAValidSeatsList_whenApplyingStrategy_shouldFindCheapestSeat() {
        List<Seat> seats = Arrays.asList(normalSeat, cheapestSeat);

        Optional<Seat> takenSeat = strategy.applyStrategy(seats.stream());

        assertSame(cheapestSeat, takenSeat.get());
    }

    public Seat getSeat(double price) {
        int rowNumber = 21;
        String seatName = "name";
        int legRoom = 1;
        boolean hasWindow = false;
        boolean hasClearView = false;
        Seat.SeatClass seatClass = Seat.SeatClass.BUSINESS;
        boolean isExitRow = false;
        return new Seat(rowNumber, seatName, legRoom, hasWindow, hasClearView, price, seatClass, isExitRow);
    }
}