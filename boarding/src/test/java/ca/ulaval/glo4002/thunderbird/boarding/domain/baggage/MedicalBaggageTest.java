package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class MedicalBaggageTest {

    private final double DELTA = 0.01f;
    private final int DIMENSION_VALUE = 11;
    private final Length LINEAR_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE);
    private final int WEIGHT_VALUE = 22;
    private final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private final String PERSONNAL_TYPE = "medical";
    private final float MEDICAL_PRICE = 0;

    private BaggageFactory baggageFactory = new BaggageFactory();
    private Baggage baggage;
    private final Seat.SeatClass BUSINESS = Seat.SeatClass.BUSINESS;
    private NormalizedBaggageDTO baggageDTO = new NormalizedBaggageDTO(LINEAR_DIMENSION, WEIGHT, PERSONNAL_TYPE);

    @Before
    public void setup() {
        Passenger passenger = mock(Passenger.class);
        willReturn(BUSINESS).given(passenger).getSeatClass();
        baggage = baggageFactory.createBaggage(passenger, baggageDTO);
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(MEDICAL_PRICE, baggage.getPrice(), DELTA);
        assertFalse(baggage.isChecked());
        assertEquals(PERSONNAL_TYPE, baggage.getType());
    }
}
