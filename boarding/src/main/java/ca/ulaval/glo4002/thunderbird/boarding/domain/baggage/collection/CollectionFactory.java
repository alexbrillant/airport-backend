package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

public class CollectionFactory {
    private static final String STANDARD = "standard";
    private static final String PERSONAL = "personal";
    private static final String MEDICAL = "medical";
    private static final String CHECKED = "checked";

    public BaggageCollection createCollection(Passenger passenger, String type) {

        switch (type) {
            case STANDARD:
                return new StandardBaggageCollection(passenger);
            case PERSONAL:
                return new PersonalBaggageCollection(passenger);
            case MEDICAL:
                return new MedicalBaggageCollection(passenger);
            case CHECKED:
                switch (passenger.getSeatClass()) {
                    case BUSINESS:
                        return new BusinessBaggageCollection(passenger);
                    case ECONOMY:
                        return new EconomicBaggageCollection(passenger);
                    default:
                        throw new NoSuchStrategyException("Unknown seatClass " + passenger.getSeatClass());
                }
            default:
                throw new NoSuchStrategyException("Unknown baggage type " + type);
        }
    }
}
