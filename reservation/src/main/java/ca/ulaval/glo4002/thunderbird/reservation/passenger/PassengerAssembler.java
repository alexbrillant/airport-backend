package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class PassengerAssembler {
    public PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO passengerDTO = new PassengerDTO();

        passengerDTO.passengerHash = passenger.getId().toString();
        passengerDTO.seatClass = passenger.getSeatClass();
        passengerDTO.flightNumber = passenger.getFlightNumber();
        passengerDTO.flightDate = ISO_INSTANT.format(passenger.getFlightDate());
        passengerDTO.vip = passenger.isVip();
        passengerDTO.checkedIn = passenger.isCheckedIn();
        passengerDTO.child = passenger.isChild();

        return passengerDTO;
    }
}
