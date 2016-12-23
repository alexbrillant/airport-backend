package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat.SeatClass;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class SeatAssignationStrategyTest {
    private static final boolean IS_EXIT_ROW = true;
    private static final boolean IS_CHILD = true;

    private final Seat expectedBestSeat = mock(Seat.class);
    private final Passenger passenger = mock(Passenger.class);

    private List<Seat> actualFilteredSeats;
    private SeatAssignationStrategy strategy;

    @Before
    public void setUp() {
        strategy = new SeatAssignationStrategy() {
            @Override
            protected Optional<Seat> applyStrategy(Stream<Seat> filteredSeats) {
                actualFilteredSeats = filteredSeats.collect(Collectors.toList());
                return Optional.of(expectedBestSeat);
            }
        };
    }

    @Test
    public void givenTwoSeatsAndOneBusiness_whenFindingBestSeat_shouldReturnBusinessSeat() {
        initPassenger(SeatClass.BUSINESS, !IS_CHILD);
        Seat economyClassSeat = getSeat(SeatClass.ECONOMY, !IS_EXIT_ROW);
        Seat businessClassSeat = getSeat(SeatClass.BUSINESS, !IS_EXIT_ROW);

        strategy.findBestSeat(Arrays.asList(economyClassSeat, businessClassSeat), passenger);

        assertEquals(1, actualFilteredSeats.size());
        assertEquals(businessClassSeat, actualFilteredSeats.get(0));
    }

    @Test
    public void givenAChildAndOneOfTwoSeatIsAnExistRow_whenFindingBestSeat_shouldNotReturnAnExitRow() {
        initPassenger(SeatClass.ECONOMY, IS_CHILD);
        Seat exitRowSeat = getSeat(SeatClass.ECONOMY, IS_EXIT_ROW);
        Seat seat = getSeat(SeatClass.ECONOMY, !IS_EXIT_ROW);

        strategy.findBestSeat(Arrays.asList(exitRowSeat, seat), passenger);

        assertEquals(1, actualFilteredSeats.size());
        assertSame(seat, actualFilteredSeats.get(0));
    }

    @Test
    public void whenGettingBestSeat_shouldBeTheRightSeat() {
        Seat actualBestSeat = strategy.findBestSeat(new ArrayList<>(), passenger);

        assertSame(expectedBestSeat, actualBestSeat);
    }


    @Test(expected = NoMoreSeatAvailableException.class)
    public void givenNoSeatAvailable_whenApplyingStrategy_shouldThrowAnException() {
        SeatAssignationStrategy brokenStrategy = new SeatAssignationStrategy() {
            @Override
            protected Optional<Seat> applyStrategy(Stream<Seat> filteredSeats) {
                return Optional.empty();
            }
        };

        brokenStrategy.findBestSeat(new ArrayList<>(), passenger);
    }

    private void initPassenger(SeatClass seatClass, boolean isChild) {
        willReturn(seatClass).given(passenger).getSeatClass();
        willReturn(isChild).given(passenger).isChild();
    }

    private Seat getSeat(SeatClass seatClass, boolean isExitRow) {
        int rowNumber = 1;
        String seatName = "name";
        int legRoom = 1;
        boolean hasWindow = false;
        boolean hasClearView = false;
        double price = 1;
        return new Seat(rowNumber, seatName, legRoom, hasWindow, hasClearView, price, seatClass, isExitRow);
    }
}