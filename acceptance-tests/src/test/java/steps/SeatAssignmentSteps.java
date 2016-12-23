package steps;

import contexts.boarding.BoardingContext;
import contexts.reservation.ReservationContext;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;
import fixtures.boarding.FlightFixture;
import fixtures.boarding.RestSeatAssigmentFixture;
import fixtures.reservation.RestCheckinFixture;
import fixtures.reservation.RestReservationFixture;

import java.time.Instant;
import java.util.UUID;

public class SeatAssignmentSteps implements Fr {

    private static final String FLIGHT_NUMBER = "QK-918";
    private static final String FLIGHT_DATE = "2016-10-15T11:41:00Z";
    public static final String RANDOM = "RANDOM";
    private RestSeatAssigmentFixture seatAssignmentFixture;
    private RestReservationFixture reservationFixture;
    private RestCheckinFixture checkinFixture;
    private FlightFixture flightFixture;
    private UUID passengerHash;

    @Before
    public void beforeScenario() throws Throwable {
        new ReservationContext().apply();
        new BoardingContext().apply();
        seatAssignmentFixture = new RestSeatAssigmentFixture();
        flightFixture = new FlightFixture();
        checkinFixture = new RestCheckinFixture();
        reservationFixture = new RestReservationFixture();
    }

    public SeatAssignmentSteps() {
        Étantdonné("^un passage Alice ayant une réservation à bord d'un vol$", () -> {
            passengerHash = reservationFixture.givenAPassengerWithAReservation(FLIGHT_NUMBER, FLIGHT_DATE);
        });

        Étantdonné("^que des places sont disponibles sur ce vol$", () -> {
            flightFixture.createFlight(FLIGHT_NUMBER, Instant.parse(FLIGHT_DATE));
        });

        Étantdonné("^qu'elle a fait son checkin$", () -> {
            checkinFixture.givenPassengerCheckin(passengerHash);
        });

        Quand("^elle procède à l'assignation de siège en choisissant le mode aléatoire$", () -> {
            seatAssignmentFixture.whenAssignSeat(passengerHash, RANDOM);
        });

        Alors("^un siège lui a été assigné à bord de ce vol$", () -> {
            seatAssignmentFixture.thenSeatHasBeenAsigned();
        });
    }
}