package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.ElementNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;

public class PlaneNotFoundException extends ElementNotFoundException {
    public PlaneNotFoundException(PlaneId planeId) {
        super("plane " + planeId.getModel());
    }
}
