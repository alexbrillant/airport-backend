package ca.ulaval.glo4002.thunderbird.boarding.rest;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class RestTestConfig {
    public static final int TEST_SERVER_PORT_BOARDING = 49155;
    public static final int TEST_SERVER_PORT_RESERVATION = 50550;

    public static String buildUrl(String path) {
        return String.format("http://localhost:%d%s", TEST_SERVER_PORT_BOARDING, path);
    }

    public static RequestSpecification givenBaseRequestReservation() {
        return given()
                .accept(ContentType.JSON)
                .port(TEST_SERVER_PORT_RESERVATION)
                .contentType(ContentType.JSON);
    }

    public static RequestSpecification givenBaseRequestBoarding() {
        return given()
                .accept(ContentType.JSON)
                .port(TEST_SERVER_PORT_BOARDING)
                .contentType(ContentType.JSON);
    }
}
