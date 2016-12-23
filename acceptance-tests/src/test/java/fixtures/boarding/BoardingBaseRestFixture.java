package fixtures.boarding;

import io.restassured.specification.RequestSpecification;
import runners.BoardingServerRunner;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

public class BoardingBaseRestFixture {
    protected RequestSpecification givenRequest() {
        return given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .port(BoardingServerRunner.JETTY_TEST_PORT);
    }
}