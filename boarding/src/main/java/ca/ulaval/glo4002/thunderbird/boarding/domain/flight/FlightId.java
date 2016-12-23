package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Instant;

@Embeddable
public class FlightId implements Serializable {
    private String flightNumber;
    private Instant flightDate;

    public FlightId(String flightNumber, Instant flightDate) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
    }

    public FlightId() {
        // for hibernate
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