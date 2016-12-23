package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class BusinessBaggage extends CheckedBaggage{
    public static final Mass MAX_WEIGHT = Mass.fromKilograms(30);
    public static final Length MAX_LENGTH = Length.fromCentimeters(158);

    public BusinessBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public BusinessBaggage() {
        // For hibernate
    }
}
