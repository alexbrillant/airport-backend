package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;

public class MostLegRoomSeatAssignationStrategyTest {
    private static final double CHEAPEST_PRICE = 1.0;
    private static final double NORMAL_PRICE = 2.0;
    private static final int LEG_ROOM = 2;

    private final SeatAssignationStrategy strategy = new MostLegRoomSeatAssignationStrategy();

    @Test
    public void givenTwoSeats_whenApplyingStrategy_shouldFindSeatWithMoreLegRoom() {
        Seat seat = getSeat(LEG_ROOM, NORMAL_PRICE);
        Seat seatWithMoreLegRoom = getSeat(LEG_ROOM + 1, NORMAL_PRICE);
        List<Seat> seats = Arrays.asList(seat, seatWithMoreLegRoom);

        Optional<Seat> actualSeat = strategy.applyStrategy(seats.stream());

        assertSame(seatWithMoreLegRoom, actualSeat.get());
    }

    @Test
    public void givenTwoSeatsWithSameLegRoom_whenApplyingStrategy_shouldFindCheapestSeat() {
        Seat seat = getSeat(LEG_ROOM, NORMAL_PRICE);
        Seat cheapestSeat = getSeat(LEG_ROOM, CHEAPEST_PRICE);
        List<Seat> seats = Arrays.asList(cheapestSeat, seat);

        Optional<Seat> actualSeat = strategy.applyStrategy(seats.stream());

        assertSame(cheapestSeat, actualSeat.get());
    }

    public Seat getSeat(int legRoom, double price) {
        int rowNumber = 1;
        String seatName = "name";
        boolean hasWindow = false;
        boolean hasClearView = false;
        Seat.SeatClass seatClass = Seat.SeatClass.BUSINESS;
        boolean isExitRow = false;
        return new Seat(rowNumber, seatName, legRoom, hasWindow, hasClearView, price, seatClass, isExitRow);
    }
}