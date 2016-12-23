package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

public class SeatAssignationStrategyFactory {
    public SeatAssignationStrategy getStrategy(AssignMode mode) {
        switch (mode) {
            case RANDOM:
                return new RandomSeatAssignationStrategy();
            case CHEAPEST:
                return new CheapestSeatAssignationStrategy();
            case LANDSCAPE:
                return new LandscapeSeatAssignationStrategy();
            case LEGS:
                return new MostLegRoomSeatAssignationStrategy();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }

    public enum AssignMode {
        RANDOM,
        CHEAPEST,
        LANDSCAPE,
        LEGS
    }
}