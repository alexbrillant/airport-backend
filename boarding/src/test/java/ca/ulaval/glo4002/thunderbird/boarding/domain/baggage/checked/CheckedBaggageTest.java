package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class CheckedBaggageTest {

    private static final String CHECKED_TYPE = "checked";
    private BaggageFactory baggageFactory = new BaggageFactory();
    private Passenger passenger = mock(Passenger.class);
    private Length VALID_DIMENSION = Length.fromCentimeters(10);
    private Mass VALID_WEIGHT = Mass.fromGrams(10);


    @Test
    public void givenACheckedBaggage_whenVerifyingIfChecked_shouldReturnTrue() {
        NormalizedBaggageDTO withinLimitBaggage = new NormalizedBaggageDTO(VALID_DIMENSION, VALID_WEIGHT, CHECKED_TYPE);
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
        Baggage baggage = baggageFactory.createBaggage(passenger, withinLimitBaggage);


        boolean isChecked = baggage.isChecked();

        assertTrue(isChecked);
    }
}
