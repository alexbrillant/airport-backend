package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.StandardBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class StandardBaggageCollectionTest {

    private static final double DELTA = 0.01f;
    private static final String TYPE = "standard";
    private static final List<Baggage> EMPTY_LIST = new ArrayList<>();
    private static final double BAGGAGE_TOTAL_COST = 0;
    private static final double VIP_DISCOUNT = 0.95;

    private StandardBaggageCollection baggageCollection;
    private Baggage baggage;
    private Passenger passenger;

    @Before
    public void setup() {
        passenger = mock(Passenger.class);
        willReturn(false).given(passenger).isVip();
        baggage = mock(StandardBaggage.class);
        baggageCollection = new StandardBaggageCollection(passenger);
    }

    @Test
    public void shouldReturnRightValue() {
        assertEquals(TYPE, baggageCollection.getCollectionType());
        assertEquals(EMPTY_LIST, baggageCollection.getBaggages());
    }

    @Test
    public void whenCalculatingTotalCost_shouldReturnFree() {
        double cost = baggageCollection.calculateTotalCost();

        assertEquals(BAGGAGE_TOTAL_COST, cost, DELTA);
    }

    @Test
    public void givenAVipPassenger_whenCalculatingTotalCost_shouldReturnCorrectPrice() {
        willReturn(true).given(passenger).isVip();

        double cost = baggageCollection.calculateTotalCost();

        double expectedPrice = BAGGAGE_TOTAL_COST * VIP_DISCOUNT;
        assertEquals(expectedPrice, cost, DELTA);
    }

    @Test
    public void givenABaggage_whenAddingABaggageToTheCollection_shouldCollectionContainIt() {
        baggageCollection.addBaggage(baggage);

        boolean containBaggage = baggageCollection.collection.contains(baggage);

        assertTrue(containBaggage);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenACollectionWithAlreadyABaggage_whenValidating_shouldThrowException() {
        baggageCollection.addBaggage(baggage);
        baggageCollection.validateCollection(baggage);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenABaggageWithASpeciality_whenValidating_shouldThrowException() {
        willReturn(true).given(baggage).hasSpeciality(new Oversize());

        baggageCollection.validateCollection(baggage);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenABaggageWithOversize_whenValidating_shouldThrowException() {
        willReturn(true).given(baggage).hasSpeciality(new Overweight());

        baggageCollection.validateCollection(baggage);
    }

    @Test
    public void givenAnEmptyCollectionAndAValidBaggage_whenValidatingBaggage_shouldNotThrowException() {
        baggageCollection.validateCollection(baggage);
    }
}
