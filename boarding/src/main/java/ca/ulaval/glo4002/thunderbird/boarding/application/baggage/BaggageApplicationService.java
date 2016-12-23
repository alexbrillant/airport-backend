package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.NoMoreSpaceInFlightException;
import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageNormalizer;

import java.util.UUID;

public class BaggageApplicationService {
    private PassengerRepository repository;
    private BaggageFactory baggageFactory;
    private RegisterBaggageNormalizer registerBaggageNormalizer;

    public BaggageApplicationService(PassengerRepository repository,
                                     BaggageFactory baggageFactory,
                                     RegisterBaggageNormalizer registerBaggageNormalizer) {
        this.repository = repository;
        this.baggageFactory = baggageFactory;
        this.registerBaggageNormalizer = registerBaggageNormalizer;
    }

    public BaggageApplicationService() {
        this.repository = ServiceLocator.resolve(PassengerRepository.class);
        this.baggageFactory = ServiceLocator.resolve(BaggageFactory.class);
        this.registerBaggageNormalizer = new RegisterBaggageNormalizer();
    }

    public UUID registerBaggage(UUID passengerHash, RegisterBaggageDTO registerBaggageDTO) {
        Passenger passenger = getPassenger(passengerHash);
        NormalizedBaggageDTO normalizedBaggageDTO = registerBaggageNormalizer.normalizeBaggageDTO(registerBaggageDTO);
        Baggage baggage = baggageFactory.createBaggage(passenger, normalizedBaggageDTO);
        Flight flight = passenger.getFlight();
        if (!flight.hasSpaceFor(baggage)) {
            throw new NoMoreSpaceInFlightException();
        }
        passenger.addBaggage(baggage);
        repository.savePassenger(passenger);

        return baggage.getId();
    }

    public Passenger getPassenger(UUID passengerHash) {
        Passenger passenger = repository.findByPassengerHash(passengerHash);
        if (!passenger.isCheckedIn()) {
            throw new PassengerNotCheckedInException();
        }
        return passenger;
    }
}