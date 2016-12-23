package ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.ElementNotFoundException;

import java.io.Serializable;

public class ReservationNotFoundException extends ElementNotFoundException implements Serializable {
    public ReservationNotFoundException() {
        super("reservation not found");
    }

    public ReservationNotFoundException(int id) {
        super("reservation not found with id " + id);
    }
}