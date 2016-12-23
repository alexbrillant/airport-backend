package ca.ulaval.glo4002.thunderbird.boarding.util.units;

import org.junit.Test;

import static org.junit.Assert.*;

public class MassTest {

    private static final double DELTA = 0.001;
    private static final double MASS_IN_POUNDS = 1;
    private static final double MASS_IN_GRAMS = 453.592;
    private static final double MASS_IN_KILOGRAMS = 0.453592;
    private static final double SMALLER_MASS_IN_GRAMS = MASS_IN_GRAMS - 1;
    private static final double BIGGER_MASS_IN_GRAMS = MASS_IN_GRAMS + 1;

    private Mass mass;
    private double actualMass;

    @Test
    public void whenConvertingPoundsToGrams_shouldReturnConvertedValue() {
        mass = Mass.fromPounds(MASS_IN_POUNDS);
        actualMass = mass.toGrams();

        assertEquals(MASS_IN_GRAMS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingGramsToGrams_shouldReturnSameValue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        actualMass = mass.toGrams();

        assertEquals(MASS_IN_GRAMS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingKiloGramsToGrams_shouldReturnConvertedValue() {
        mass = Mass.fromKilograms(MASS_IN_KILOGRAMS);
        actualMass = mass.toGrams();

        assertEquals(MASS_IN_GRAMS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingGramsToPounds_shouldReturnConvertedValue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        actualMass = mass.toPounds();

        assertEquals(MASS_IN_POUNDS, actualMass, DELTA);
    }

    @Test
    public void whenConvertingGramsToKiloGrams_shouldReturnConvertedValue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        actualMass = mass.toKilograms();

        assertEquals(MASS_IN_KILOGRAMS, actualMass, DELTA);
    }

    @Test
    public void whenComparingWithLengthWithSameMillimeters_shouldReturnTrue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass secondMass = Mass.fromGrams(MASS_IN_GRAMS);
        boolean areEquals = mass.equals(secondMass);

        assertTrue(areEquals);
    }

    @Test
    public void whenComparingWithOtherObject_shouldReturnFalse() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Object obj = new Object();
        boolean areEquals = mass.equals(obj);

        assertFalse(areEquals);
    }

    @Test
    public void givenALength_whenCheckingIfIsSuperiorToSmallerLength_shouldReturnTrue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass smallerLength = Mass.fromGrams(SMALLER_MASS_IN_GRAMS);

        boolean isSuperior = mass.isSuperiorTo(smallerLength);

        assertTrue(isSuperior);
    }

    @Test
    public void givenALength_whenCheckingIfIsSuperiorToSameLength_shouldReturnFalse() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass sameLength = Mass.fromGrams(MASS_IN_GRAMS);

        boolean isSuperior = mass.isSuperiorTo(sameLength);

        assertFalse(isSuperior);
    }

    @Test
    public void givenALength_whenCheckingIfIsSuperiorToBiggerLength_shouldReturnFalse() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass sameLength = Mass.fromGrams(BIGGER_MASS_IN_GRAMS);

        boolean isSuperior = mass.isSuperiorTo(sameLength);

        assertFalse(isSuperior);
    }

    @Test
    public void givenAMass_whenCheckingIfIsInferiorOrEqualToEqualMass_shouldReturnTrue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass sameMass = Mass.fromGrams(MASS_IN_GRAMS);

        boolean isInferiorTo = mass.isInferiorOrEqualTo(sameMass);

        assertTrue(isInferiorTo);
    }

    @Test
    public void givenAMass_whenCheckingIfIsInferiorOrEqualToSmallerMass_shouldReturnFalse() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass smallerMass = Mass.fromGrams(SMALLER_MASS_IN_GRAMS);

        boolean isInferiorTo = mass.isInferiorOrEqualTo(smallerMass);

        assertFalse(isInferiorTo);
    }

    @Test
    public void givenAMass_whenCheckingIfInferiorOrEqualToBiggerMass_shouldReturnTrue() {
        mass = Mass.fromGrams(MASS_IN_GRAMS);
        Mass biggerMass = Mass.fromGrams(BIGGER_MASS_IN_GRAMS);

        boolean isInferiorTo = mass.isInferiorOrEqualTo(biggerMass);

        assertTrue(isInferiorTo);
    }

    @Test
    public void givenTwoMasses_whenAdding_shouldNotModifyOriginals() {
        Mass massA = Mass.fromGrams(MASS_IN_GRAMS);
        Mass massB = Mass.fromGrams(MASS_IN_GRAMS);

        massA.add(massB);

        assertEquals(MASS_IN_GRAMS, massA.toGrams(), DELTA);
        assertEquals(MASS_IN_GRAMS, massA.toGrams(), DELTA);
    }

    @Test
    public void givenTwoMasses_whenAdding_resultShouldBeTheAdditionOfTheTwo() {
        Mass massA = Mass.fromGrams(MASS_IN_GRAMS);
        Mass massB = Mass.fromGrams(MASS_IN_GRAMS);

        Mass resultingMass = massA.add(massB);

        assertEquals(MASS_IN_GRAMS + MASS_IN_GRAMS, resultingMass.toGrams(), DELTA);
    }
}
