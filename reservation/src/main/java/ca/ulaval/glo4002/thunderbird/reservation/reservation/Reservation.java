package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private int reservationNumber;
    @NotBlank
    private String flightNumber;
    private Instant flightDate;
    @NotBlank
    @JsonIgnore
    private String reservationDate;
    @NotBlank
    @JsonIgnore
    private String reservationConfirmation;
    @NotBlank
    @JsonIgnore
    private String paymentLocation;

    @Valid
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "reservation")
    @JsonManagedReference
    private List<Passenger> passengers;

    @JsonCreator
    public Reservation(@JsonProperty("reservation_number") int reservationNumber,
                       @JsonProperty("reservation_date") String reservationDate,
                       @JsonProperty("reservation_confirmation") String reservationConfirmation,
                       @JsonProperty("flight_number") String flightNumber,
                       @JsonProperty("flight_date") Instant flightDate,
                       @JsonProperty("payment_location") String paymentLocation,
                       @JsonProperty("passengers") ArrayList<Passenger> passengers) {
        this.reservationNumber = reservationNumber;
        this.reservationDate = reservationDate;
        this.reservationConfirmation = reservationConfirmation;
        this.paymentLocation = paymentLocation;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<>(passengers);
        this.passengers.forEach(passenger -> passenger.setReservation(this));
    }

    protected Reservation() {
        // for hibernate
    }

    public static Reservation findByReservationNumber(int reservationNumber) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        Reservation reservation = entityManager.find(Reservation.class, reservationNumber);

        if (reservation == null) {
            throw new ReservationNotFoundException(reservationNumber);
        }

        return reservation;
    }

    public void save() {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(this);
    }

    public int getId() {
        return reservationNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public Instant getFlightDate() {
        return flightDate;
    }
}