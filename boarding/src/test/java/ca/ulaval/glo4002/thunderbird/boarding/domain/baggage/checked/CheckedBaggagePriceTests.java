package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;


public class CheckedBaggagePriceTests {
    private static final int BAGGAGE_COST = 50;
    private static final double OVERWEIGHT_BAGGAGE_EXCESS_FEES = 1.1f;
    private static final double SPORT_BAGGAGE_EXCESS_FEES = 1.25f;
    private static final double DELTA = 0.01f;
    private static final String SPORT_TYPE = "sport";
    private static final String CHECKED_TYPE = "checked";
    private BaggageFactory baggageFactory = new BaggageFactory();

    private final Length validDimension = BusinessBaggage.MAX_LENGTH;
    private final Length invalidDimension = Length.fromCentimeters(validDimension.toCentimeters() + 1);
    private final Mass validWeight = BusinessBaggage.MAX_WEIGHT;
    private final Mass invalidWeight = Mass.fromGrams(validWeight.toGrams() + 1);
    private Passenger passenger;
    private Baggage baggage;

    @Before
    public void setUp(){
        passenger = mock(Passenger.class);
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
    }

    @Test
    public void givenValidDimensionAndWeight_whenCalculatingPrice_shouldNotPayOverweightExcessFees() {
        NormalizedBaggageDTO withinLimitBaggage = new NormalizedBaggageDTO(validDimension, validWeight, CHECKED_TYPE);
        baggage = baggageFactory.createBaggage(passenger, withinLimitBaggage);

        double actualPrice = baggage.getPrice();

        assertEquals(BAGGAGE_COST, actualPrice, DELTA);
    }

    @Test
    public void givenOversizeDimension_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        NormalizedBaggageDTO excessLength = new NormalizedBaggageDTO(invalidDimension, validWeight, CHECKED_TYPE);
        baggage = baggageFactory.createBaggage(passenger, excessLength);

        double actualPrice = baggage.getPrice();

        double expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidWeight_whenCalculatingPrice_shouldPayOverweightExcessFees() {
        NormalizedBaggageDTO excessWeight = new NormalizedBaggageDTO(validDimension, invalidWeight, CHECKED_TYPE);
        baggage = baggageFactory.createBaggage(passenger, excessWeight);

        double actualPrice = baggage.getPrice();

        double expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenInvalidDimensionAndWeight_whenCalculatingPrice_shouldPayOverweightExcessFeesTwoTimes() {
        NormalizedBaggageDTO excessWnL = new NormalizedBaggageDTO(invalidDimension, invalidWeight, CHECKED_TYPE);

        baggage = baggageFactory.createBaggage(passenger, excessWnL);

        double actualPrice = baggage.getPrice();

        double expectedPrice = BAGGAGE_COST * OVERWEIGHT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithValidDimensionAndWeight_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        NormalizedBaggageDTO sportWithinLimit = new NormalizedBaggageDTO(validDimension, validWeight, SPORT_TYPE);
        baggage = baggageFactory.createBaggage(passenger, sportWithinLimit);

        double actualPrice = baggage.getPrice();

        double expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithInvalidDimension_whenCalculatingPrice_shouldOnlyPaySportExcessFees() {
        NormalizedBaggageDTO sportWithinLimit = new NormalizedBaggageDTO(invalidDimension,validWeight,SPORT_TYPE);
        baggage = baggageFactory.createBaggage(passenger, sportWithinLimit);

        double actualPrice = baggage.getPrice();

        double expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }

    @Test
    public void givenSportBaggageWithInvalidWeight_whenCalculatingPrice_shouldPaySportAndOverweightExcessFees() {
        NormalizedBaggageDTO sportWithinLimit = new NormalizedBaggageDTO(validDimension,invalidWeight,SPORT_TYPE);
        baggage = baggageFactory.createBaggage(passenger, sportWithinLimit);

        double actualPrice = baggage.getPrice();

        double expectedPrice = BAGGAGE_COST * SPORT_BAGGAGE_EXCESS_FEES * OVERWEIGHT_BAGGAGE_EXCESS_FEES;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }
}
