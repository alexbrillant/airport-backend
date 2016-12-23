package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerAlreadyCheckedInException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="Reservation_Passenger")
public class Passenger {
    private static final int AGE_MAJORITY = 21;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private final UUID passengerHash = UUID.randomUUID();

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String passportNumber;

    @NotBlank
    private String seatClass;

    @JsonIgnore
    private int age;

    @JsonIgnore
    private boolean isCheckedIn = false;

    @JsonIgnore
    private boolean isVip = false;

    @ManyToOne
    @JoinColumn(name = "reservationNumber")
    @JsonBackReference
    private Reservation reservation;

    @JsonCreator
    public Passenger(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName,
                     @JsonProperty("age") int age, @JsonProperty("passport_number") String passportNumber,
                     @JsonProperty("seat_class") String seatClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
    }

    protected Passenger() {
        // for hibernate
    }

    public static Passenger findByPassengerHash(UUID passengerHash) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        Passenger passenger = entityManager.find(Passenger.class, passengerHash);

        if (passenger == null) {
            throw new PassengerNotFoundException(passengerHash);
        }

        return passenger;
    }

    public UUID getId() {
        return passengerHash;
    }

    @JsonProperty("child")
    public boolean isChild() {
        return age < AGE_MAJORITY;
    }

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(this);
    }

    public String getFlightNumber() {
        return reservation.getFlightNumber();
    }

    public Instant getFlightDate() {
        return reservation.getFlightDate();
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public boolean isVip() {
        return isVip;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void checkin(boolean vip) {
        if (isCheckedIn) {
            throw new PassengerAlreadyCheckedInException(passengerHash);
        }
        isCheckedIn = true;
        isVip = vip;
    }

    public String getSeatClass() {
        return seatClass;
    }
}