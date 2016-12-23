package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.Entity;

@Entity
public class Overweight extends Speciality {
    private final String SPECIALITY_NAME = "OVERWEIGHT";
    private final float SPECIALITY_FEE = 1.1f;

    public Overweight() {
        id = SPECIALITY_NAME;
    }

    @Override
    public float getSpecialityFee() {
        return SPECIALITY_FEE;
    }

    @Override
    public String getSpecialityName() {
        return SPECIALITY_NAME;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Overweight && EqualsBuilder.reflectionEquals(this, obj);
    }
}
