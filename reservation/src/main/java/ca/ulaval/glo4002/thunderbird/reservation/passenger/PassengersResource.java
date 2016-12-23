package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path(PassengersResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class PassengersResource {
    public static final String PATH = "/passengers/";

    private final PassengerAssembler passengerAssembler;

    public PassengersResource() {
        passengerAssembler = new PassengerAssembler();
    }

    @GET
    @Path("{passenger_hash}")
    public PassengerDTO fetchPassenger(@PathParam("passenger_hash") UUID passengerHash) {
        Passenger passenger = Passenger.findByPassengerHash(passengerHash);
        return passengerAssembler.toDTO(passenger);
    }
}