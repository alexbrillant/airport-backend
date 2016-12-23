package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.Optional;
import java.util.stream.Stream;

public class RandomSeatAssignationStrategy extends SeatAssignationStrategy {
    @Override
    protected Optional<Seat> applyStrategy(Stream<Seat> filteredSeats) {
        return filteredSeats.findAny();
    }
}