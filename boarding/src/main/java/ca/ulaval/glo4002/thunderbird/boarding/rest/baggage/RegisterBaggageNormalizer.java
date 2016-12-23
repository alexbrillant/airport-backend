package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class RegisterBaggageNormalizer {
    private static final String KILOGRAMS = "kg";
    private static final String POUNDS = "lbs";
    private static final String CENTIMETERS = "cm";
    private static final String INCHES = "po";

    public NormalizedBaggageDTO normalizeBaggageDTO(RegisterBaggageDTO request) {
        Length length = getDimension(request);
        Mass weight = getWeight(request);
        String type = request.type;

        return new NormalizedBaggageDTO(length, weight, type);
    }

    private Mass getWeight(RegisterBaggageDTO request) {
        switch (request.weightUnit) {
            case KILOGRAMS:
                return Mass.fromKilograms(request.weight);
            case POUNDS:
                return Mass.fromPounds(request.weight);
            default:
                throw new IllegalFieldWebException();
        }
    }

    private Length getDimension(RegisterBaggageDTO request) {
        switch (request.linearDimensionUnit) {
            case CENTIMETERS:
                return Length.fromCentimeters(request.linearDimension);
            case INCHES:
                return Length.fromInches(request.linearDimension);
            default:
                throw new IllegalFieldWebException();
        }
    }
}
