package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class NormalizedBaggageDTO {

    public Length length;
    public Mass mass;
    public String type;

    public NormalizedBaggageDTO(Length length, Mass mass, String type) {
        this.length = length;
        this.mass = mass;
        this.type = type;
    }
}
