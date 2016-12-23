package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import javax.persistence.EntityManager;
import java.util.UUID;

public class HibernatePassengerRepository implements PassengerRepository {

    private PassengerService passengerService;

    public HibernatePassengerRepository(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Override
    public Passenger findByPassengerHash(UUID passengerHash) {
        Passenger passenger = getPassengerFromHibernate(passengerHash);

        if (passenger == null) {
            passenger = passengerService.fetchPassenger(passengerHash);
            savePassenger(passenger);
        } else {
            passenger = updatePassenger(passenger);
        }

        return passenger;
    }

    private Passenger updatePassenger(Passenger passenger) {
        if (!passenger.isCheckedIn()) {
            Passenger updatePassenger = passengerService.fetchPassenger(passenger.getHash());
            if (updatePassenger.isCheckedIn()) {
                passenger.setCheckedIn();
            }
        }

        return passenger;
    }

    private Passenger getPassengerFromHibernate(UUID passengerHash) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        return entityManager.find(Passenger.class, passengerHash);
    }

    @Override
    public void savePassenger(Passenger passenger) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(passenger);
    }
}
