package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PassengerIntegrationTest {
    private static final UUID NON_EXISTENT_PASSENGER_HASH = new UUID(1L, 2L);

    private Passenger passenger;

    @Before
    public void setUp() {
        passenger = TestConfig.getDefaultPassenger();
    }

    @Test
    public void whenSavingPassenger_shouldBeAbleToRetrieve() {
        passenger.save();

        Passenger retrievedPassenger = Passenger.findByPassengerHash(passenger.getId());
        assertEquals(passenger.getId().toString(), retrievedPassenger.getId().toString());
    }

    @Test(expected = PassengerNotFoundException.class)
    public void whenFinding_shouldThrowNotFound() {
        Passenger.findByPassengerHash(NON_EXISTENT_PASSENGER_HASH);
    }
}