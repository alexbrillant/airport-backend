package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CollectionFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

public class HibernatePassengerRepositoryIntegrationTest {
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION = UUID.randomUUID();
    private static final UUID NOT_CHECKED_IN_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID CHECKED_IN_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID PASSENGER_UUID_WITH_NO_BAGGAGE = UUID.randomUUID();
    private static final UUID PASSENGER_UUID_WITH_BAGGAGE = UUID.randomUUID();
    private static final boolean IS_VIP = true;
    private static final Length LINEAR_DIMENSION_IN_MM = Length.fromMillimeters(10);
    private static final Mass WEIGHT_IN_KGS = Mass.fromKilograms(10);
    private static final boolean IS_CHECKED_IN = true;
    private static final boolean IS_CHILD = true;
    private static final Flight NO_FLIGHT = null;
    private static final String CHECKED = "checked";

    private final PassengerService passengerService = mock(PassengerService.class);
    private final PassengerRepository repository = new HibernatePassengerRepository(passengerService);
    private final Passenger updatePassenger = mock(Passenger.class);
    private final BaggageFactory baggageFactory = new BaggageFactory();

    @BeforeClass
    public static void setUpFlight() throws Exception {
        ServiceLocator.reset();
        ServiceLocator.registerSingleton(CollectionFactory.class, new CollectionFactory());
    }

    @Test
    public void whenSavingPassenger_shouldBeSavedCorrectly() {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, IS_VIP, IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);

        Passenger actualPassenger = repository.findByPassengerHash(VALID_PASSENGER_UUID);
        assertEquals(expectedPassenger.getHash(), actualPassenger.getHash());
    }

    @Test
    public void givenAPassengerPresentInReservation_whenGettingPassenger_shouldReturnThePassenger() {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION,
                Seat.SeatClass.ECONOMY, IS_VIP, IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        willReturn(expectedPassenger).given(passengerService).fetchPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        Passenger actualPassenger = repository.findByPassengerHash(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void givenPassengerWithBaggage_whenSavingThisPassenger_shouldSaveBaggagesCorrectly() {
        Passenger expectedPassenger = new Passenger(PASSENGER_UUID_WITH_BAGGAGE,
                Seat.SeatClass.ECONOMY, IS_VIP, IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);

        NormalizedBaggageDTO baggageDTO = new NormalizedBaggageDTO(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_KGS, CHECKED);

        Baggage baggage = baggageFactory.createBaggage(expectedPassenger, baggageDTO);
        expectedPassenger.addBaggage(baggage);

        repository.savePassenger(expectedPassenger);

        Passenger actualPassenger = repository.findByPassengerHash(PASSENGER_UUID_WITH_BAGGAGE);
        assertFalse(actualPassenger.getBaggages().isEmpty());
    }

    @Test
    public void givenSavedPassengerWithNoBaggage_whenAddingBaggages_shouldSavePassengerCorrectly() {
        Passenger expectedPassenger = new Passenger(PASSENGER_UUID_WITH_NO_BAGGAGE,
                Seat.SeatClass.ECONOMY, IS_VIP, IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);

        Passenger repoPassenger = repository.findByPassengerHash(PASSENGER_UUID_WITH_NO_BAGGAGE);
        NormalizedBaggageDTO baggageDTO = new NormalizedBaggageDTO(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_KGS, CHECKED);

        Baggage baggage = baggageFactory.createBaggage(repoPassenger, baggageDTO);
        repoPassenger.addBaggage(baggage);
        repository.savePassenger(repoPassenger);
        Passenger actualPassenger = repository.findByPassengerHash(PASSENGER_UUID_WITH_NO_BAGGAGE);

        assertFalse(actualPassenger.getBaggages().isEmpty());
    }

    @Test
    public void givenNonCheckedInAndSavedPassenger_whenGettingPassenger_shouldCheckForUpdates() {
        Passenger expectedPassenger = new Passenger(NOT_CHECKED_IN_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, IS_VIP, !IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);
        willReturn(updatePassenger).given(passengerService).fetchPassenger(NOT_CHECKED_IN_PASSENGER_UUID);
        repository.findByPassengerHash(NOT_CHECKED_IN_PASSENGER_UUID);

        verify(passengerService).fetchPassenger(NOT_CHECKED_IN_PASSENGER_UUID);
    }

    @Test
    public void givenNonCheckedInAndNotSavedPassenger_whenGettingPassenger_shouldCallServiceOnlyOnce() {
        Passenger expectedPassenger = new Passenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, IS_VIP, !IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        willReturn(expectedPassenger).given(passengerService).fetchPassenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID);

        repository.findByPassengerHash(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID);

        verify(passengerService, times(1)).fetchPassenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID);
    }

    @Test
    public void givenCheckedInAndSavedPassenger_whenGettingPassenger_shouldNotCheckForUpdates() {
        Passenger expectedPassenger = new Passenger(CHECKED_IN_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, IS_VIP, IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);

        repository.findByPassengerHash(CHECKED_IN_PASSENGER_UUID);

        verify(passengerService, never()).fetchPassenger(CHECKED_IN_PASSENGER_UUID);
    }

    @Test
    public void givenAPassengerCheckedInOnReservation_whenGettingPassenger_shouldCheckInPassenger() {
        UUID checkedInOnReservationPassengerHash = UUID.randomUUID();
        Passenger expectedPassenger = new Passenger(checkedInOnReservationPassengerHash,
                Seat.SeatClass.ECONOMY, IS_VIP, !IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);
        willReturn(true).given(updatePassenger).isCheckedIn();
        willReturn(updatePassenger).given(passengerService).fetchPassenger(checkedInOnReservationPassengerHash);

        Passenger actualPassenger = repository.findByPassengerHash(checkedInOnReservationPassengerHash);

        verify(passengerService, times(1)).fetchPassenger(checkedInOnReservationPassengerHash);
        assertTrue(actualPassenger.isCheckedIn());
    }

    @Test
    public void givenAPassengerNotCheckedInAnywhere_whenGettingPassenger_shouldNotCheckIn() {
        Passenger expectedPassenger = new Passenger(NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, IS_VIP, !IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);
        willReturn(false).given(updatePassenger).isCheckedIn();
        willReturn(updatePassenger).given(passengerService).fetchPassenger(NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID);

        Passenger actualPassenger = repository.findByPassengerHash(NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID);

        assertFalse(actualPassenger.isCheckedIn());
    }

    @Test
    public void givenAPassengerCheckedInOnReservation_whenGettingPassenger_shouldSavePassenger() {
        UUID checkedInOnReservationPassengerHash = UUID.randomUUID();
        Passenger expectedPassenger = new Passenger(checkedInOnReservationPassengerHash,
                Seat.SeatClass.ECONOMY, IS_VIP, !IS_CHECKED_IN, !IS_CHILD, NO_FLIGHT);
        repository.savePassenger(expectedPassenger);
        willReturn(true).given(updatePassenger).isCheckedIn();
        willReturn(updatePassenger).given(passengerService).fetchPassenger(checkedInOnReservationPassengerHash);

        repository.findByPassengerHash(checkedInOnReservationPassengerHash);
        Passenger actualPassenger = repository.findByPassengerHash(checkedInOnReservationPassengerHash);

        verify(passengerService, times(1)).fetchPassenger(checkedInOnReservationPassengerHash);
        assertTrue(actualPassenger.isCheckedIn());
    }
}