package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

public class AMSSystemFactory {
    public AMSSystem create() {
        return new AMSSystemRandom();
    }
}
