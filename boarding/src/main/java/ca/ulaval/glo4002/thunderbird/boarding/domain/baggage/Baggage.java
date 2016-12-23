package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.BaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Baggage {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    protected UUID baggageHash;
    protected float price;
    protected String type;

    @OneToMany(cascade = CascadeType.ALL)
    protected Set<Speciality> specialities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    protected BaggageCollection baggageCollection;

    @Embedded
    protected Length linearDimension;

    @Embedded
    protected Mass weight;

    public Baggage(Length linearDimension, Mass weight, String type) {
        this.specialities = new HashSet<>();
        this.baggageHash = UUID.randomUUID();
        this.linearDimension = linearDimension;
        this.weight = weight;
        this.type = type;
    }

    protected Baggage() {
        // for hibernate
    }

    public abstract double getPrice();

    public abstract boolean isChecked();

    public abstract String getType();

    public void setBaggageCollection(BaggageCollection baggageCollection) {
        this.baggageCollection = baggageCollection;
    }

    public UUID getId() {
        return baggageHash;
    }

    public Mass getWeight() {
        return weight;
    }

    public Length getDimension() {
        return linearDimension;
    }

    public boolean hasSpeciality(Speciality speciality) {
        for (Speciality specs : specialities) {
            if (specs.getSpecialityName().equals(speciality.getSpecialityName())) {
                return true;
            }
        }
        return false;
    }

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    public void removeSpeciality(Speciality speciality) {
        specialities.remove(speciality);
    }

    public void validateBaggage(Length maximumLinearDimension, Mass maximumWeight) {
        if (weight.isSuperiorTo(maximumWeight)) {
            throw new BaggageWeightInvalidException();
        }
        if (linearDimension.isSuperiorTo(maximumLinearDimension)) {
            throw new BaggageDimensionInvalidException();
        }
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