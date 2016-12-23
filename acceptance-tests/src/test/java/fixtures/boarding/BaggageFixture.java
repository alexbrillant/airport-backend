package fixtures.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class BaggageFixture {
    public NormalizedBaggageDTO createBaggageDTO(Length length, Mass mass, String checkedType) {
        return new NormalizedBaggageDTO(length, mass, checkedType);
    }
}