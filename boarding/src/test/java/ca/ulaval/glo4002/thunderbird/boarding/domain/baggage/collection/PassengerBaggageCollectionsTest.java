package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.MedicalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.PersonalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.BusinessBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class PassengerBaggageCollectionsTest {

    private static final Mass PERSO_WEIGHT = Mass.fromKilograms(8);
    private static final Mass BUSINESS_WEIGHT = Mass.fromKilograms(20);
    private static final Mass MEDICAL_WEIGHT = Mass.fromKilograms(3);
    private static final float DELTA = 0.01f;
    private PassengerBaggageCollections collections;
    private Baggage medicalBaggage;
    private Baggage personalBaggage;
    private Baggage businessBaggage;

    @Before
    public void setup() {
        Passenger passenger = mock(Passenger.class);
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
        collections = new PassengerBaggageCollections(passenger);
        mockBaggages();
    }

    private void mockBaggages() {
        medicalBaggage = mock(MedicalBaggage.class);
        personalBaggage = mock(PersonalBaggage.class);
        businessBaggage = mock(BusinessBaggage.class);

        willReturn(PERSO_WEIGHT).given(personalBaggage).getWeight();
        willReturn(BUSINESS_WEIGHT).given(businessBaggage).getWeight();
        willReturn(MEDICAL_WEIGHT).given(medicalBaggage).getWeight();

        willReturn(false).given(medicalBaggage).hasSpeciality(any(Speciality.class));
        willReturn("medical").given(medicalBaggage).getType();
        willReturn("checked").given(businessBaggage).getType();
        willReturn("personal").given(personalBaggage).getType();
        willReturn(0.0d).given(medicalBaggage).getPrice();
        willReturn(50.0d).given(businessBaggage).getPrice();
        willReturn(0.0d).given(personalBaggage).getPrice();
    }

    @Test
    public void givenABaggageCollections_whenAddingANewTypeOfBaggage_shouldAddANewCollection() {
        int collectionSizeBefore = collections.collection.size();

        collections.addBaggage(medicalBaggage);

        int expectedSize = collectionSizeBefore + 1;
        int actualSize = collections.collection.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void givenABaggageCollections_whenAddingASecondBaggageOfAType_shouldNotAddANewCollection() {
        willReturn("medical").given(medicalBaggage).getType();
        collections.addBaggage(medicalBaggage);
        int expectedSize = collections.collection.size();

        collections.addBaggage(medicalBaggage);

        int actualSize = collections.collection.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void givenACollectionsWithThreeBaggages_whenGettingBaggagesList_shouldReturnAllOfThem() {
        collections.addBaggage(medicalBaggage);
        collections.addBaggage(businessBaggage);
        collections.addBaggage(personalBaggage);

        Set<Baggage> actualCollection = collections.getBaggages();

        assertTrue(actualCollection.contains(medicalBaggage));
        assertTrue(actualCollection.contains(businessBaggage));
        assertTrue(actualCollection.contains(personalBaggage));
    }

    @Test
    public void givenACollectionsWithThreeBaggages_whenCalculatingMass_shouldReturnCorrectMass() {
        collections.addBaggage(medicalBaggage);
        collections.addBaggage(businessBaggage);
        collections.addBaggage(personalBaggage);

        Mass actualMass = collections.calculateBaggagesMass();

        Mass expectedMass = Mass.fromKilograms(MEDICAL_WEIGHT.toKilograms() + BUSINESS_WEIGHT.toKilograms() +
                PERSO_WEIGHT.toKilograms());
        assertEquals(expectedMass, actualMass);
    }

    @Test
    public void givenACollectionsWithThreeBaggages_whenCalculatingTotalPrice_shouldReturnCorrectPrice() {
        collections.addBaggage(medicalBaggage);
        collections.addBaggage(businessBaggage);
        collections.addBaggage(businessBaggage);
        collections.addBaggage(businessBaggage);

        double actualPrice = collections.calculateTotalPrice();

        double expectedPrice = 50;
        assertEquals(expectedPrice, actualPrice, DELTA);
    }
}
