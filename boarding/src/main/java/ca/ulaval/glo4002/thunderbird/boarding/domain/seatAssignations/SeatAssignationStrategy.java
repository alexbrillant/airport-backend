package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions.NoMoreSeatAvailableException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class SeatAssignationStrategy {
    public Seat findBestSeat(List<Seat> availableSeats, Passenger passenger) {
        Stream<Seat> filteredSeats = availableSeats.stream();

        filteredSeats = filterSeatClass(filteredSeats, passenger);
        filteredSeats = filterExistRows(filteredSeats, passenger);

        Optional<Seat> bestSeat = applyStrategy(filteredSeats);
        if (!bestSeat.isPresent()) {
            throw new NoMoreSeatAvailableException();
        }

        return bestSeat.get();
    }

    protected abstract Optional<Seat> applyStrategy(Stream<Seat> filteredSeats);

    private Stream<Seat> filterSeatClass(Stream<Seat> seats, Passenger passenger) {
        return seats.filter(seat -> Objects.equals(seat.getSeatClass(), passenger.getSeatClass()));
    }

    private Stream<Seat> filterExistRows(Stream<Seat> seats, Passenger passenger) {
        if (passenger.isChild()) {
            return seats.filter(seat -> !seat.isExitRow());
        }
        return seats;
    }
}