package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;

public class ReservationTest {
    private Reservation reservation;

    @Before
    public void givenDefaultReservation() {
        reservation = TestConfig.getDefaultReservation();
    }

    @Test
    public void shouldReturnTheRightId() {
        int actualReservationNumber = reservation.getId();
        assertEquals(TestConfig.RESERVATION_NUMBER, actualReservationNumber);
    }

    @Test
    public void shouldReturnTheRightFlightNumber() {
        String actualFlightNumber = reservation.getFlightNumber();
        assertEquals(TestConfig.FLIGHT_NUMBER, actualFlightNumber);
    }

    @Test
    public void shouldReturnTheRightFlightDate() {
        Instant actualFlightDate = reservation.getFlightDate();
        assertEquals(TestConfig.FLIGHT_DATE, actualFlightDate);
    }

    @Test
    public void shouldReturnAPassenger() {
        List<Passenger> passengerList = reservation.getPassengers();
        assertFalse(passengerList.isEmpty());
    }

    @Test
    public void givenPassengerReservation_shouldBeTheSameReservation() {
        Passenger passenger = reservation.getPassengers().get(0);
        Reservation passengerReservation = passenger.getReservation();

        assertSame(reservation, passengerReservation);
    }

    @Test
    public void givenPassengerReservation_shouldBeTheSameFlightNumber() {
        List<Passenger> passengerList = reservation.getPassengers();
        Passenger passenger = passengerList.get(0);
        String expectedFlightNumber = reservation.getFlightNumber();
        String actualFlightNumber = passenger.getFlightNumber();

        assertEquals(expectedFlightNumber, actualFlightNumber);
    }

    @Test
    public void givenPassengerReservation_shouldBeTheSameFlightDate() {
        List<Passenger> passengerList = reservation.getPassengers();
        Passenger passenger = passengerList.get(0);
        Instant expectedFlightDate = reservation.getFlightDate();
        Instant actualFlightDate = passenger.getFlightDate();

        assertEquals(expectedFlightDate, actualFlightDate);
    }
}