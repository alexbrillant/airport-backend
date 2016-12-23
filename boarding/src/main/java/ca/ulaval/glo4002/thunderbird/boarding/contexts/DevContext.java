package ca.ulaval.glo4002.thunderbird.boarding.contexts;

import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CollectionFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.*;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat.SeatClass;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.HibernatePlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneServiceGlo3000;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DevContext implements Context {
    public static final boolean IS_CHECKED_IN = true;
    public static final boolean IS_VIP = true;
    public static final boolean IS_CHILD = true;
    public static final String CHECKED = "checked";
    public static Passenger EXISTENT_BOARDING_PASSENGER;

    private static final String FLIGHT_NUMBER = "QK-918";
    private static final Instant FLIGHT_DATE = Instant.ofEpochMilli(123456);

    @Override
    public void apply() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        registerPlaneRepository();
        registerFlightRepository();
        registerPassengerRepository();
        registerBaggageFactories();
        fillDatabase();
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
        AMSSystem amsSystem = new AMSSystemFactory().create();
        PlaneRepository planeRepository = ServiceLocator.resolve(PlaneRepository.class);
        FlightRepository flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);

        ServiceLocator.registerSingleton(FlightRepository.class, flightRepository);
    }

    private void registerPassengerRepository() {
        PassengerService service = new PassengerService();
        PassengerRepository passengerRepository = new HibernatePassengerRepository(service);

        ServiceLocator.registerSingleton(PassengerRepository.class, passengerRepository);
    }

    private void fillDatabase() {
        Plane plane = createPlane();
        Flight flight = createFlight(plane);
        Passenger passenger = createPassenger(flight);

        PlaneRepository planeRepository = ServiceLocator.resolve(PlaneRepository.class);
        FlightRepository flightRepository = ServiceLocator.resolve(FlightRepository.class);
        PassengerRepository passengerRepository = ServiceLocator.resolve(PassengerRepository.class);

        planeRepository.savePlane(plane);
        flightRepository.saveFlight(flight);
        passengerRepository.savePassenger(passenger);
        EXISTENT_BOARDING_PASSENGER = passenger;
    }

    private Passenger createPassenger(Flight flight) {
        Passenger passenger = new Passenger(UUID.randomUUID(), SeatClass.ECONOMY,
                !IS_VIP, IS_CHECKED_IN, !IS_CHILD, flight);

        Length length1 = Length.fromMillimeters(500);
        Mass mass1 = Mass.fromGrams(1000);
        Length length2 = Length.fromMillimeters(200);
        Mass mass2 = Mass.fromGrams(500);

        BaggageFactory baggageFactory = ServiceLocator.resolve(BaggageFactory.class);

        Baggage baggage1 = baggageFactory.createBaggage(passenger, new NormalizedBaggageDTO(length1, mass1, CHECKED));
        Baggage baggage2 = baggageFactory.createBaggage(passenger, new NormalizedBaggageDTO(length2, mass2, CHECKED));
        passenger.addBaggage(baggage1);
        passenger.addBaggage(baggage2);

        return passenger;
    }

    private Plane createPlane() {
        PlaneId planeId = new PlaneId("dash-8");
        List<Seat> seats = Arrays.asList(createSeat());
        return new Plane(planeId, 1, 1000, seats);
    }

    private Flight createFlight(Plane plane) {
        FlightId flightId = new FlightId(FLIGHT_NUMBER, FLIGHT_DATE);
        return new Flight(flightId, plane);
    }

    private Seat createSeat() {
        int rowNumber = 1;
        String seatName = "name";
        int legRoom = 1;
        boolean hasWindow = false;
        boolean hasClearView = false;
        double price = 1;
        SeatClass seatClass = SeatClass.ECONOMY;
        boolean isExitRow = false;
        return new Seat(rowNumber, seatName, legRoom, hasWindow, hasClearView, price, seatClass, isExitRow);
    }
}
