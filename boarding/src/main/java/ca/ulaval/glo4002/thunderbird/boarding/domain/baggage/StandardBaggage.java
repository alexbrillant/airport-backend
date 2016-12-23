package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;

@Entity
public class StandardBaggage extends Baggage {
    private static final String TYPE = "standard";
    private static final double PRICE = 30;
    public static final Mass MAXIMUM_WEIGHT = Mass.fromKilograms(10);
    public static final Length MAXIMUM_LENGTH = Length.fromCentimeters(118);



    public StandardBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public StandardBaggage() {
        // For hibernate
    }

    @Override
    public double getPrice() {
        return PRICE;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
