package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.BusinessBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class BusinessBaggageCollectionTest {

    private static final int FREE_BAGGAGE_COUNT = 2;
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(30);
    private static final double FREE = 0;
    private static final double BAGGAGE_PRICE = 20;
    private static final float DELTA = 0.01f;
    private static final String TYPE = "checked";
    private static final int VIP_COUNT_LIMIT = 4;
    private static final int NORMAL_COUNT_LIMIT = 3;
    private static final double VIP_DISCOUNT = 0.95;
    private BusinessBaggageCollection businessBaggageCollection;
    private Baggage baggage;
    private Passenger passenger;

    @Before
    public void setup() {
        baggage = mock(BusinessBaggage.class);
        passenger = mock(Passenger.class);
        willReturn(false).given(passenger).isVip();
        businessBaggageCollection = new BusinessBaggageCollection(passenger);
        willReturn(BAGGAGE_PRICE).given(baggage).getPrice();
        businessBaggageCollection.addBaggage(baggage);
        businessBaggageCollection.addBaggage(baggage);
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(WEIGHT_LIMIT, businessBaggageCollection.getWeightLimit());
        assertEquals(DIMENSION_LIMIT, businessBaggageCollection.getDimensionLimit());
        assertEquals(FREE_BAGGAGE_COUNT, businessBaggageCollection.getFreeBaggageCount());
        assertEquals(TYPE, businessBaggageCollection.getCollectionType());
    }

    @Test
    public void givenAVipPassenger_whenGettingBaggageCountLimit_shouldReturnVipCountLimit () {
        willReturn(true).given(passenger).isVip();

        int actualBaggageCount = businessBaggageCollection.getBaggageCountLimit();

        assertEquals(VIP_COUNT_LIMIT, actualBaggageCount);
    }

    @Test
    public void givenANonVipPassenger_whenGettingBaggageCountLimit_shouldReturnCountLimit () {
        willReturn(false).given(passenger).isVip();

        int actualBaggageCount = businessBaggageCollection.getBaggageCountLimit();

        assertEquals(NORMAL_COUNT_LIMIT, actualBaggageCount);
    }

    @Test
    public void givenCollectionWithMoreThanOneBaggage_whenCalculatingTotalCost_shouldReturnAppropriatePrice() {
        businessBaggageCollection.addBaggage(baggage);

        double actualPrice = businessBaggageCollection.calculateTotalCost();

        assertEquals(BAGGAGE_PRICE, actualPrice, DELTA);
    }

    @Test
    public void givenCollectionForAVip_whenCalculatingTotalCost_shouldReturnAppropriatePrice() {
        willReturn(true).given(passenger).isVip();
        businessBaggageCollection.addBaggage(baggage);

        double actualPrice = businessBaggageCollection.calculateTotalCost();

        double expectedPrice = BAGGAGE_PRICE * VIP_DISCOUNT;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenCollectionWithOnlyOneBaggage_whenCalculatingTotalCost_shouldBeFree() {
        double actualPrice = businessBaggageCollection.calculateTotalCost();

        assertEquals(FREE, actualPrice, DELTA);
    }
}
