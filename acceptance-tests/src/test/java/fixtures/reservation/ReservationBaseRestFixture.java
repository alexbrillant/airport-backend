package fixtures.reservation;

import io.restassured.specification.RequestSpecification;
import runners.ReservationServerRunner;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class ReservationBaseRestFixture {
    protected RequestSpecification givenRequest() {
        return given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .port(ReservationServerRunner.JETTY_TEST_PORT);
    }
}