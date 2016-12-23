package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CheckinValidationTest {
    private static final UUID PASSENGER_HASH = new UUID(1L, 2L);
    private static final boolean NOT_VIP = false;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenValidCheckin_shouldReturnAnId() {
        Checkin checkin = new Checkin(PASSENGER_HASH, Checkin.SELF, NOT_VIP);

        assertNotNull(checkin.getId());
    }

    @Test
    public void givenValidCheckin_whenValidating_shouldNotHaveErrors() {
        Checkin checkin = new Checkin(PASSENGER_HASH, Checkin.SELF, NOT_VIP);

        Set<ConstraintViolation<Checkin>> constraintViolations = validator.validate(checkin);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenCheckinWithoutPassengerHash_whenValidating_shouldHaveAnError() {
        UUID invalidPassengerHash = null;
        Checkin checkin = new Checkin(invalidPassengerHash, Checkin.SELF, NOT_VIP);

        Set<ConstraintViolation<Checkin>> constraintViolations = validator.validate(checkin);

        assertEquals(1, constraintViolations.size());
        Object actualInvalidValue = constraintViolations.iterator().next().getInvalidValue();
        assertEquals(invalidPassengerHash, actualInvalidValue);
    }

    @Test
    public void givenCheckinWithoutName_whenValidating_shouldHaveAnError() {
        String invalidName = null;
        Checkin checkin = new Checkin(PASSENGER_HASH, invalidName, NOT_VIP);

        Set<ConstraintViolation<Checkin>> constraintViolations = validator.validate(checkin);

        assertEquals(1, constraintViolations.size());
        Object actualInvalidValue = constraintViolations.iterator().next().getInvalidValue();
        assertEquals(invalidName, actualInvalidValue);
    }

    @Test
    public void givenCheckinWithEmptyName_whenValidating_shouldHaveAnError() {
        String invalidName = "  ";
        Checkin checkin = new Checkin(PASSENGER_HASH, invalidName, NOT_VIP);

        Set<ConstraintViolation<Checkin>> constraintViolations = validator.validate(checkin);

        assertEquals(1, constraintViolations.size());
        Object actualInvalidValue = constraintViolations.iterator().next().getInvalidValue();
        assertEquals(invalidName, actualInvalidValue);
    }

    @Test
    public void givenCheckinWithInvalidVip_whenValidate_shouldHaveAnError() {
        Boolean invalidVip = null;
        Checkin checkin = new Checkin(PASSENGER_HASH, Checkin.SELF, invalidVip);

        Set<ConstraintViolation<Checkin>> constraintViolations = validator.validate(checkin);

        assertEquals(1, constraintViolations.size());
        Object actualInvalidValue = constraintViolations.iterator().next().getInvalidValue();
        assertEquals(invalidVip, actualInvalidValue);
    }
}