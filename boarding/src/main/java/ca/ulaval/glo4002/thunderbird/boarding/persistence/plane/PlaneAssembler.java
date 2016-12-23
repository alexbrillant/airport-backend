package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.List;

public class PlaneAssembler {
    private final SeatsAssembler seatsAssembler;

    public PlaneAssembler() {
        seatsAssembler = new SeatsAssembler();
    }

    public Plane toDomain(PlaneDTO dto, SeatsDTO seatsDto) {
        PlaneId planeId = new PlaneId(dto.model);
        List<Seat> seats = seatsAssembler.toDomain(seatsDto);
        return new Plane(planeId, dto.numberOfSeats, dto.cargoWeight, seats);
    }
}