package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory.AssignMode;

public class SeatAssignationAssembler {
    private static final String RANDOM_MODE = "RANDOM";
    private static final String CHEAPEST_MODE = "CHEAPEST";
    private static final String LANDSCAPE_MODE = "LANDSCAPE";
    private static final String LEGS_MODE = "LEGS";

    public AssignMode getMode(SeatAssignationDTO request) {
        switch (request.mode) {
            case RANDOM_MODE:
                return AssignMode.RANDOM;
            case CHEAPEST_MODE:
                return AssignMode.CHEAPEST;
            case LANDSCAPE_MODE:
                return AssignMode.LANDSCAPE;
            case LEGS_MODE:
                return AssignMode.LEGS;
            default:
                throw new NoSuchStrategyException(request.mode);
        }
    }
}
