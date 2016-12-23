package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.Instant;

@Path(CheckinResource.PATH)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckinResource {
    public static final String PATH = "/checkins/";

    @Context
    private UriInfo uriInfo;

    @POST
    public Response checkin(@Valid Checkin checkin) {
        checkin.completeCheckin(Instant.now());
        checkin.save();

        String checkinId = checkin.getId().toString();
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(checkinId).build();
        return Response.created(uri).build();
    }
}