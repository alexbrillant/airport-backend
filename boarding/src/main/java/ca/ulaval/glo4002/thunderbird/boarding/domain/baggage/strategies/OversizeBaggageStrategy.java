package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class OversizeBaggageStrategy {

    public void checkOversize(Baggage baggage, Length maxLength) {
        try {
            baggage.validateBaggage(maxLength, Mass.fromKilograms(Double.MAX_VALUE));
        } catch (BaggageDimensionInvalidException e) {
            baggage.addSpeciality(new Oversize());
        }
    }
}
