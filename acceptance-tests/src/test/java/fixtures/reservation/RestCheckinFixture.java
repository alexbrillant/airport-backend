package fixtures.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.CheckinResource;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestCheckinFixture extends ReservationBaseRestFixture {
    public void givenPassengerCheckin(UUID passengerHash) {
        Map<String, Object> body = getJsonAsMap(passengerHash, "AGENT", false);
        URI uri = UriBuilder.fromPath(CheckinResource.PATH).build();
        givenRequest().body(body)
                .when().post(uri);
    }

    private Map<String, Object> getJsonAsMap(UUID passengerHash, String by, Boolean vip) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("passenger_hash", passengerHash.toString());
        jsonAsMap.put("by", by);
        jsonAsMap.put("vip", vip.toString());
        return jsonAsMap;
    }
}