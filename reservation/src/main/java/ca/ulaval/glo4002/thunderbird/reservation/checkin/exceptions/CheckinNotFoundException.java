package ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.ElementNotFoundException;

import java.io.Serializable;
import java.util.UUID;

public class CheckinNotFoundException extends ElementNotFoundException implements Serializable {
    public CheckinNotFoundException(UUID id) {
        super("checkin with id " + id);
    }
}