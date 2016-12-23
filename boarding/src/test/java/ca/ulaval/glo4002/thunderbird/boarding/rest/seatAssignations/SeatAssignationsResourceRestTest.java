package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import io.restassured.response.Response;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static ca.ulaval.glo4002.thunderbird.boarding.contexts.DevContext.EXISTENT_BOARDING_PASSENGER;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.buildUrl;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequestBoarding;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.*;

public class SeatAssignationsResourceRestTest {
    private static final UUID NON_EXISTENT_PASSENGER_HASH = UUID.randomUUID();
    private static final String VALID_MODE = "RANDOM";
    private static final String INVALID_MODE = "INVALID";
    private static final String INT_REGEX = "\\d+";

    @Test
    public void givenAValidPassengerHashAndAValidMode_whenAssigningSeat_shouldAssignSeat() {
        Map<String, Object> seatAssignationBody = createSeatAssignationBody(EXISTENT_BOARDING_PASSENGER.getHash(), VALID_MODE);

        Response response;
        response = givenBaseRequestBoarding().body(seatAssignationBody)
                .when().post(SeatAssignationsResource.PATH)
                .then().statusCode(CREATED.getCode())
                .and().extract().response();

        Boolean locationValidity = isLocationValid(response.getHeader(HttpHeaders.LOCATION));
        assertTrue(locationValidity);
        String seat = response.path("seat");
        assertNotNull(seat);
        assertNotEquals("", seat);
    }

    private boolean isLocationValid(String location) {
        String baseUrl = buildUrl(SeatAssignationsResource.PATH);
        baseUrl = baseUrl.replace("/", "\\/");
        Pattern pattern = Pattern.compile(baseUrl + INT_REGEX);

        return pattern.matcher(location).matches();
    }

    @Test
    public void givenAnInvalidPassengerHashAndAValidMode_whenAssigningSeat_shouldReturnNotFound() {
        Map<String, Object> seatAssignationBody = createSeatAssignationBody(NON_EXISTENT_PASSENGER_HASH, VALID_MODE);

        givenBaseRequestBoarding().body(seatAssignationBody)
                .when().post(SeatAssignationsResource.PATH)
                .then().statusCode(NOT_FOUND.getCode());
    }

    @Test
    public void givenAValidPassengerHashAndInvalidMode_whenAssigningSeat_shouldReturnBadRequest() {
        Map<String, Object> seatAssignationBody = createSeatAssignationBody(EXISTENT_BOARDING_PASSENGER.getHash(), INVALID_MODE);

        givenBaseRequestBoarding().body(seatAssignationBody)
                .when().post(SeatAssignationsResource.PATH)
                .then().statusCode(BAD_REQUEST.getCode());
    }

    private Map<String, Object> createSeatAssignationBody(UUID passengerHash, String mode) {
        Map<String, Object> seatAssignationBody = new HashMap<>();
        seatAssignationBody.put("passenger_hash", passengerHash);
        seatAssignationBody.put("mode", mode);
        return seatAssignationBody;
    }
}