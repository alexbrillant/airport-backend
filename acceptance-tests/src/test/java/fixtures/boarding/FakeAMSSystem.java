package fixtures.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;

public class FakeAMSSystem implements AMSSystem {
    public static final String DASH_8 = "dash-8";

    public synchronized String getPlaneModel(String flightNumber) {
        return DASH_8;
    }
}
