package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReservationIntegrationTest {
    private static final int NON_EXISTENT_RESERVATION_NUMBER = 12345;

    private Reservation reservation;
    private Passenger passenger;

    @Before
    public void setUp() {
        reservation = TestConfig.getDefaultReservation();
        passenger = reservation.getPassengers().get(0);
    }

    @Test
    public void givenAReservation_whenSavingReservation_shouldBeAbleToRetrieve() {
        reservation.save();

        Reservation retrievedReservation = Reservation.findByReservationNumber(reservation.getId());
        List<Passenger> retrievedPassengers = retrievedReservation.getPassengers();
        assertEquals(reservation.getId(), retrievedReservation.getId());
        assertEquals(1, retrievedPassengers.size());
        assertEquals(passenger.getId(), retrievedPassengers.get(0).getId());
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenANonexistentReservationNumber_whenFinding_shouldThrowNotFound() {
        Reservation.findByReservationNumber(NON_EXISTENT_RESERVATION_NUMBER);
    }
}