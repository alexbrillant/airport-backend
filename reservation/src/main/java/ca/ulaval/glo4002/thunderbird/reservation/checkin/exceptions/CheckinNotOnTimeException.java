package ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions;

import java.io.Serializable;

public class CheckinNotOnTimeException extends RuntimeException implements Serializable {
    public CheckinNotOnTimeException() {
        super();
    }
}