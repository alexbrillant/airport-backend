package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import java.time.Instant;

public interface FlightRepository {
    Flight getFlight(String flightNumber, Instant flightDate);

    void saveFlight(Flight flight);
}
