package ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions;

import java.io.Serializable;
import java.util.UUID;

public class PassengerAlreadyCheckedInException extends RuntimeException implements Serializable {
    public PassengerAlreadyCheckedInException(UUID id) {
        super(String.format("passenger with id %s already checked in", id));
    }
}