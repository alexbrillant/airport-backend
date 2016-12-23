package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;

import java.time.Instant;
import java.util.HashMap;

public class InMemoryFlightRepository implements FlightRepository {
    private HashMap<FlightId, Flight> flights = new HashMap<>();
    private AMSSystem amsSystem;
    private PlaneService planeService;

    public InMemoryFlightRepository(AMSSystem amsSystem, PlaneService planeService) {
        this.amsSystem = amsSystem;
        this.planeService = planeService;
    }

    public Flight getFlight(String flightNumber, Instant flightDate) {
        FlightId flightId = new FlightId(flightNumber, flightDate);
        Flight flight = flights.get(flightId);
        if (flight == null) {
            flight = fetchFlight(flightNumber, flightDate);
        }

        return flight;
    }

    private Flight fetchFlight(String flightNumber, Instant flightDate) {
        FlightId flightId = new FlightId(flightNumber, flightDate);
        String modelID = amsSystem.getPlaneModel(flightNumber);
        Plane plane = planeService.getPlane(new PlaneId(modelID));

        return new Flight(flightId, plane);
    }

    @Override
    public void saveFlight(Flight flight) {
        FlightId flightId = flight.getId();
        flights.put(flightId, flight);
    }
}