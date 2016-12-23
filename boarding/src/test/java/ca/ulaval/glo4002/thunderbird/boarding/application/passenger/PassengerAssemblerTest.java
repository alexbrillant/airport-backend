package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;
import org.junit.Before;
import org.junit.Test;
import sun.nio.cs.ext.ISCII91;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class PassengerAssemblerTest {
    private static final UUID PASSENGER_HASH = UUID.randomUUID();
    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";
    private static final String INVALID_SEAT_CLASS = "";
    private static final String FLIGHT_NUMBER = "flight_number";
    private static final String FLIGHT_DATE = Instant.now().toString();
    private static final boolean IS_VIP = true;
    private static final boolean IS_CHECKED_IN = true;
    private static final boolean IS_CHILD = true;

    private final Flight flight = mock(Flight.class);
    private final FlightRepository flightRepository = mock(FlightRepository.class);
    private final PassengerAssembler passengerAssembler = new PassengerAssembler(flightRepository);
    private PassengerDTO passengerDTO;
    private Passenger actualPassenger;

    @Before
    public void setUp() {
        willReturn(flight).given(flightRepository).getFlight(FLIGHT_NUMBER, Instant.parse(FLIGHT_DATE));
    }

    @Test
    public void givenPassengerDTO_whenConvertingToDomain_ShouldReturnCorrectPassenger() {
        PassengerDTO passengerDTO = new PassengerDTO(PASSENGER_HASH, ECONOMY, FLIGHT_DATE,
                FLIGHT_NUMBER, IS_VIP, IS_CHECKED_IN, !IS_CHILD);

        actualPassenger = passengerAssembler.toDomain(passengerDTO);

        assertEquals(PASSENGER_HASH, actualPassenger.getHash());
        assertEquals(Seat.SeatClass.ECONOMY, actualPassenger.getSeatClass());
        assertEquals(IS_VIP, actualPassenger.isVip());
        assertEquals(IS_CHECKED_IN, actualPassenger.isCheckedIn());
        assertEquals(!IS_CHILD, actualPassenger.isChild());
        assertSame(flight, actualPassenger.getFlight());
    }

    @Test
    public void givenPassengerDTOWithBusinessSeat_whenConvertingToDomain_shouldReturnBusinessSeatClass() {
        passengerDTO = new PassengerDTO(PASSENGER_HASH, BUSINESS, FLIGHT_DATE,
                FLIGHT_NUMBER, IS_VIP, IS_CHECKED_IN, IS_CHILD);

        actualPassenger = passengerAssembler.toDomain(passengerDTO);

        assertEquals(Seat.SeatClass.BUSINESS, actualPassenger.getSeatClass());
    }

    @Test
    public void givenPassengerDTOWithEconomySeat_whenConvertingToDomain_shouldReturnEconomySeatClass() {
        passengerDTO = new PassengerDTO(PASSENGER_HASH, ECONOMY, FLIGHT_DATE,
                FLIGHT_NUMBER, IS_VIP, IS_CHECKED_IN, IS_CHILD);

        actualPassenger = passengerAssembler.toDomain(passengerDTO);

        assertEquals(Seat.SeatClass.ECONOMY, actualPassenger.getSeatClass());
    }

    @Test (expected = IllegalFieldWebException.class)
    public void givenPassegnerDTOWithInvalidSeatClass_whenConvertingToDomain_shouldThrowException() {
        passengerDTO = new PassengerDTO(PASSENGER_HASH, INVALID_SEAT_CLASS, FLIGHT_DATE,
                FLIGHT_NUMBER, IS_VIP, IS_CHECKED_IN, IS_CHILD);

        passengerAssembler.toDomain(passengerDTO);
    }
}