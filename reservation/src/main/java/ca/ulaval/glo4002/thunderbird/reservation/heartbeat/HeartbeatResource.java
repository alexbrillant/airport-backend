package ca.ulaval.glo4002.thunderbird.reservation.heartbeat;

import org.hibernate.validator.constraints.NotBlank;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path(HeartbeatResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class HeartbeatResource {
    public static final String PATH = "/heartbeat/";

    @GET
    public Heartbeat beat(@NotBlank @QueryParam("token") String token) {
        return new Heartbeat(token);
    }
}