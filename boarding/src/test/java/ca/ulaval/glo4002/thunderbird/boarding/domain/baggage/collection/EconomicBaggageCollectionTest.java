package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.EconomicBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class EconomicBaggageCollectionTest {

    private static final int FREE_BAGGAGE_COUNT = 1;
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(23);
    private static final double DELTA = 0.01f;
    private static final double BAGGAGE_PRICE = 20.0f;
    private static final double FREE = 0;
    private static final String TYPE = "checked";
    private static final int VIP_COUNT_LIMIT = 4;
    private static final int NORMAL_COUNT_LIMI = 3;
    private static final double VIP_DISCOUNT = 0.95;
    private EconomicBaggageCollection economicBaggageCollection;
    private Baggage baggage;
    private Passenger passenger;

    @Before
    public void setup() {
        baggage = mock(EconomicBaggage.class);
        passenger = mock(Passenger.class);
        economicBaggageCollection = new EconomicBaggageCollection(passenger);
        willReturn(BAGGAGE_PRICE).given(baggage).getPrice();
        economicBaggageCollection.addBaggage(baggage);
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(WEIGHT_LIMIT, economicBaggageCollection.getWeightLimit());
        assertEquals(DIMENSION_LIMIT, economicBaggageCollection.getDimensionLimit());
        assertEquals(FREE_BAGGAGE_COUNT, economicBaggageCollection.getFreeBaggageCount());
        assertEquals(TYPE, economicBaggageCollection.getCollectionType());
    }

    @Test
    public void givenAVipPassenger_whenGettingBaggageCountLimit_shouldReturnVipCountLimit () {
        willReturn(true).given(passenger).isVip();

        int actualBaggageCount = economicBaggageCollection.getBaggageCountLimit();

        assertEquals(VIP_COUNT_LIMIT, actualBaggageCount);
    }

    @Test
    public void givenANonVipPassenger_whenGettingBaggageCountLimit_shouldReturnCountLimit () {
        willReturn(false).given(passenger).isVip();

        int actualBaggageCount = economicBaggageCollection.getBaggageCountLimit();

        assertEquals(NORMAL_COUNT_LIMI, actualBaggageCount);
    }

    @Test
    public void givenCollectionWithMoreThanOneBaggage_whenCalculatingTotalCost_shouldReturnAppropriatePrice() {
        economicBaggageCollection.addBaggage(baggage);

        double actualPrice = economicBaggageCollection.calculateTotalCost();

        assertEquals(BAGGAGE_PRICE, actualPrice, DELTA);
    }

    @Test
    public void givenCollectionForAVip_whenCalculatingTotalCost_shouldReturnAppropriatePrice() {
        willReturn(true).given(passenger).isVip();
        economicBaggageCollection.addBaggage(baggage);
        economicBaggageCollection.addBaggage(baggage);
        economicBaggageCollection.addBaggage(baggage);

        double actualPrice = economicBaggageCollection.calculateTotalCost();

        double expectedPrice = BAGGAGE_PRICE * 3 * VIP_DISCOUNT;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenCollectionWithOnlyOneBaggage_whenCalculatingTotalCost_shouldBeFree() {
        double actualPrice = economicBaggageCollection.calculateTotalCost();

        assertEquals(FREE, actualPrice, DELTA);
    }
}
