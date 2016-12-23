package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CollectionFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystemFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.HibernatePlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;

public class DemoContext implements Context {
    @Override
    public void apply() {
        registerPlaneRepository();
        registerFlightRepository();
        registerPassengerRepository();
        registerBaggageFactories();
    }

    private void registerPlaneRepository() {
        PlaneService planeService = new PlaneServiceGlo3000();
        PlaneRepository planeRepository = new HibernatePlaneRepository(planeService);

        ServiceLocator.registerSingleton(PlaneRepository.class, planeRepository);
    }

    private void registerFlightRepository() {
        AMSSystem amsSystem = new AMSSystemFactory().create();
        PlaneRepository planeRepository = ServiceLocator.resolve(PlaneRepository.class);
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);

        ServiceLocator.registerSingleton(FlightRepository.class, flightRepository);
    }

    private void registerBaggageFactories() {
        BaggageFactory baggageFactory = new BaggageFactory();
        
        ServiceLocator.registerSingleton(BaggageFactory.class, baggageFactory);
        ServiceLocator.registerSingleton(CollectionFactory.class, new CollectionFactory());
    }

    private void registerPassengerRepository() {
        PassengerService service = new PassengerService();
        PassengerRepository passengerRepository = new HibernatePassengerRepository(service);

        ServiceLocator.registerSingleton(PassengerRepository.class, passengerRepository);
    }
}