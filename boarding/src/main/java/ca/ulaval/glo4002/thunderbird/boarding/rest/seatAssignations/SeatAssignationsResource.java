package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.application.seat.SeatAssignationApplicationService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;

@Path(SeatAssignationsResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class SeatAssignationsResource {
    public static final String PATH = "/seat-assignations/";

    @Context
    UriInfo uriInfo;
    private SeatAssignationApplicationService applicationService;
    private final TakenSeatAssembler assembler;

    public SeatAssignationsResource() {
        assembler = new TakenSeatAssembler();
        applicationService = new SeatAssignationApplicationService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSeat(SeatAssignationDTO request) {
        Seat assignedSeat = applicationService.assignSeat(request);

        TakenSeatDTO takenSeatDTO = assembler.fromDomain(assignedSeat);

        URI uri = buildUri(assignedSeat);
        return Response.created(uri).entity(takenSeatDTO).build();
    }

    private URI buildUri(Seat assignedSeat) {
        String seatAssignationsIdString = Integer.toString(assignedSeat.getId());
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        return uriBuilder.path(seatAssignationsIdString).build();
    }
}