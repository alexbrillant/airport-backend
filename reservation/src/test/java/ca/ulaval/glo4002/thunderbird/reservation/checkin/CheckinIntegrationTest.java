package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CheckinIntegrationTest {
    private static final boolean NOT_VIP = false;
    private static final UUID NON_EXISTENT_CHECKIN_HASH = new UUID(1L, 2L);
    private static final UUID NON_EXISTENT_PASSENGER_HASH = new UUID(3L, 4L);

    private static final UUID PASSENGER_HASH = new UUID(5L, 6L);

    @Test
    public void givenCheckinWithExistentPassenger_whenFindingPassenger_shouldBeAbleToRetrieve() {
        Passenger passenger = TestConfig.getDefaultPassenger();
        passenger.save();
        UUID existentPassengerHash = passenger.getId();
        Checkin checkin = new Checkin(existentPassengerHash, Checkin.SELF, NOT_VIP);

        Passenger retrievedPassenger = checkin.getPassenger();

        assertEquals(passenger.getId().toString(), retrievedPassenger.getId().toString());
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenCheckinWithNonExistentPassenger_whenFindingPassenger_shouldThrowNotFound() {
        Checkin checkin = new Checkin(NON_EXISTENT_PASSENGER_HASH, Checkin.SELF, NOT_VIP);

        checkin.getPassenger();
    }

    @Test
    public void givenValidCheckin_whenCheckinIsSaved_shouldBeAbleToRetrieve() {
        Checkin checkin = new Checkin(PASSENGER_HASH, Checkin.SELF, NOT_VIP);

        checkin.save();

        Checkin retrievedCheckin = Checkin.findByCheckinHash(checkin.getId());
        assertEquals(checkin.getId().toString(), retrievedCheckin.getId().toString());
    }

    @Test(expected = CheckinNotFoundException.class)
    public void whenFinding_shouldThrowNotFound() {
        Checkin.findByCheckinHash(NON_EXISTENT_CHECKIN_HASH);
    }
}