package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies.OversizeBaggageStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies.OverweightBaggageStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

public class CheckedBaggageFactory {

    private OversizeBaggageStrategy oversizeStrategy;
    private OverweightBaggageStrategy overweightStrategy;
    private static final String CHECKED = "checked";

    public CheckedBaggageFactory() {
        this.overweightStrategy = new OverweightBaggageStrategy();
        this.oversizeStrategy = new OversizeBaggageStrategy();
    }

    public Baggage createCheckedBaggage(Passenger passenger, NormalizedBaggageDTO dto) {

        switch (passenger.getSeatClass()) {
            case ECONOMY:
                EconomicBaggage economicBaggage = new EconomicBaggage(dto.length, dto.mass, CHECKED);
                overweightStrategy.checkOverweight(economicBaggage, EconomicBaggage.MAX_WEIGHT);
                oversizeStrategy.checkOversize(economicBaggage, EconomicBaggage.MAX_LENGTH);
                return economicBaggage;
            case BUSINESS:
                BusinessBaggage businessBaggage = new BusinessBaggage(dto.length, dto.mass, CHECKED);
                overweightStrategy.checkOverweight(businessBaggage, BusinessBaggage.MAX_WEIGHT);
                oversizeStrategy.checkOversize(businessBaggage, BusinessBaggage.MAX_LENGTH);
                return businessBaggage;
            default:
                throw new NoSuchStrategyException("unknown seatClass" + passenger.getSeatClass());
        }
    }
}
