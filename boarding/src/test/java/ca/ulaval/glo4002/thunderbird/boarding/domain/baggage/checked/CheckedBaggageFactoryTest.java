package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class CheckedBaggageFactoryTest {

    private static final Length ECONOMY_LENGTH = EconomicBaggage.MAX_LENGTH;
    private static final Length ECONOMY_INVALID_LENGTH = Length.fromCentimeters(ECONOMY_LENGTH.toCentimeters() + 1);
    private static final Mass ECONOMY_WEIGHT = EconomicBaggage.MAX_WEIGHT;
    private static final Mass ECONOMY_INVALID_WEIGHT = Mass.fromKilograms(ECONOMY_WEIGHT.toKilograms() + 1);
    private static final Length BUSINESS_LENGTH = BusinessBaggage.MAX_LENGTH;
    private static final Length BUSINESS_INVALID_LENGTH = Length.fromCentimeters(BUSINESS_LENGTH.toCentimeters() + 1);
    private static final Mass BUSINESS_WEIGHT = BusinessBaggage.MAX_WEIGHT;
    private static final Mass BUSINESS_INVALID_WEIGHT = Mass.fromKilograms(BUSINESS_WEIGHT.toKilograms() + 1);
    private static final String TYPE = "checked";

    private CheckedBaggageFactory checkedBaggageFactory;
    private Passenger passenger;
    private NormalizedBaggageDTO economyDTO;
    private NormalizedBaggageDTO businessDTO;
    private Baggage baggage;

    @Before
    public void setup() {
        passenger = mock(Passenger.class);
        checkedBaggageFactory = new CheckedBaggageFactory();
    }

    @Test
    public void givenAPassengerWithEconomicSeatClass_whenCreatingCheckedBaggage_shouldReturnEconomicBaggage () {
        willReturn(Seat.SeatClass.ECONOMY).given(passenger).getSeatClass();
        economyDTO = new NormalizedBaggageDTO(ECONOMY_LENGTH, ECONOMY_WEIGHT, TYPE);

        baggage = checkedBaggageFactory.createCheckedBaggage(passenger, economyDTO);

        assertThat(baggage, instanceOf(EconomicBaggage.class));
    }

    @Test
    public void givenAnInvalidWeightDTO_whenCreatingCheckedBaggage_shouldBaggageHaveSpecialityOverweight() {
        willReturn(Seat.SeatClass.ECONOMY).given(passenger).getSeatClass();
        economyDTO = new NormalizedBaggageDTO(ECONOMY_LENGTH, ECONOMY_INVALID_WEIGHT, TYPE);

        baggage = checkedBaggageFactory.createCheckedBaggage(passenger, economyDTO);

        boolean isOverWeight = baggage.hasSpeciality(new Overweight());
        assertTrue(isOverWeight);
    }

    @Test
    public void givenAnInvalidDimensionDTO_whenCreatingCheckedBaggage_shouldBaggageHaveSpecialityOversize() {
        willReturn(Seat.SeatClass.ECONOMY).given(passenger).getSeatClass();
        economyDTO = new NormalizedBaggageDTO(ECONOMY_INVALID_LENGTH, ECONOMY_WEIGHT, TYPE);

        baggage = checkedBaggageFactory.createCheckedBaggage(passenger, economyDTO);

        boolean isOverSize = baggage.hasSpeciality(new Oversize());
        assertTrue(isOverSize);
    }

    @Test
    public void givenAPassengerWithBusinessSeatClass_whenCreatingCheckedBaggage_shouldReturnBusinessBaggage () {
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
        businessDTO = new NormalizedBaggageDTO(ECONOMY_LENGTH, ECONOMY_WEIGHT, TYPE);

        baggage = checkedBaggageFactory.createCheckedBaggage(passenger, businessDTO);

        assertThat(baggage, instanceOf(BusinessBaggage.class));
    }

    @Test
    public void givenAnInvalidWeightDTO_whenCreatingBusinessBaggage_shouldBaggageHaveSpecialityOverweight() {
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
        businessDTO = new NormalizedBaggageDTO(BUSINESS_LENGTH, BUSINESS_INVALID_WEIGHT, TYPE);

        baggage = checkedBaggageFactory.createCheckedBaggage(passenger, businessDTO);

        boolean isOverWeight = baggage.hasSpeciality(new Overweight());
        assertTrue(isOverWeight);
    }

    @Test
    public void givenAnInvalidDimensionDTO_whenCreatingBusinessBaggage_shouldBaggageHaveSpecialityOversize() {
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
        businessDTO = new NormalizedBaggageDTO(BUSINESS_INVALID_LENGTH, BUSINESS_WEIGHT, TYPE);

        baggage = checkedBaggageFactory.createCheckedBaggage(passenger, businessDTO);

        boolean isOverSize = baggage.hasSpeciality(new Oversize());
        assertTrue(isOverSize);
    }
}
