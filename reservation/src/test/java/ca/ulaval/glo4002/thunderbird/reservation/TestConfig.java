package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class TestConfig {
    public static final int RESERVATION_NUMBER = 43525;
    public static final String RESERVATION_DATE = "2016-01-31";
    public static final String RESERVATION_CONFIRMATION = "A3833";
    public static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    public static final String FLIGHT_NUMBER = "AC1765";
    public static final Instant FLIGHT_DATE = ISO_INSTANT.parse("2016-09-06T13:00:00Z", Instant::from);

    public static final String FIRST_NAME = "Uncle";
    public static final String LAST_NAME = "Bob";
    public static final String PASSPORT_NUMBER = "2564-5424";
    public static final String SEAT_CLASS = "economy";
    public static final int AGE = 18;

    public static Passenger getDefaultPassenger() {
        return new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    public static Reservation getDefaultReservation() {
        Passenger passenger = getDefaultPassenger();
        ArrayList<Passenger> passengers = new ArrayList<>(Arrays.asList(passenger));

        return new Reservation(RESERVATION_NUMBER,
                RESERVATION_DATE,
                RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER,
                FLIGHT_DATE,
                PAYMENT_LOCATION,
                passengers);
    }
}