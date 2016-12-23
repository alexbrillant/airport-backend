package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CheckedBaggageCollection extends BaggageCollection {
    protected static final String TYPE = "checked";

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<Baggage> collection = new ArrayList<>();

    public CheckedBaggageCollection(Passenger passenger) {
        this.passenger = passenger;
    }

    protected CheckedBaggageCollection() {
        //for hibernate
    }

    public List<Baggage> getBaggages() {
        return collection;
    }

    @Override
    public void addBaggage(Baggage baggage) {
        validateBaggage(baggage);
        validateCollection(baggage);
        baggage.setBaggageCollection(this);
        collection.add(baggage);
    }

    protected abstract int getBaggageCountLimit();

    protected abstract Mass getWeightLimit();

    protected abstract Length getDimensionLimit();

    protected abstract int getFreeBaggageCount();

    protected abstract void validateBaggage(Baggage baggage);

    @Override
    public String getCollectionType() {
        return TYPE;
    }

    public void validateCollection(Baggage baggage) {
        if (collection.size() >= getBaggageCountLimit())
            throw new BaggageAmountUnauthorizedException();
    }

    public abstract double calculateTotalCost();

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}