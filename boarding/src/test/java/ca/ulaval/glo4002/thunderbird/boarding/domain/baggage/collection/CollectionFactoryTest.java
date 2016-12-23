package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class CollectionFactoryTest {

    private static final String STANDARD = "standard";
    private static final String PERSONAL = "personal";
    private static final String MEDICAL = "medical";
    private static final String CHECKED = "checked";
    private static final Seat.SeatClass BUSINESS = Seat.SeatClass.BUSINESS;
    private static final Seat.SeatClass ECONOMY = Seat.SeatClass.ECONOMY;
    private static final String UNKNOWN_TYPE = "";

    private final CollectionFactory factory = new CollectionFactory();
    private Passenger passenger;
    private BaggageCollection baggageCollection;

    @Before
    public void setup() {
        passenger = mock(Passenger.class);
    }

    @Test
    public void givenTypeStandard_whenCreatingCollection_shouldReturnStandardBaggageCollection() {
        baggageCollection = factory.createCollection(passenger, STANDARD);

        assertThat(baggageCollection, instanceOf(StandardBaggageCollection.class));
    }

    @Test
    public void givenTypePersonal_whenCreatingCollection_shouldReturnPersonalBaggageCollection() {
        baggageCollection = factory.createCollection(passenger, PERSONAL);

        assertThat(baggageCollection, instanceOf(PersonalBaggageCollection.class));
    }

    @Test
    public void givenTypeMedical_whenCreatingCollection_shouldReturnMedicalBaggageCollection() {
        baggageCollection = factory.createCollection(passenger, MEDICAL);

        assertThat(baggageCollection, instanceOf(MedicalBaggageCollection.class));
    }

    @Test
    public void givenTypeCheckedAndSeatBusiness_whenCreatingCollection_shouldReturnBusinessBaggageCollection() {
        willReturn(BUSINESS).given(passenger).getSeatClass();
        baggageCollection = factory.createCollection(passenger, CHECKED);

        assertThat(baggageCollection, instanceOf(BusinessBaggageCollection.class));
    }

    @Test
    public void givenTypeCheckedAndSeatEconomy_whenCreatingCollection_shouldReturnEconomyBaggageCollection() {
        willReturn(ECONOMY).given(passenger).getSeatClass();
        baggageCollection = factory.createCollection(passenger, CHECKED);

        assertThat(baggageCollection, instanceOf(EconomicBaggageCollection.class));
    }

    @Test (expected = NoSuchStrategyException.class)
    public void givenUnknownType_whenCreatingCollection_shouldThrowException() {
        factory.createCollection(passenger, UNKNOWN_TYPE);
    }
}
