package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;

import java.util.UUID;

public interface PassengerRepository {
    Passenger findByPassengerHash(UUID passengerHash);

    void savePassenger(Passenger passenger) throws RepositorySavingException;
}
