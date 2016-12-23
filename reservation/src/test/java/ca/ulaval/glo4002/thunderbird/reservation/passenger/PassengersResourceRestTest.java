package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.contexts.DevContext;
import org.junit.Test;

import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;

public class PassengersResourceRestTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();
    private static final String PASSENGER_SERVICE_ADDRESS = PassengersResource.PATH;
    private static final String PASSENGER_REST_FORMAT = PASSENGER_SERVICE_ADDRESS + "{passenger_UUID}";

    @Test
    public void givenRandomPassengerHash_whenAskingForPassenger_shouldReturnNotFound() {
        givenBaseRequest()
                .when().get(PASSENGER_REST_FORMAT, RANDOM_UUID.toString())
                .then().statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void givenExistingPassengerHash_whenAskingForPassenger_shouldReturnExistentPassenger() {
        givenBaseRequest()
                .when().get(PASSENGER_REST_FORMAT, DevContext.NON_CHECKIN_PASSENGER_HASH)
                .then().statusCode(OK.getStatusCode())
                .and().body("passenger_hash", equalTo(DevContext.NON_CHECKIN_PASSENGER_HASH.toString()))
                .and().body("seat_class", equalTo(DevContext.SEAT_CLASS))
                .and().body("flight_number", equalTo(DevContext.FLIGHT_NUMBER))
                .and().body("flight_date", equalTo(DevContext.FLIGHT_DATE_STRING))
                .and().body("vip", equalTo(false))
                .and().body("checked_in", equalTo(false))
                .and().body("child", equalTo(false));
    }
}