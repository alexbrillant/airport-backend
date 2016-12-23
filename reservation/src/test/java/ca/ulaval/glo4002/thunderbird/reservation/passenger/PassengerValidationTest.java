package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PassengerValidationTest {
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
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenAFirstNameToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(inputToValidate, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        Set<ConstraintViolation<Passenger>> constraintViolations = validator.validate(passenger);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenALastNameToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, inputToValidate, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        Set<ConstraintViolation<Passenger>> constraintViolations = validator.validate(passenger);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenAPassportNumberToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, inputToValidate, SEAT_CLASS);

        Set<ConstraintViolation<Passenger>> constraintViolations = validator.validate(passenger);

        assertEquals(isValid, constraintViolations.isEmpty());
    }

    @Test
    public void givenASeatClassToValidate_whenValidating_shouldBeRightResult() {
        Passenger passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, inputToValidate);

        Set<ConstraintViolation<Passenger>> constraintViolations = validator.validate(passenger);

        assertEquals(isValid, constraintViolations.isEmpty());
    }
}