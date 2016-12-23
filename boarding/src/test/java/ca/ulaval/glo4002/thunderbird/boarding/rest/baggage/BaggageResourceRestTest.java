package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

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
import static javax.ws.rs.core.Response.Status.OK;
import static org.eclipse.jetty.http.HttpStatus.Code.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BaggageResourceRestTest {
    private static final String CM_UNIT_FROM_REQUEST = "cm";
    private static final int LINEAR_DIMENSION = 10;
    private static final String KG_UNIT_FROM_REQUEST = "kg";
    private static final String BAGGAGE_TYPE_CHECKED = "checked";
    private static final int WEIGHT = 10;
    private static final String INVALID_UNIT = "invalid_unit";
    private static final UUID VALID_PASSENGER_HASH = EXISTENT_BOARDING_PASSENGER.getHash();
    private static final UUID INVALID_PASSENGER_UUID = UUID.randomUUID();
    private static final String ALLOWED = "allowed";
    private static final String REFUSATION_REASON = "refusation_reason";

    private static final String UUIDRegex = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

    @Test
    public void givenAValidBaggageAndExistentPassenger_whenRegisteringValidBaggage_shouldRegisterBaggage() {
        Map<String, Object> registerBaggageBody = createRegisterBaggageBody(CM_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                KG_UNIT_FROM_REQUEST,
                WEIGHT,
                BAGGAGE_TYPE_CHECKED);

        Response response = givenBaseRequestBoarding()
                .and().body(registerBaggageBody)
                .when().post(String.format("/passengers/%s/baggages", VALID_PASSENGER_HASH))
                .then().statusCode(CREATED.getCode())
                .and().extract().response();


        Boolean locationValidity = isLocationValid(response.getHeader(HttpHeaders.LOCATION), VALID_PASSENGER_HASH);
        assertTrue(locationValidity);
        Boolean allowed = response.path(ALLOWED);
        assertTrue(allowed);
        String deniedReason = response.path(REFUSATION_REASON);
        assertNull(deniedReason);
    }

    @Test
    public void givenAValidPassengerWithBaggages_whenGettingBaggagesList_shouldReturnBaggagesList() {
        givenBaseRequestBoarding()
                .when().get("/passengers/" + VALID_PASSENGER_HASH + "/baggages")
                .then().statusCode(OK.getStatusCode());
    }

    @Test
    public void givenAnInvalidPassenger_whenGettingBaggagesList_shouldGetNotFound() {
        givenBaseRequestBoarding()
                .when().get("/passengers/" + INVALID_PASSENGER_UUID + "/baggages")
                .then().statusCode(NOT_FOUND.getCode());
    }

    @Test
    public void givenAnInvalidWeightUnitBaggage_whenRegisteringBaggage_shouldReturnBadRequest() {
        Map<String, Object> registerBaggageBody = createRegisterBaggageBody(CM_UNIT_FROM_REQUEST,
                LINEAR_DIMENSION,
                INVALID_UNIT,
                WEIGHT,
                BAGGAGE_TYPE_CHECKED);

        givenBaseRequestBoarding()
                .body(registerBaggageBody)
                .when().post(String.format("/passengers/%s/baggages", VALID_PASSENGER_HASH))
                .then().statusCode(BAD_REQUEST.getCode());
    }

    private boolean isLocationValid(String location, UUID passengerHash) {
        String baseUrl = buildUrl("/passengers/" + passengerHash + "/baggages/");
        baseUrl = baseUrl.replace("/", "\\/");
        Pattern pattern = Pattern.compile(baseUrl + UUIDRegex);
        return pattern.matcher(location).matches();
    }

    private Map<String, Object> createRegisterBaggageBody(String linearDimensionUnit,
                                                          int linearDimension,
                                                          String weightUnit,
                                                          int weight,
                                                          String baggageType) {
        Map<String, Object> registerBaggageBody = new HashMap<>();
        registerBaggageBody.put("linear_dimension_unit", linearDimensionUnit);
        registerBaggageBody.put("linear_dimension", linearDimension);
        registerBaggageBody.put("weight", weight);
        registerBaggageBody.put("weight_unit", weightUnit);
        registerBaggageBody.put("type", baggageType);
        return registerBaggageBody;
    }
}
