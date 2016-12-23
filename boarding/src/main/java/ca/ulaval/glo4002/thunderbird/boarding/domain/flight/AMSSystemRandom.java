package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import java.util.HashMap;
import java.util.Random;

public class AMSSystemRandom implements AMSSystem {
    public static final String DASH_8 = "dash-8";
    public static final String AIRBUS_A320 = "a320";
    public static final String BOEING_777_300 = "boeing-777-300";
    private static final String[] modelSet = {DASH_8, AIRBUS_A320, BOEING_777_300};
    private static final String DASH_FLIGHT = "QK-918";
    private static final String AIRBUS_FLIGHT = "NK-750";
    private static final String BOEING_FLIGHT = "QK-432";
    private Random random = new Random();
    private HashMap<String, String> flightModelCache = new HashMap<>();

    @Override
    public synchronized String getPlaneModel(String flightNumber) {
        switch (flightNumber) {
            case DASH_FLIGHT:
                return DASH_8;

            case AIRBUS_FLIGHT:
                return AIRBUS_A320;

            case BOEING_FLIGHT:
                return BOEING_777_300;

            default:
                return createPlaneModel(flightNumber);
        }
    }

    private String createPlaneModel(String flightNumber) {
        String planeModel = flightModelCache.get(flightNumber);
        if (planeModel == null) {
            int planeModelNumber = random.nextInt(modelSet.length);
            planeModel = modelSet[planeModelNumber];
            flightModelCache.put(flightNumber, planeModel);
        }
        return planeModel;
    }

}
