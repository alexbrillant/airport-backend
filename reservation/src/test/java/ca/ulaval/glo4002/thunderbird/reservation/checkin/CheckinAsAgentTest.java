package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

public class CheckinAsAgentTest {
    private static final String AGENT_ID = "agentId";
    private static final UUID PASSENGER_HASH = new UUID(1L, 2L);
    private static final Instant TODAYS_DATE = Instant.parse("2016-09-06T13:00:00Z");
    private static final boolean NOT_VIP = false;

    private Checkin checkinAsAgent;
    private Passenger passenger;
    private Reservation reservation;

    @Before
    public void setup() {
        reservation = mock(Reservation.class);
        passenger = mock(Passenger.class);
        willReturn(PASSENGER_HASH).given(passenger).getId();
        willReturn(reservation).given(passenger).getReservation();
        checkinAsAgent = new Checkin(PASSENGER_HASH, AGENT_ID, NOT_VIP) {
            @Override
            public Passenger getPassenger() {
                return passenger;
            }
        };
    }

    @Test
    public void shouldNotBeSelfCheckin() {
        assertFalse(checkinAsAgent.isSelfCheckin());
    }

    @Test
    public void whenCompletingCheckin_shouldCheckinAndSavePassenger() {
        checkinAsAgent.completeCheckin(TODAYS_DATE);

        verify(reservation, never()).getFlightDate();
        verify(passenger).checkin(NOT_VIP);
        verify(passenger).save();
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenPassengerWithoutReservation_whenCompletingCheckin_shouldNotCompleteCheckin() {
        willReturn(null).given(passenger).getReservation();

        checkinAsAgent.completeCheckin(TODAYS_DATE);
    }
}