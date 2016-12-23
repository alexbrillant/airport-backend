package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
public class PassengerBaggageCollections {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID checkedBaggageId;

    @OneToOne(fetch = FetchType.EAGER)
    protected Passenger passenger;

    @OneToMany(mappedBy = "passengerBaggageCollections", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    protected Set<BaggageCollection> collection;

    public PassengerBaggageCollections(Passenger passenger) {
        this.checkedBaggageId = passenger.getHash();
        this.passenger = passenger;
        collection = new HashSet<>();
    }

    protected PassengerBaggageCollections() {
        //For Hibernate
    }

    public void addBaggage(Baggage baggage) {
        Optional<BaggageCollection> baggageCollection = getBaggageCollectionForBaggage(baggage);

        if (baggageCollection.isPresent()) {
            baggageCollection.get().addBaggage(baggage);
        } else {
            addNewCollection(baggage);
        }
    }

    private void addNewCollection(Baggage baggage) {
        CollectionFactory collectionFactory = new CollectionFactory();
        BaggageCollection newCollection = collectionFactory.createCollection(passenger, baggage.getType());

        newCollection.addBaggage(baggage);
        newCollection.setPassengerBaggageCollections(this);

        collection.add(newCollection);
    }

    private Optional<BaggageCollection> getBaggageCollectionForBaggage(Baggage baggage) {
        return collection.stream()
                .filter(x -> x.getCollectionType()
                .equals(baggage.getType()))
                .findFirst();
    }

    public double calculateTotalPrice() {
        return collection.stream()
                .map(BaggageCollection::calculateTotalCost)
                .reduce(0.0d, (a, b) -> a + b);
    }

    public Set<Baggage> getBaggages() {
        Set<Baggage> baggageList = new HashSet<>();

        for (BaggageCollection baggageCollection : collection) {
            baggageList.addAll(baggageCollection.getBaggages());
        }
        return baggageList;
    }

    public Mass calculateBaggagesMass() {
        Mass mass = Mass.fromGrams(0);
        for (BaggageCollection baggages : collection) {
            for (Baggage baggage : baggages.getBaggages()) {
                mass = mass.add(baggage.getWeight());
            }
        }
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}
