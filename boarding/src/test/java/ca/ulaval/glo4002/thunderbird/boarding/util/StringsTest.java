package ca.ulaval.glo4002.thunderbird.boarding.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringsTest {
    @Test
    public void givenANullString_whenCheckingIfIsNullOrEmpty_shouldBeTrue() {
        String string = null;

        Boolean result = Strings.isNullOrEmpty(string);

        assertTrue(result);
    }

    @Test
    public void givenAnEmptyString_whenCheckingIfIsNullOrEmpty_shouldBeTrue() {
        String string = "  ";

        Boolean result = Strings.isNullOrEmpty(string);

        assertTrue(result);
    }

    @Test
    public void givenNotEmptyString_whenCheckingIfIsNullOrEmpty_shouldBeFalse() {
        String string = "NotEmpty";

        Boolean result = Strings.isNullOrEmpty(string);

        assertFalse(result);
    }
}