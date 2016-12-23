package ca.ulaval.glo4002.thunderbird.boarding.util.units;

import javax.persistence.Embeddable;

@Embeddable
public class Length {
    private static final double MILLIMETERS_IN_A_CENTIMETER = 10;
    private static final double MILLIMETERS_IN_A_INCH = 25.4;

    private double millimeters;

    private Length(double millimeters) {
        this.millimeters = millimeters;
    }

    protected Length() {
        // for hibernate
    }

    public static Length fromMillimeters(double millimeters) {
        return new Length(millimeters);
    }

    public static Length fromCentimeters(double centimeters) {
        return new Length(centimeters * MILLIMETERS_IN_A_CENTIMETER);
    }

    public static Length fromInches(double inches) {
        return new Length(inches * MILLIMETERS_IN_A_INCH);
    }

    public double toCentimeters() {
        return millimeters / MILLIMETERS_IN_A_CENTIMETER;
    }

    public double toInches() {
        return millimeters / MILLIMETERS_IN_A_INCH;
    }

    public double toMillimeters() {
        return millimeters;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Length)) {
            return false;
        }

        Length other = (Length) obj;
        return millimeters == other.millimeters;
    }

    public boolean isSuperiorTo(Length length) {
        return millimeters > length.millimeters;
    }
}