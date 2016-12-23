package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.BaggageApplicationService;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Set;
import java.util.UUID;

@Path("/passengers/{passenger_hash}/baggages")
@Produces(MediaType.APPLICATION_JSON)
public class BaggageResource {
    @Context
    UriInfo uriInfo;

    private final BaggageApplicationService baggageApplicationService;
    private final BaggagesListAssembler baggagesListAssembler;

    public BaggageResource() {
        this.baggageApplicationService = new BaggageApplicationService();
        this.baggagesListAssembler = new BaggagesListAssembler();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerBaggage(RegisterBaggageDTO request, @PathParam("passenger_hash") UUID passengerHash) {
        UUID baggageId = baggageApplicationService.registerBaggage(passengerHash, request);
        URI uri = uriInfo.getAbsolutePathBuilder().path(baggageId.toString()).build();
        RegisterBaggageResponse registerBaggageResponse = RegisterBaggageResponse.accepted();

        return Response.created(uri).entity(registerBaggageResponse).build();
    }

    @GET
    public Response getBaggagesList(@PathParam("passenger_hash") UUID passengerHash) {
        Passenger passenger = baggageApplicationService.getPassenger(passengerHash);
        Set<Baggage> baggages = passenger.getBaggages();
        double price = passenger.calculateBaggagesPrice();
        BaggagesListDTO baggagesListDTO = baggagesListAssembler.toDTO(price, baggages);

        return Response.ok(baggagesListDTO, MediaType.APPLICATION_JSON).build();
    }
}