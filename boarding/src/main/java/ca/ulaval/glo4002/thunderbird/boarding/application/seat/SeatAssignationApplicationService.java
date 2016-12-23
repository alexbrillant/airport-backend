package ca.ulaval.glo4002.thunderbird.boarding.application.seat;

import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory.AssignMode;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations.SeatAssignationDTO;

public class SeatAssignationApplicationService {
    private final SeatAssignationAssembler seatAssignationRequestAssembler;
    private final SeatAssignationStrategyFactory seatAssignationStrategyFactory;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public SeatAssignationApplicationService() {
        this.seatAssignationRequestAssembler = new SeatAssignationAssembler();
        this.seatAssignationStrategyFactory = new SeatAssignationStrategyFactory();
        this.flightRepository = ServiceLocator.resolve(FlightRepository.class);
        this.passengerRepository = ServiceLocator.resolve(PassengerRepository.class);
    }

    public Seat assignSeat(SeatAssignationDTO request) {
        Passenger passenger = passengerRepository.findByPassengerHash(request.passengerHash);

        SeatAssignationStrategy strategy = getSeatAssignationStrategy(request);
        passenger.assignSeat(strategy);

        flightRepository.saveFlight(passenger.getFlight());
        passengerRepository.savePassenger(passenger);
        return passenger.getSeat();
    }

    private SeatAssignationStrategy getSeatAssignationStrategy(SeatAssignationDTO request) {
        AssignMode assignMode = seatAssignationRequestAssembler.getMode(request);
        return seatAssignationStrategyFactory.getStrategy(assignMode);
    }
}