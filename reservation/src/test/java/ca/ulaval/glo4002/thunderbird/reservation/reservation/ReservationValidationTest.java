package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ReservationValidationTest {
    private static final int RESERVATION_NUMBER = 37353;
    private static final String RESERVATION_DATE = "2016-01-31";
    private static final String RESERVATION_CONFIRMATION = "A3833";
    private static final String PAYMENT_LOCATION = "/payments/da39a3ee5e6b4b0d3255bfef95601890afd80709";
    private static final String FLIGHT_NUMBER = "AC1765";
    private static final Instant FLIGHT_DATE = ISO_INSTANT.parse("2016-09-06T13:00:00Z", Instant::from);

    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private static Validator validator;

    @Parameter
    public String inputToValidate;
    @Parameter(value = 1)
    public boolean isValid;

    @Parameters(name = "''{0}'' => {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"validString", true}, {null, false}, {"   ", false}
        });
    }

    @BeforeClass
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenAReservationDateToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        Reservation reservation = new Reservation(RESERVATION_NUMBER, inputToValidate, RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER, FLIGHT_DATE, PAYMENT_LOCATION, new ArrayList<>(Arrays.asList(passenger)));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenAReservationConfirmationToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        Reservation reservation = new Reservation(RESERVATION_NUMBER, RESERVATION_DATE, inputToValidate,
                FLIGHT_NUMBER, FLIGHT_DATE, PAYMENT_LOCATION, new ArrayList<>(Arrays.asList(passenger)));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenAFlightNumberToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        Reservation reservation = new Reservation(RESERVATION_NUMBER, RESERVATION_DATE, RESERVATION_CONFIRMATION,
                inputToValidate, FLIGHT_DATE, PAYMENT_LOCATION, new ArrayList<>(Arrays.asList(passenger)));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenAPaymentLocationToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        Reservation reservation = new Reservation(RESERVATION_NUMBER, RESERVATION_DATE, RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER, FLIGHT_DATE, inputToValidate, new ArrayList<>(Arrays.asList(passenger)));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenAPassengerToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(inputToValidate, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        Reservation reservation = new Reservation(RESERVATION_NUMBER, RESERVATION_DATE, RESERVATION_CONFIRMATION,
                FLIGHT_NUMBER, FLIGHT_DATE, PAYMENT_LOCATION, new ArrayList<>(Arrays.asList(passenger)));

        Set<ConstraintViolation<Reservation>> constraintViolations = validator.validate(reservation);

        assertEquals(isValid, constraintViolations.isEmpty());
    }
}