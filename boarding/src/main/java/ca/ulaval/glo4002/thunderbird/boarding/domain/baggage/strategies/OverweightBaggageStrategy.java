package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class OverweightBaggageStrategy {

    public void checkOverweight(Baggage baggage, Mass maxSize) {
        try {
            baggage.validateBaggage(Length.fromInches(Double.MAX_VALUE), maxSize);
        } catch (BaggageWeightInvalidException e) {
            baggage.addSpeciality(new Overweight());
        }
    }
}
