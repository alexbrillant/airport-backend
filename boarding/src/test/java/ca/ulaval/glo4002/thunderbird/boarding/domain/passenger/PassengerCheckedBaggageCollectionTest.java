package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.PassengerBaggageCollections;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PassengerCheckedBaggageCollectionTest {
    private static final UUID HASH = UUID.randomUUID();
    private static final Seat.SeatClass SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final boolean IS_VIP = true;
    private static final boolean IS_CHECKED_IN = true;
    private static final boolean IS_CHILD = true;

    private Flight flight = mock(Flight.class);
    private PassengerBaggageCollections baggagesCollection = mock(PassengerBaggageCollections.class);
    private Passenger passenger = new Passenger(HASH, SEAT_CLASS, !IS_VIP, !IS_CHECKED_IN, !IS_CHILD, flight, baggagesCollection);
    private Passenger vipPassenger = new Passenger(HASH, SEAT_CLASS, IS_VIP, !IS_CHECKED_IN, !IS_CHILD, flight, baggagesCollection);

    @Test
    public void whenAddingCheckedBaggage_shouldAddInCheckedBaggages() {
        Baggage baggage = mock(Baggage.class);
        passenger.addBaggage(baggage);

        verify(baggagesCollection).addBaggage(baggage);
    }

    @Test
    public void givenCheckedBaggages_whenGettingAllBaggages_shouldReturnTheseCheckedBaggages() {
        Set<Baggage> expectedBaggages = new HashSet<>();
        willReturn(expectedBaggages).given(baggagesCollection).getBaggages();

        Set<Baggage> actualBaggages = passenger.getBaggages();

        verify(baggagesCollection).getBaggages();
        assertSame(expectedBaggages, actualBaggages);
    }

    @Test
    public void givenAPrice_whenCalculatingBaggagesPrice_shouldReturnThisPrice() {
        double expectedPrice = 100;
        willReturn(expectedPrice).given(baggagesCollection).calculateTotalPrice();

        double actualPrice = passenger.calculateBaggagesPrice();

        verify(baggagesCollection).calculateTotalPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void givenAPrice_whenCalculationBaggagesPriceForVip_shouldReturnThisPriceWithADiscount() {
        double expectedPriceWithoutDiscount = 100;
        willReturn(expectedPriceWithoutDiscount).given(baggagesCollection).calculateTotalPrice();

        double actualPrice = vipPassenger.calculateBaggagesPrice();

        double expectedPrice = expectedPriceWithoutDiscount * 0.95f;
        verify(baggagesCollection).calculateTotalPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }
}