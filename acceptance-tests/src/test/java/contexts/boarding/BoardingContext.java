package contexts.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.Context;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CollectionFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.HibernatePlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import fixtures.boarding.FakeAMSSystem;
import runners.BoardingServerRunner;

public class BoardingContext implements Context {

    @Override
    public void apply() {
        ServiceLocator.reset();
        registerServices();
        new BoardingServerRunner().startJettyServer();
    }

    private void registerServices() {
        ServiceLocator.registerSingleton(PassengerRepository.class, new HibernatePassengerRepository(new PassengerService()));
        registerBaggageFactories();
        registerPlaneRepository();
        registerFlightRepository();
    }

    private void registerBaggageFactories() {
        BaggageFactory baggageFactory = new BaggageFactory();
        ServiceLocator.registerSingleton(BaggageFactory.class, baggageFactory);
        ServiceLocator.registerSingleton(CollectionFactory.class, new CollectionFactory());
    }

    private void registerPlaneRepository() {
        PlaneService planeService = new PlaneServiceGlo3000();
        PlaneRepository planeRepository = new HibernatePlaneRepository(planeService);
        ServiceLocator.registerSingleton(PlaneRepository.class, planeRepository);
    }

    private void registerFlightRepository() {
        PlaneRepository planeRepository = ServiceLocator.resolve(PlaneRepository.class);
        FakeAMSSystem amsSystem = new FakeAMSSystem();
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);
        ServiceLocator.registerSingleton(FlightRepository.class, flightRepository);
    }
}