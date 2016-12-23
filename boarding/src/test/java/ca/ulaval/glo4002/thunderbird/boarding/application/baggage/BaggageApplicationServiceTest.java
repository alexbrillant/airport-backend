package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.NoMoreSpaceInFlightException;
import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageNormalizer;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaggageApplicationServiceTest {
    private static final UUID PASSENGER_HASH = UUID.randomUUID();
    private static final UUID BAGGAGE_ID = UUID.randomUUID();
    private Passenger passenger;
    private NormalizedBaggageDTO normalizedBaggageDTO;
    private RegisterBaggageDTO registerBaggageDTO;
    private RegisterBaggageNormalizer registerBaggageNormalizer;
    private PassengerRepository passengerRepository;
    private BaggageFactory baggageFactory;
    private Baggage baggage;
    private Flight flight;

    @Before
    public void setUp() throws Exception {
        passenger = mock(Passenger.class);
        normalizedBaggageDTO = mock(NormalizedBaggageDTO.class);
        registerBaggageNormalizer = mock(RegisterBaggageNormalizer.class);
        registerBaggageDTO = mock(RegisterBaggageDTO.class);
        passengerRepository = mock(PassengerRepository.class);
        baggageFactory = mock(BaggageFactory.class);
        baggage = mock(Baggage.class);
        flight = mock(Flight.class);
        Flight flight = mock(Flight.class);
        willReturn(true).given(flight).hasSpaceFor(any(Baggage.class));
        willReturn(flight).given(passenger).getFlight();
        willReturn(baggage).given(baggageFactory).createBaggage(passenger,normalizedBaggageDTO);
        willReturn(BAGGAGE_ID).given(baggage).getId();
        willReturn(normalizedBaggageDTO).given(registerBaggageNormalizer).normalizeBaggageDTO(registerBaggageDTO);
    }

    @Test
    public void givenCheckedInPassenger_whenRegisteringBaggage_shouldAddBaggageToPassenger() {
        setPassengerCheckedInInRepo(true);
        BaggageApplicationService applicationService = new BaggageApplicationService(passengerRepository, baggageFactory, registerBaggageNormalizer);

        UUID actualResult = applicationService.registerBaggage(PASSENGER_HASH, registerBaggageDTO);

        verify(passenger).addBaggage(baggage);
        assertEquals(BAGGAGE_ID, actualResult);
    }

    @Test(expected = PassengerNotCheckedInException.class)
    public void givenNotCheckedInPassenger_whenRegisteringBaggge_shouldThrowException() {
        setPassengerCheckedInInRepo(false);
        BaggageApplicationService applicationService = new BaggageApplicationService(passengerRepository, baggageFactory, registerBaggageNormalizer);

        applicationService.registerBaggage(PASSENGER_HASH, registerBaggageDTO);
    }

    @Test(expected = NoMoreSpaceInFlightException.class)
    public void givenAFlightWithNoSpaceForBaggages_whenAddingBaggage_shouldThrowNoMoreSpaceInFlight() {
        setPassengerCheckedInInRepo(true);
        willReturn(false).given(flight).hasSpaceFor(any(Baggage.class));
        willReturn(flight).given(passenger).getFlight();
        BaggageApplicationService applicationService = new BaggageApplicationService(passengerRepository, baggageFactory, registerBaggageNormalizer);

        applicationService.registerBaggage(PASSENGER_HASH, registerBaggageDTO);
    }

    private void setPassengerCheckedInInRepo(boolean checkedIn) {
        willReturn(checkedIn).given(passenger).isCheckedIn();
        willReturn(passenger).given(passengerRepository).findByPassengerHash(PASSENGER_HASH);
    }
}
