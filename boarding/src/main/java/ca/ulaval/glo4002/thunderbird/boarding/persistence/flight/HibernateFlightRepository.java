package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;

import javax.persistence.EntityManager;
import java.time.Instant;

public class HibernateFlightRepository implements FlightRepository {
    private final AMSSystem amsSystem;
    private final PlaneRepository planeRepository;

    public HibernateFlightRepository(AMSSystem amsSystem, PlaneRepository planeRepository) {
        this.amsSystem = amsSystem;
        this.planeRepository = planeRepository;
    }

    @Override
    public Flight getFlight(String flightNumber, Instant flightDate) {
        Flight flight = getFlightFromDB(flightNumber, flightDate);
        if (flight == null) {
            flight = createFlight(flightNumber, flightDate);
            saveFlight(flight);
        }
        return flight;
    }

    @Override
    public void saveFlight(Flight flight) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(flight);
    }

    private Flight getFlightFromDB(String flightNumber, Instant flightDate) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        FlightId flightId = new FlightId(flightNumber, flightDate);
        return entityManager.find(Flight.class, flightId);
    }

    private Flight createFlight(String flightNumber, Instant flightDate) {
        FlightId flightId = new FlightId(flightNumber, flightDate);
        String modelId = amsSystem.getPlaneModel(flightNumber);
        Plane plane = planeRepository.getPlane(new PlaneId(modelId));

        return new Flight(flightId, plane);
    }
}