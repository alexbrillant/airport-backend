package ca.ulaval.glo4002.thunderbird.boarding.util.units;

import javax.persistence.Embeddable;

@Embeddable
public class Mass {
    private static final double GRAMS_IN_A_POUND = 453.592;
    private static final double GRAMS_IN_A_KILOGRAM = 1000;

    private double grams;

    private Mass(double grams) {
        this.grams = grams;
    }

    protected Mass() {
        // for hibernate
    }

    public static Mass fromPounds(double pounds) {
        return new Mass(pounds * GRAMS_IN_A_POUND);
    }

    public static Mass fromGrams(double grams) {
        return new Mass(grams);
    }

    public static Mass fromKilograms(double kilograms) {
        return new Mass(kilograms * GRAMS_IN_A_KILOGRAM);
    }

    public double toPounds() {
        return grams / GRAMS_IN_A_POUND;
    }

    public double toGrams() {
        return grams;
    }

    public double toKilograms() {
        return grams / GRAMS_IN_A_KILOGRAM;
    }

    public Mass add(Mass otherMass) {
        return Mass.fromGrams(grams + otherMass.grams);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mass)) {
            return false;
        }

        Mass other = (Mass) obj;
        return grams == other.grams;
    }

    public boolean isSuperiorTo(Mass mass) {
        return grams > mass.grams;
    }

    public boolean isInferiorOrEqualTo(Mass mass) {
        return grams <= mass.grams;
    }
}
