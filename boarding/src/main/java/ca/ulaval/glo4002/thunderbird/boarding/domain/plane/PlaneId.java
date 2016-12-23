package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PlaneId implements Serializable {
    private String model;

    public PlaneId(String model) {
        this.model = model;
    }

    public PlaneId() {
        // for hibernate
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}