package ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PassengerAlreadyCheckedInExceptionMapper implements ExceptionMapper<PassengerAlreadyCheckedInException> {
    @Override
    public Response toResponse(PassengerAlreadyCheckedInException exception)
    {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}