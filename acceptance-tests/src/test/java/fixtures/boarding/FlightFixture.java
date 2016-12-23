package fixtures.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;

import java.time.Instant;

public class FlightFixture extends HibernateBaseFixture {
    private FlightRepository flightRepository;

    public FlightFixture() {
        flightRepository = ServiceLocator.resolve(FlightRepository.class);
    }

    public void createFlight(String flightNumber, Instant flightDate) {
        withEntityManager((tx) -> {
            flightRepository.getFlight(flightNumber, flightDate);
        });
    }
}
