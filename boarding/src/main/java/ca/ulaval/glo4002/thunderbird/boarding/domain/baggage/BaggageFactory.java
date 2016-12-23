package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Sport;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies.OversizeBaggageStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies.OverweightBaggageStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;

public class BaggageFactory {
    private static final String CHECKED = "checked";
    private static final String STANDARD = "standard";
    private static final String PERSONAL = "personal";
    private static final String MEDICAL = "medical";
    private static final String SPORT = "sport";
    private final OverweightBaggageStrategy overweightStrategy;
    private final OversizeBaggageStrategy oversizeStrategy;

    private CheckedBaggageFactory checkedBaggageFactory = new CheckedBaggageFactory();

    public BaggageFactory() {
        this.overweightStrategy = new OverweightBaggageStrategy();
        this.oversizeStrategy = new OversizeBaggageStrategy();
    }

    public Baggage createBaggage(Passenger passenger, NormalizedBaggageDTO dto) {
        switch (dto.type) {
            case CHECKED:
                return checkedBaggageFactory.createCheckedBaggage(passenger, dto);
            case MEDICAL:
                MedicalBaggage medicalBaggage = new MedicalBaggage(dto.length, dto.mass, MEDICAL);
                overweightStrategy.checkOverweight(medicalBaggage, MedicalBaggage.MAXIMUM_WEIGHT);
                oversizeStrategy.checkOversize(medicalBaggage, MedicalBaggage.MAXIMUM_LENGTH);
                return medicalBaggage;
            case PERSONAL:
                PersonalBaggage personalBaggage = new PersonalBaggage(dto.length, dto.mass, PERSONAL);
                overweightStrategy.checkOverweight(personalBaggage, PersonalBaggage.MAXIMUM_WEIGHT);
                oversizeStrategy.checkOversize(personalBaggage, PersonalBaggage.MAXIMUM_LENGTH);
                return personalBaggage;
            case STANDARD:
                StandardBaggage standardBaggage = new StandardBaggage(dto.length, dto.mass, STANDARD);
                oversizeStrategy.checkOversize(standardBaggage, StandardBaggage.MAXIMUM_LENGTH);
                overweightStrategy.checkOverweight(standardBaggage, StandardBaggage.MAXIMUM_WEIGHT);
                return standardBaggage;
            case SPORT:
                Baggage sportBaggage = checkedBaggageFactory.createCheckedBaggage(passenger, dto);
                sportBaggage.addSpeciality(new Sport());
                sportBaggage.removeSpeciality(new Oversize());
                return sportBaggage;
            default:
                throw new NoSuchStrategyException("Unknown baggage type : " + dto.type);
        }
    }

}
