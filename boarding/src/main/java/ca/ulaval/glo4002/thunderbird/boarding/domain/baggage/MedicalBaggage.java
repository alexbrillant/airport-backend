package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;

@Entity
public class MedicalBaggage extends StandardBaggage {
    private static final String TYPE = "medical";
    private static final double PRICE = 0;


    public MedicalBaggage(Length linearDimension, Mass weight, String type) {
        super(linearDimension, weight, type);
    }

    public MedicalBaggage() {
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
