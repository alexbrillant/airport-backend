package helpers;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MeasureConverter {
    private static final String KG = "kg";
    private static final String CM = "cm";
    private static final String REGEX_TO_SPLIT_NUMBERS_AND_DIGITS = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
    private static final int UNIT_INDEX = 1;
    private static final int VALUE_INDEX = 0;

    public static Length getLengthFromString(String lengthAsString) {
        String[] lengthValueUnitTable = lengthAsString.split(REGEX_TO_SPLIT_NUMBERS_AND_DIGITS);
        String unit = lengthValueUnitTable[UNIT_INDEX];
        double value = Double.valueOf(lengthValueUnitTable[VALUE_INDEX]);
        switch (unit) {
            case CM:
                return Length.fromCentimeters(value);
            default:
                throw new NotImplementedException();
        }
    }

    public static Mass getMassFromString(String massAsString) {
        String[] lengthValueUnitTable = massAsString.split(REGEX_TO_SPLIT_NUMBERS_AND_DIGITS);
        String unit = lengthValueUnitTable[UNIT_INDEX];
        double value = Double.valueOf(lengthValueUnitTable[VALUE_INDEX]);
        switch (unit) {
            case KG:
                return Mass.fromKilograms(value);
            default:
                throw new NotImplementedException();
        }
    }
}
