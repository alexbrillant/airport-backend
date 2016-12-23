package ca.ulaval.glo4002.thunderbird.reservation.contexts;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class DevContext implements Context {
    public final static int EXISTENT_RESERVATION_NUMBER = 100;
    public static UUID EXISTENT_PASSENGER_HASH;
    public static UUID NON_CHECKIN_PASSENGER_HASH;
    public static final int NON_CHECKIN_RESERVATION_NUMBER = 200;
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final int AGE = 100;
    public static final String SEAT_CLASS = "economy";
    public static final String FLIGHT_NUMBER = "AC1765";
    public static final String FLIGHT_DATE_STRING = "2016-09-06T13:00:00Z";

    @Override
    public void apply() {
        fillDatabase();
    }

    private void fillDatabase() {
        Passenger passenger = createPassenger();
        Reservation reservation = createReservation(passenger, EXISTENT_RESERVATION_NUMBER);
        EXISTENT_PASSENGER_HASH = passenger.getId();

        Passenger nonCheckinPassenger = createPassenger();
        Reservation nonCheckinReservation = createReservation(nonCheckinPassenger, NON_CHECKIN_RESERVATION_NUMBER);
        NON_CHECKIN_PASSENGER_HASH = nonCheckinPassenger.getId();

        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        reservation.save();
        nonCheckinReservation.save();
    }

    private Passenger createPassenger() {
        String passportNumber = "passportNumber";

        return new Passenger(FIRST_NAME, LAST_NAME, AGE, passportNumber, SEAT_CLASS);
    }

    private Reservation createReservation(Passenger passenger, int reservation_number) {
        String date = "2016-01-31";
        String confirmation = "A3833";
        String payment = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
        Instant flightDate = ISO_INSTANT.parse(FLIGHT_DATE_STRING, Instant::from);
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        return new Reservation(reservation_number, date, confirmation, FLIGHT_NUMBER, flightDate, payment, passengers);
    }
}