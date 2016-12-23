package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertSame;

public class LandscapeSeatAssignationStrategyTest {
    private static final double CHEAPEST_PRICE = 1.0;
    private static final double NORMAL_PRICE = 2.0;
    private static final boolean HAS_WINDOW = true;
    private static final boolean HAS_CLEAR_VIEW = true;

    private final SeatAssignationStrategy strategy = new LandscapeSeatAssignationStrategy();

    @Test
    public void givenOneSeatWithWindow_whenApplyingStrategy_shouldReturnThisSeat() {
        Seat seatWithoutWindow = getSeat(!HAS_WINDOW, !HAS_CLEAR_VIEW, NORMAL_PRICE);
        Seat seatWithWindow = getSeat(HAS_WINDOW, !HAS_CLEAR_VIEW, NORMAL_PRICE);
        List<Seat> seats = Arrays.asList(seatWithoutWindow, seatWithWindow);

        Optional<Seat> actualSeat = strategy.applyStrategy(seats.stream());

        assertSame(seatWithWindow, actualSeat.get());
    }

    @Test
    public void givenTwoSeatsWithWindows_whenApplyingStrategy_shouldReturnSeatWithClearView() {
        Seat seatWithWindow = getSeat(HAS_WINDOW, !HAS_CLEAR_VIEW, NORMAL_PRICE);
        Seat seatWithWindowAndClearView = getSeat(HAS_WINDOW, HAS_CLEAR_VIEW, NORMAL_PRICE);
        List<Seat> seats = Arrays.asList(seatWithWindow, seatWithWindowAndClearView);

        Optional<Seat> actualSeat = strategy.applyStrategy(seats.stream());

        assertSame(seatWithWindowAndClearView, actualSeat.get());
    }

    @Test
    public void givenTwoSeatsWithWindowsAndClearViews_whenApplyingStrategy_shouldReturnCheapestSeat() {
        Seat cheapestSeatWithWindowAndClearView = getSeat(HAS_WINDOW, HAS_CLEAR_VIEW, CHEAPEST_PRICE);
        Seat seatWithWindowAndClearView = getSeat(HAS_WINDOW, HAS_CLEAR_VIEW, NORMAL_PRICE);
        List<Seat> seats = Arrays.asList(cheapestSeatWithWindowAndClearView, seatWithWindowAndClearView);

        Optional<Seat> actualSeat = strategy.applyStrategy(seats.stream());

        assertSame(cheapestSeatWithWindowAndClearView, actualSeat.get());
    }

    public Seat getSeat(boolean hasWindow, boolean hasClearView, double price) {
        int rowNumber = 1;
        String seatName = "name";
        int legRoom = 1;
        Seat.SeatClass seatClass = Seat.SeatClass.BUSINESS;
        boolean isExitRow = false;
        return new Seat(rowNumber, seatName, legRoom, hasWindow, hasClearView, price, seatClass, isExitRow);
    }
}