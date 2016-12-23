package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterBaggageNormalizerTest {

    private static final String INCH_UNIT = "po";
    private static final int LENGTH_IN_INCHES = 123;
    private static final String KG_UNIT = "kg";
    private static final int WEIGHT_IN_KG = 832;
    private static final String TYPE_CHECKED = "checked";
    private static final double DELTA = 0.01;
    private static final String CM_UNIT = "cm";
    private static final int LENGTH_IN_CM = 1234;
    private static final String LBS_UNIT = "lbs";
    private static final int WEIGHT_IN_LBS = 928;
    private static final String TYPE_SPORT = "sport";

    @Test
    public void givenARegisterBaggageDTO_whenNormalizingFromInAndKg_shouldAValidNormalizedDTOBeCreated() {
        RegisterBaggageDTO registerBaggageDTO =
                new RegisterBaggageDTO(INCH_UNIT, LENGTH_IN_INCHES, KG_UNIT, WEIGHT_IN_KG, TYPE_CHECKED);
        RegisterBaggageNormalizer registerBaggageNormalizer = new RegisterBaggageNormalizer();

        NormalizedBaggageDTO normalisedDTO = registerBaggageNormalizer.normalizeBaggageDTO(registerBaggageDTO);

        assertEquals(LENGTH_IN_INCHES,normalisedDTO.length.toInches(),DELTA);
        assertEquals(WEIGHT_IN_KG,normalisedDTO.mass.toKilograms(),DELTA);
        assertEquals(TYPE_CHECKED,normalisedDTO.type);
    }

    @Test
    public void givenARegisterBaggageDTO_whenNormalizingFromCmAndLbs_shouldAValidNormalizedDTOBeCreated() {
        RegisterBaggageDTO registerBaggageDTO =
                new RegisterBaggageDTO(CM_UNIT, LENGTH_IN_CM, LBS_UNIT, WEIGHT_IN_LBS, TYPE_SPORT);
        RegisterBaggageNormalizer registerBaggageNormalizer = new RegisterBaggageNormalizer();

        NormalizedBaggageDTO normalisedDTO = registerBaggageNormalizer.normalizeBaggageDTO(registerBaggageDTO);

        assertEquals(LENGTH_IN_CM,normalisedDTO.length.toCentimeters(),DELTA);
        assertEquals(WEIGHT_IN_LBS,normalisedDTO.mass.toPounds(),DELTA);
        assertEquals(TYPE_SPORT,normalisedDTO.type);
    }
}