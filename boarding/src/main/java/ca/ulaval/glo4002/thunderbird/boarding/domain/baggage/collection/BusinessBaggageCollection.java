package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.Entity;

@Entity
public class BusinessBaggageCollection extends CheckedBaggageCollection {
    private static final int FREE_BAGGAGE_COUNT = 2;
    private static final int BAGGAGE_COUNT_LIMIT = 3;
    private static final Mass WEIGHT_LIMIT = Mass.fromKilograms(30);
    private static final Length DIMENSION_LIMIT = Length.fromCentimeters(158);

    public BusinessBaggageCollection(Passenger passenger) {
        super(passenger);
    }

    protected BusinessBaggageCollection() {
        //for hibernate
    }

    @Override
    protected int getBaggageCountLimit() {
        return passenger.isVip() ? BAGGAGE_COUNT_LIMIT + 1 : BAGGAGE_COUNT_LIMIT;
    }

    @Override
    protected Mass getWeightLimit() {
        return WEIGHT_LIMIT;
    }

    @Override
    protected Length getDimensionLimit() {
        return DIMENSION_LIMIT;
    }

    @Override
    protected int getFreeBaggageCount() {
        return FREE_BAGGAGE_COUNT;
    }

    @Override
    protected void validateBaggage(Baggage baggage) {
        //No Validation
    }

    @Override
    public double calculateTotalCost() {
        float cost = 0;
        int baggageNumber = 1;
        for (Baggage currentBaggage : collection) {
            if (baggageNumber > FREE_BAGGAGE_COUNT) {
                cost += currentBaggage.getPrice();
            }
            baggageNumber++;
        }
        return modifyPriceForVip(cost);
    }
}