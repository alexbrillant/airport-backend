package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.internal.util.compare.EqualsHelper;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Speciality {

    @Id
    protected String id;

    public abstract float getSpecialityFee();
    public abstract String getSpecialityName();
    public abstract boolean equals(Object obj);

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

}
