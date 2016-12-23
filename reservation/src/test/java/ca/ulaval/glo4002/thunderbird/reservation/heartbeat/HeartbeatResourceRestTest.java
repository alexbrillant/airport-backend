package ca.ulaval.glo4002.thunderbird.reservation.heartbeat;

import org.junit.Test;

import static ca.ulaval.glo4002.thunderbird.reservation.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;

public class HeartbeatResourceRestTest {
    private final static String PARAM_NAME = "token";
    private final static String TOKEN = "A_TOKEN";

    @Test
    public void givenAToken_whenGettingHeartbeat_shouldReturnThisToken() {
        givenBaseRequest()
                .param(PARAM_NAME, TOKEN)
                .when().get(HeartbeatResource.PATH)
                .then().statusCode(OK.getStatusCode())
                .body("token", equalTo(TOKEN));
    }
}