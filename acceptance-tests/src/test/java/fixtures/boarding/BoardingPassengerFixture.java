package fixtures.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BoardingPassengerFixture extends HibernateBaseFixture {
    private static final double DELTA = 0.01;
    private static final int DIMENSION_VALUE = 11;
    private static final Length LINEAR_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE);
    private static final int WEIGHT_VALUE = 22;
    private static final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private static final Flight NO_FLIGHT = null;
    private static final String CHECKED_TYPE = "checked";

    private PassengerRepository repository;
    private BaggageFactory baggageFactory;

    public BoardingPassengerFixture() {
        this.repository = new HibernatePassengerRepository(new PassengerService());
        this.baggageFactory = new BaggageFactory();
    }

    public void givenAPassenger(UUID passengerHash, String flightNumber, Seat.SeatClass seatClass) {
        withEntityManager((tx) -> {
            Passenger passenger = new Passenger(passengerHash, seatClass, false, true, false, NO_FLIGHT);
            repository.savePassenger(passenger);
        });
    }

    public void givenABaggageForPassenger(UUID passengerHash) {
        NormalizedBaggageDTO baggageDTO = new NormalizedBaggageDTO(LINEAR_DIMENSION, WEIGHT, CHECKED_TYPE);
        addBaggageToPassenger(passengerHash, baggageDTO);
    }

    public void addBaggageToPassenger(UUID passengerHash, NormalizedBaggageDTO dto) {
        withEntityManager((tx) -> {
            Passenger passenger = repository.findByPassengerHash(passengerHash);
            Baggage baggage = baggageFactory.createBaggage(passenger, dto);
            passenger.addBaggage(baggage);
            repository.savePassenger(passenger);
        });
    }

    public void thenTotalBaggagePriceEquals(UUID passengerHash, float totalPriceExpected) {
        withEntityManager((tx) -> {
            Passenger passenger = repository.findByPassengerHash(passengerHash);
            double totalPriceActual = passenger.calculateBaggagesPrice();
            assertEquals(totalPriceExpected, totalPriceActual, DELTA);
        });
    }
}