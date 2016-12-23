package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.*;

public class SeatsAssembler {
    public List<Seat> toDomain(SeatsDTO dto) {
        HashMap<Integer, Seat.SeatClass> rowClasses = getClassesByRow(dto.classes);
        Set<Integer> exitRows = getExitRowSet(dto.exitRows);
        List<Seat> seats = new ArrayList<>(dto.seats.length);

        for (SeatsDTO.SeatDTO seatDTO : dto.seats) {
            Seat.SeatClass seatClass = rowClasses.get(seatDTO.row);
            boolean isExitRow = exitRows.contains(seatDTO.row);
            Seat seat = new Seat(seatDTO.row, seatDTO.seat, seatDTO.legroom, seatDTO.window, seatDTO.clearView,
                    seatDTO.price, seatClass, isExitRow);
            seats.add(seat);
        }

        return seats;
    }

    private HashMap<Integer, Seat.SeatClass> getClassesByRow(SeatsDTO.PriceClassDTO[] classes) {
        HashMap<Integer, Seat.SeatClass> rowClasses = new HashMap<>();
        for (SeatsDTO.PriceClassDTO priceClass : classes) {
            for (int row : priceClass.rows) {
                String name = priceClass.name.toUpperCase().replace("-", "_");
                rowClasses.put(row, Seat.SeatClass.valueOf(name));
            }
        }
        return rowClasses;
    }

    private Set<Integer> getExitRowSet(int[] exitRows) {
        Set<Integer> rowSet = new HashSet<>();

        for (int row : exitRows) {
            rowSet.add(row);
        }

        return rowSet;
    }
}
