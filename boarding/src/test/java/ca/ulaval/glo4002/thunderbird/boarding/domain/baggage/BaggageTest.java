package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class BaggageTest {

    private static final float DELTA = 0.01f;
    private final int DIMENSION_VALUE = 100;
    private final int INVALID_DIMENSION_VALUE = 200;
    private final Length LINEAR_DIMENSION = Length.fromCentimeters(DIMENSION_VALUE);
    private final Length INVALID_DIMENSION = Length.fromCentimeters(INVALID_DIMENSION_VALUE);

    private final int WEIGHT_VALUE = 5;
    private final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private final Mass INVALID_WEIGHT = Mass.fromGrams(WEIGHT_VALUE - 1);
    private final String CHECKED_TYPE = "checked";
    private final Seat.SeatClass BUSINESS = Seat.SeatClass.BUSINESS;

    private BaggageFactory baggageFactory = new BaggageFactory();
    private Baggage baggage;
    private Baggage oversizeBaggage;
    private Passenger passenger;

    @Before
    public void setup() {
        passenger = mock(Passenger.class);
        willReturn(BUSINESS).given(passenger).getSeatClass();
        baggage = createBaggage(LINEAR_DIMENSION, WEIGHT, CHECKED_TYPE);
        oversizeBaggage = createBaggage(INVALID_DIMENSION, WEIGHT, CHECKED_TYPE);
    }

    private Baggage createBaggage(Length length, Mass mass, String type) {
        NormalizedBaggageDTO dto = new NormalizedBaggageDTO(length, mass, type);
        return baggageFactory.createBaggage(passenger, dto);
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(LINEAR_DIMENSION, baggage.getDimension());
        assertEquals(WEIGHT, baggage.getWeight());
        assertEquals(CHECKED_TYPE, baggage.getType());
    }

    @Test
    public void givenOversizeBaggagge_whenCheckingIfHasSpecialityOversize_shouldReturnTrue () {

        boolean expectedOversize = oversizeBaggage.hasSpeciality(new Oversize());

        assertTrue(expectedOversize);
    }

    @Test
    public void givenBaggage_whenCheckingIfHasSpecialityOversize_shouldReturnFalse() {
        boolean hasSpecialities = baggage.hasSpeciality(new Oversize());

        assertFalse(hasSpecialities);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void whenDimensionIsInvalid_shouldThrowAnException() {
        oversizeBaggage.validateBaggage(LINEAR_DIMENSION, WEIGHT);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void whenWeightIsInvalid_shouldThrowAnException() {
        baggage.validateBaggage(LINEAR_DIMENSION, INVALID_WEIGHT);
    }
}