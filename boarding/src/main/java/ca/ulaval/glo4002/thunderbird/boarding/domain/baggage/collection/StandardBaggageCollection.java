package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Overweight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StandardBaggageCollection extends BaggageCollection {
    private static final String TYPE = "standard";
    private static final int BAGGAGES_LIMIT = 1;
    private static final float COST = 0;

    public StandardBaggageCollection(Passenger passenger) {
        this.passenger = passenger;
        collection = new ArrayList<>();
    }

    @Override
    public void addBaggage(Baggage baggage) {
        validateCollection(baggage);
        baggage.setBaggageCollection(this);
        collection.add(baggage);
    }

    @Override
    protected void validateCollection(Baggage baggage) {
        if (collection.size() >= BAGGAGES_LIMIT) {
            throw new BaggageAmountUnauthorizedException();
        }
        if (baggage.hasSpeciality(new Oversize())) {
            throw new BaggageDimensionInvalidException();
        }
        if (baggage.hasSpeciality(new Overweight())) {
            throw new BaggageWeightInvalidException();
        }
    }

    @Override
    public double calculateTotalCost() {
        return modifyPriceForVip(COST);
    }

    @Override
    public String getCollectionType() {
        return TYPE;
    }

    @Override
    public List<Baggage> getBaggages() {
        return collection;
    }
}
