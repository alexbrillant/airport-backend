package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;

import java.time.Instant;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class PassengerAssembler {
    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";

    private final FlightRepository flightRepository;

    public PassengerAssembler() {
        this.flightRepository = ServiceLocator.resolve(FlightRepository.class);
    }

    public PassengerAssembler(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Passenger toDomain(PassengerDTO passengerDTO) {
        Seat.SeatClass seatClass = getSeatClassFromString(passengerDTO.seatClass);
        UUID passengerHash = passengerDTO.passengerHash;
        Instant flightDate = Instant.parse(passengerDTO.flightDate);
        String flightNumber = passengerDTO.flightNumber;
        Boolean isVip = passengerDTO.vip;
        Boolean isCheckedIn = passengerDTO.checkedIn;
        Boolean isChild = passengerDTO.child;
        Flight flight = flightRepository.getFlight(flightNumber, flightDate);

        return new Passenger(passengerHash, seatClass, isVip, isCheckedIn, isChild, flight);
    }

    private Seat.SeatClass getSeatClassFromString(String source) {
        source = source.toLowerCase();
        switch (source) {
            case ECONOMY:
                return Seat.SeatClass.ECONOMY;
            case BUSINESS:
                return Seat.SeatClass.BUSINESS;
            default:
                throw new IllegalFieldWebException();

        }
    }
}